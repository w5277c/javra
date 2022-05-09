/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import JAObjects.JAObject;
import enums.EMsgType;
import main.Line;
import main.ProgInfo;

public class JAIfNDef extends JAObject {
	public JAIfNDef(ProgInfo l_pi, Line l_line) throws Exception {
		String tmp = l_line.get_value().trim().toLowerCase();
		if(!tmp.isEmpty()) {
			l_pi.get_ii().block_start(l_line, null != l_pi.get_constant(tmp));
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_INVALID_SYNTAX);
		}
	}
}
