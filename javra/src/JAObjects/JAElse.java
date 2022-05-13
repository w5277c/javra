/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import enums.EMsgType;
import main.Line;
import main.ProgInfo;

public class JAElse extends JAObject {
	public JAElse(ProgInfo l_pi) throws Exception {
		String tmp = l_pi.get_cur_line().get_value().trim().toLowerCase();
		if(tmp.isEmpty()) {
			l_pi.get_ii().block_skip_invert(l_pi.get_cur_line());
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, MSG_INVALID_SYNTAX);
		}
	}
}
