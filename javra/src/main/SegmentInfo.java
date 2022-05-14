/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import enums.ESegmentType;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import output.HexBuilder;
import output.IntelHexBuilder;

public class SegmentInfo {
	protected	ESegmentType					type;
	protected	HashMap<Integer,DataBlock>	blocks		= new HashMap<>();
	protected	DataBlock						cur_block	= null;
	protected	boolean							overlap			= false;
	
	public SegmentInfo(ESegmentType l_type) {
		type = l_type;
	}

	public ESegmentType get_type() {
		return type;
	}
	
	public DataBlock set_block(int l_addr) {
		DataBlock datablock = blocks.get(l_addr);
		if(null == datablock) {
			cur_block = new DataBlock(l_addr); 
			blocks.put(l_addr, cur_block);
			return null;
		}
		else {
			cur_block = datablock;
			return datablock;
		}
	}
	
	public DataBlock get_cur_block() {
		if(null == cur_block) {
			cur_block = new DataBlock(0x0000);
			blocks.put(0x0000, cur_block);
		}
		return cur_block;
	}
	
	public boolean get_overlap() {
		return overlap;
	}
	public void set_overlap(boolean l_is_on) {
		overlap = l_is_on;
	}

	public void set_addr(int l_addr) {
		DataBlock datablock = null;
		for(DataBlock _datablock : blocks.values()) {
			if(_datablock.get_start() <= l_addr && (_datablock.get_start()+_datablock.get_length()) > l_addr) {
				datablock = _datablock;
				break;
			}
		}
		if(null == datablock) {
			datablock = cur_block;
		}
		else {
			cur_block = datablock;
		}
		datablock.set_addr(l_addr);
	}

	public void build(ProgInfo l_pi, HexBuilder l_builder) throws IOException {
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
			if((block1.get_start() + block1.get_length()-0x01) >= block2.get_start()) {
				block1.set_overlap(block2.get_start());
			}
		}
		
		for(DataBlock block : sorted) {
			l_builder.push(block.get_data(), block.get_start(), block.get_length() - block.get_overlap());
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
		if(this instanceof CodeSegmentInfo) {
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
