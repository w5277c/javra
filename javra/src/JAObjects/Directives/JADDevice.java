/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects.Directives;

import enums.EMsgType;
import main.Line;
import main.ProgInfo;

public class JADDevice extends JADirective {
	public JADDevice(ProgInfo l_pi, Line l_line) throws Exception {
		if(!l_line.get_value().trim().isEmpty()) {
			l_pi.set_device(l_line.get_value().trim().toLowerCase());
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_INVALID_SYNTAX);
		}
	}
}
