/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects.Directives;

import enums.EMsgType;
import main.Line;
import main.ProgInfo;

public class JADMessage extends JADirective {
	public JADMessage(ProgInfo l_pi, Line l_line) throws Exception {
		l_pi.print(EMsgType.MSG_MESSAGE, null, l_line.get_value());
	}
}
