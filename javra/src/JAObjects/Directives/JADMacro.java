/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects.Directives;

import enums.EMsgType;
import main.Constant;
import main.Line;
import main.Macros;
import main.ProgInfo;

public class JADMacro extends JADirective {
	public JADMacro(ProgInfo l_pi, Line l_line, boolean l_start) throws Exception {
		if(l_start) {
			String name = l_line.get_value().trim().toLowerCase();
			if(null != name) {
				Constant contant = l_pi.get_constants().get(name);
				if(null == contant) {
					Integer register_id = get_register(l_pi, name);
					if(null == register_id) {
						Macros macros = l_pi.get_macros().get(name);
						if(null == macros) {
							if(!l_pi.create_macros(name)) {
								l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_MISSING, ".endmacro");
							}
						}
						else {
							l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_ALREADY_DEFINED, "as macros");
						}
					}
					else {
						l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_ALREADY_DEFINED, "as 'r" + Integer.toString(register_id) + "'");
					}
				}
				else {
					l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_ALREADY_DEFINED, "at " + contant.get_line().get_location());
				}
			}
			else {
				l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_INVALID_SYNTAX);
			}
		}
		else {
			if(!l_pi.close_macros()) {
				l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_MISSING, ".macro");
			}
		}
	}
}
