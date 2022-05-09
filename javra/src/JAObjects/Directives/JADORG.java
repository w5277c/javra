/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects.Directives;

import JAObjects.Expr;
import enums.EMsgType;
import main.Line;
import main.ORGInfo;
import main.ProgInfo;

public class JADORG extends JADirective {
	public JADORG(ProgInfo l_pi, Line l_line) {
		Long addr = Expr.parse(l_pi, l_line, l_line.get_value());
		if(null == addr) {
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_INVALID_NUMBER);
		}
		else {
			l_pi.get_cur_segment().get_orglist().add(new ORGInfo(addr));
		}
	}
}
