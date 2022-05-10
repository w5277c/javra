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

public class JADef extends JAObject {
	private	String	name;
	
	public JADef(ProgInfo l_pi, Line l_line) {
		String parts[] = l_line.get_value().split("=");
		String tmp = parts[0x00].trim().toLowerCase();
		if(0x02 == parts.length && !tmp.isEmpty() && tmp.replaceAll(REGEX_CONST_NAME, "").isEmpty()) {
			name = tmp;
			if(is_undefined(l_pi, name)) {
				tmp = parts[0x01].trim().toLowerCase();
				Integer register = get_register(l_pi, tmp);
				if(null == register) {
					l_pi.print(EMsgType.MSG_ERROR, MSG_WRONG_REGISTER);
				}
				else {
					l_pi.get_registers()[register] = name;
				}
			}
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, MSG_INVALID_SYNTAX);
		}
	}
}
