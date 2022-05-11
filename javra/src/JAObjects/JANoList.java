/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
11.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import enums.EMsgType;
import main.Line;
import main.ProgInfo;

public class JANoList extends JAObject {
	public JANoList(ProgInfo l_pi, Line l_line) {
		if(!l_line.get_value().trim().isEmpty()) {
			l_pi.print(EMsgType.MSG_ERROR, MSG_INVALID_SYNTAX);
		}
		l_pi.set_list_on(false);
	}
}
