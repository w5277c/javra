/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import enums.EMsgType;
import enums.ESegmentType;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import output.HexBuilder;

public class SegmentInfo {
	protected	ProgInfo							pi;
	protected	ESegmentType					type;
	protected	HashMap<Integer,DataBlock>	blocks		= new HashMap<>();
	protected	DataBlock						cur_block	= null;
	protected	boolean							overlap		= false;
	
	public SegmentInfo(ProgInfo l_pi, ESegmentType l_type) {
		pi = l_pi;
		type = l_type;
	}

	public ESegmentType get_type() {
		return type;
	}
	
	private DataBlock create(Line l_line, Integer l_address) {
		if(ESegmentType.DATA == type) {
			if(null == pi.get_device()) {
				pi.print(EMsgType.MSG_WARNING, l_line, "No .DEVICE definition found. Cannot make useful address range check !");
				return new DataBlock(null == l_address ? 0x0000 : l_address);
			}
			else {
				if(l_address < pi.get_device().get_ram_start() || l_address > (pi.get_device().get_ram_start() + pi.get_device().get_ram_size())) {
					pi.print(EMsgType.MSG_ERROR, l_line,	"SRAM address ouf of range (" + pi.get_device().get_ram_start() + " <= " + l_address +
																		" <= " + (pi.get_device().get_ram_start() + pi.get_device().get_ram_size()) + ")");
					return new DataBlock(pi.get_device().get_ram_start());
				}
				else {
					return new DataBlock(null == l_address ? pi.get_device().get_ram_start() : l_address);
				}
			}
		}
		else if(ESegmentType.EEPROM == type) {
			if(null == pi.get_device()) {
				pi.print(EMsgType.MSG_WARNING, l_line, "No .DEVICE definition found. Cannot make useful address range check !");
				return new DataBlock(null == l_address ? 0x0000 : l_address);
			}
			else {
				if(0 > l_address || l_address > pi.get_device().get_eeprom_size()) {
					pi.print(EMsgType.MSG_ERROR, l_line, "EEPROM address ouf of range (0 <= " + l_address + " <= " +	(pi.get_device().get_eeprom_size()) + ")");
					return new DataBlock(0x0000);
				}
				else {
					return new DataBlock(null == l_address ? 0x0000 : l_address);
				}
			}
		}
		else {
			if(null == pi.get_device()) {
				pi.print(EMsgType.MSG_WARNING, l_line, "No .DEVICE definition found. Cannot make useful address range check !");
				return new CodeBlock(null == l_address ? 0x0000 : l_address);
			}
			else {
				if(0 > l_address || l_address > pi.get_device().get_flash_size()) {
					pi.print(EMsgType.MSG_ERROR, l_line, "EEPROM address ouf of range (0 <= " + l_address + " <= " +	(pi.get_device().get_flash_size()) + ")");
					return new CodeBlock(0x0000);
				}
				else {
					return new CodeBlock(null == l_address ? 0x0000 : l_address);
				}
			}
		}
	}
	
	public DataBlock get_cur_block(Line l_line) {
		if(null == cur_block) {
			cur_block = create(l_line, 0x0000);
			blocks.put(cur_block.get_address(), cur_block);
		}
		return cur_block;
	}
	
	public boolean get_overlap() {
		return overlap;
	}
	public void set_overlap(boolean l_is_on) {
		overlap = l_is_on;
	}

	public void set_addr(Line l_line, int l_addr) {
		if(null == cur_block) {
			cur_block = create(l_line, l_addr);
			blocks.put(cur_block.get_address(), cur_block);
		}

		DataBlock datablock = null;
		for(DataBlock _datablock : blocks.values()) {
			if(_datablock.get_start() <= l_addr && (_datablock.get_start()+_datablock.get_length()) > l_addr) {
				datablock = _datablock;
				break;
			}
		}
		if(null != datablock) {
			cur_block = datablock;
			cur_block.set_addr(l_addr);
		}
		else if(null == datablock && (cur_block.get_start() + cur_block.get_length()) == l_addr) {
			cur_block.set_addr(l_addr);
		}
		else {
			cur_block = create(l_line, l_addr);
			blocks.put(cur_block.get_address(), cur_block);
		}
	}

	public void build(ProgInfo l_pi, HexBuilder l_builder) throws IOException {
		if(ESegmentType.DATA != type) {
			LinkedList<DataBlock> sorted = new LinkedList<>(blocks.values());
			Collections.sort(sorted, new Comparator<DataBlock>() {
				@Override
				public int compare(DataBlock o1, DataBlock o2) {
					if(o1.get_start() == o2.get_start()) {
						return Integer.compare(o1.get_length(), o2.get_length());
					}
					return Integer.compare(o1.get_start(), o2.get_start());
				}
			});
			for(int index=0; index<(sorted.size()-0x01);index++) {
				DataBlock block1 = sorted.get(index);
				DataBlock block2 = sorted.get(index+0x01);
/*				if((block1.get_start() + block1.get_length()) == block2.get_start()) {
					block1.append(block2);
					sorted.remove(block2);
					index--;
				}
				else */
			if((block1.get_start() + block1.get_length()-0x01) >= block2.get_start()) {
					block1.set_overlap(block2.get_start());
				}
			}

			for(DataBlock block : blocks.values()) {
				if(ESegmentType.CODE == type) {
					l_builder.push(block.get_data(), block.get_start()*2, block.get_length()*2 - block.get_overlap()*2);
				}
				else {
					l_builder.push(block.get_data(), block.get_start(), block.get_length() - block.get_overlap());
				}
			}
		}
	}
	
	public void print_stat(ProgInfo l_pi) {
		int total = 0;
		l_pi.print("--" + type +  "-----------------------------------");
		for(DataBlock block : blocks.values()) {
			l_pi.print(	" Start\t= " + String.format("%04X", block.get_start()) +
							", End = " + String.format("%04X", block.get_start() + block.get_length() - 0x01) +
							", Length = " + String.format("%04X", block.get_length()) + 
							(0x00 == block.get_overlap() ? "" : " [Overlap: " + String.format("%04X", block.get_overlap()) + "]"));
			total += (block.get_length() - block.get_overlap());
		}
		l_pi.print(" -----");
		if(ESegmentType.CODE == type) {
			l_pi.print(" Total\t:  " + total + " words (" + (total*2) + " bytes)");
		}
		else {
			l_pi.print(" Total\t:  " + total + " bytes");
		}
	}
	
	public boolean is_empty() {
		for(DataBlock datablock : blocks.values()) {
			if(0 != datablock.get_length()) {
				return false;
			}
		}
		return true;
	}
}
