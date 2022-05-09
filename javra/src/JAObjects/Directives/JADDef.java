/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects.Directives;

import enums.EMsgType;
import main.Constant;
import main.Line;
import main.ProgInfo;

public class JADDef extends JADirective {
	private	String	name;
	
	public JADDef(ProgInfo l_pi, Line l_line) {
		String parts[] = l_line.get_value().split("=");
		String tmp = parts[0x00].trim().toLowerCase();
		if(0x02 == parts.length && !tmp.isEmpty() && tmp.replaceAll(REG_CONST_NAME, "").isEmpty()) {
			name = tmp;
			Constant _constant = l_pi.get_constants().get(name);
			if(null != _constant) {
				l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_ALREADY_DEFINED, " at '" + _constant.get_line().get_location() + "'");
			}
			else {
				Integer _register = get_register(l_pi, name);
				if(null != _register) {
					l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_ALREADY_DEFINED, " as 'r" + _register + "'");
				}
				else {
					tmp = parts[0x01].trim().toLowerCase();
					Integer register = get_register(l_pi, tmp);
					if(null == register) {
						l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_WRONG_REGISTER);
					}
					else {
						l_pi.get_registers()[register] = name;
					}
				}
			}
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_INVALID_SYNTAX);
		}
	}
}
