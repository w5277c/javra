/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import common.Expr;
import JAObjects.JAObject;
import enums.EMsgType;
import main.Line;
import main.ProgInfo;

public class JAIf extends JAObject {
	public JAIf(ProgInfo l_pi, Line l_line) throws Exception {
		String tmp = l_line.get_value().trim().toLowerCase();
		if(!tmp.isEmpty()) {
			if(l_pi.get_ii().is_blockskip()) {
				l_pi.get_ii().block_start(l_line, false);
			}
			else {
				Long _tmp = Expr.parse(l_pi, l_line, tmp);
				if(null == _tmp) {
					l_pi.print(EMsgType.MSG_ERROR, MSG_INVALID_SYNTAX);
				}
				else {
					l_pi.get_ii().block_start(l_line, 0x00 == _tmp);
				}
			}
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, MSG_INVALID_SYNTAX);
		}
	}
}
