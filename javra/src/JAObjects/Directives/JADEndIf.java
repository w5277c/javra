/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects.Directives;

import enums.EMsgType;
import main.Line;
import main.ProgInfo;

public class JADEndIf extends JADirective {
	public JADEndIf(ProgInfo l_pi, Line l_line) throws Exception {
		String tmp = l_line.get_value().trim().toLowerCase();
		if(tmp.isEmpty()) {
			l_pi.get_ii().block_end(l_line);
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_INVALID_SYNTAX);
		}
	}
}
