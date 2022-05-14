/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import enums.EMsgType;
import main.Line;
import main.ProgInfo;

public class JAMacro extends JAObject {
	public JAMacro(ProgInfo l_pi, Line l_line, String l_value, boolean l_start) throws Exception {
		super(l_pi, l_line, l_value);
		
		if(l_start) {
			if(!value.isEmpty()) {
				if(l_pi.is_undefined(line, value, false)) {
					if(!l_pi.create_macro(line, value)) {
						l_pi.print(EMsgType.MSG_ERROR, line, MSG_MISSING, "found no closing .macro");
					}
				}
			}
			else {
				l_pi.print(EMsgType.MSG_ERROR, line, MSG_MISSING_PARAMETERS);
			}
		}
		else {
			if(value.isEmpty()) {
				if(!l_pi.close_macro()) {
					l_pi.print(EMsgType.MSG_ERROR, line, MSG_MISSING, "no .MACRO found before .endmacro");
				}
			}
			else {
				l_pi.print(EMsgType.MSG_ERROR, line, MSG_DIRECTIVE_GARBAGE);
			}
		}
	}
}
