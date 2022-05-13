/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import common.Expr;
import enums.EMsgType;
import main.ProgInfo;

public class JAElseIf extends JAObject {
	public JAElseIf(ProgInfo l_pi) throws Exception {
		String tmp = l_pi.get_cur_line().get_value().trim().toLowerCase();
		if(!tmp.isEmpty()) {
			Long value = Expr.parse(l_pi, tmp);
			if(null == value) {
				//l_pi.print(EMsgType.MSG_ERROR, MSG_INVALID_SYNTAX);
				l_pi.put_unparsed();
			}
			else {
				l_pi.get_ii().block_elseif(0x00 == value);
			}
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, MSG_INVALID_SYNTAX);
		}
	}
}
