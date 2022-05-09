/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects.Directives;

import JAObjects.Expr;
import enums.EMsgType;
import main.Line;
import main.ProgInfo;

public class JADIf extends JADirective {
	public JADIf(ProgInfo l_pi, Line l_line) throws Exception {
		String tmp = l_line.get_value().trim().toLowerCase();
		if(!tmp.isEmpty()) {
			if(l_pi.get_ii().is_blockskip()) {
				l_pi.get_ii().block_start(l_line, false);
			}
			else {
				Long _tmp = Expr.parse(l_pi, l_line, tmp);
				if(null == _tmp) {
					l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_INVALID_SYNTAX);
				}
				else {
					l_pi.get_ii().block_start(l_line, 0x00 == _tmp);
				}
			}
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_INVALID_SYNTAX);
		}
	}
}
