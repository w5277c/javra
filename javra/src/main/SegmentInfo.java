/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import java.util.LinkedList;

public class SegmentInfo {
	private	LinkedList<DataBlock> datablocks	= new LinkedList<>();

	public SegmentInfo() {
		datablocks.add(new DataBlock(0x0000));
	}

	public void add_datablock(long l_addr) {
		datablocks.add(new DataBlock(l_addr));
	}
	
	public DataBlock get_datablock() {
		return datablocks.getLast();
	}
}
