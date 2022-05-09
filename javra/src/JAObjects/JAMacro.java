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

public class JAMacro extends JAObject {
	public JAMacro(ProgInfo l_pi, Line l_line, boolean l_start) throws Exception {
		if(l_start) {
			String name = l_line.get_value().trim().toLowerCase();
			if(null != name) {
				if(is_undefined(l_pi, l_line, name)) {
					if(!l_pi.create_macro(l_line, name)) {
						l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_MISSING, "found no closing .macro");
					}
				}
			}
		}
		else {
			if(!l_pi.close_macro()) {
				l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_MISSING, "no .MACRO found before .endmacro");
			}
		}
	}
}
