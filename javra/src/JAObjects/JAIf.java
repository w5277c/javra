/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
----------------------------------------------------------------------------------------------------------------------------------------------------------------
TODO:defined(__ATmega48__)
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import common.Expr;
import enums.EMsgType;
import main.ProgInfo;

public class JAIf extends JAObject {
	public JAIf(ProgInfo l_pi) throws Exception {
		String tmp = l_pi.get_cur_line().get_value().trim().toLowerCase();
		if(!tmp.isEmpty()) {
			if(l_pi.get_ii().is_blockskip()) {
				l_pi.get_ii().block_start(l_pi.get_cur_line(), false);
			}
			else {
				Long _tmp = Expr.parse(l_pi, tmp);
				if(null == _tmp) {
					//l_pi.print(EMsgType.MSG_ERROR, MSG_INVALID_SYNTAX);
					l_pi.put_unparsed();
				}
				else {
					l_pi.get_ii().block_start(l_pi.get_cur_line(), 0x00 == _tmp);
				}
			}
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, MSG_INVALID_SYNTAX);
		}
	}
}
