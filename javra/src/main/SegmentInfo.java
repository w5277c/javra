/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import enums.ESegmentType;
import java.util.LinkedList;

public class SegmentInfo {
	private	ESegmentType				type;
	private	LinkedList<DataBlock>	datablocks	= new LinkedList<>();
	private	boolean						overlap		= false;
	
	public SegmentInfo(ESegmentType l_type) {
		type = l_type;
		datablocks.add(new DataBlock(0x0000));
	}

	public ESegmentType get_type() {
		return type;
	}
	
	public void add_datablock(int l_addr) {
		datablocks.add(new DataBlock(l_addr));
	}
	
	public DataBlock get_datablock() {
		return datablocks.getLast();
	}
	
	public boolean get_overlap() {
		return overlap;
	}
	public void set_overlap(boolean l_is_on) {
		overlap = l_is_on;
	}
}
