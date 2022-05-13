/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import enums.ESegmentType;
import java.util.HashMap;

public class SegmentInfo {
	private	ESegmentType					type;
	private	HashMap<Integer,DataBlock>	datablocks		= new HashMap<>();
	private	DataBlock						cur_datablock	= null;
	private	boolean							overlap			= false;
	
	public SegmentInfo(ESegmentType l_type) {
		type = l_type;
	}

	public ESegmentType get_type() {
		return type;
	}
	
	public DataBlock set_datablock(int l_addr) {
		DataBlock datablock = datablocks.get(l_addr);
		if(null == datablock) {
			cur_datablock = new DataBlock(l_addr); 
			datablocks.put(l_addr, cur_datablock);
			return null;
		}
		else {
			cur_datablock = datablock;
			return datablock;
		}
	}
	
	public DataBlock get_cur_datablock() {
		if(null == cur_datablock) {
			cur_datablock = new DataBlock(0x0000);
			datablocks.put(0x0000, cur_datablock);
		}
		return cur_datablock;
	}
	
	public boolean get_overlap() {
		return overlap;
	}
	public void set_overlap(boolean l_is_on) {
		overlap = l_is_on;
	}

	public void set_waddr(int l_waddr) {
		DataBlock datablock = null;
		for(DataBlock _datablock : datablocks.values()) {
			if(_datablock.get_wstart() <= l_waddr && (_datablock.get_wstart()+_datablock.get_wlength()) > l_waddr) {
				datablock = _datablock;
				break;
			}
		}
		if(null == datablock) {
			datablock = cur_datablock;
		}
		else {
			cur_datablock = datablock;
		}
		datablock.set_waddr(l_waddr);
	}
}
