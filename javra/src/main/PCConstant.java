/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import enums.EMsgType;
import enums.ESegmentType;

public class PCConstant extends Constant {
	private	Line		line;
	private	String	name;
	private	ProgInfo	pi;
	
	public PCConstant(ProgInfo l_pi) {
		super(new Line("", 0, ""), "pc", 0);
		
		pi = l_pi;
	}
	
	@Override
	public long get_num(Line l_line) {
		if(ESegmentType.CODE != pi.get_cur_segment().get_type()) {
			pi.print(EMsgType.MSG_WARNING, "got PC request in not CSEG");
		}
		return pi.get_cseg().get_datablock().get_addr();
	}
}
