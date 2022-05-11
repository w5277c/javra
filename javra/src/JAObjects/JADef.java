/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
----------------------------------------------------------------------------------------------------------------------------------------------------------------
TODO: #define SQR(X) ((X)*(X))
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/

package JAObjects;

import common.Macro;
import enums.EMsgType;
import main.Constant;
import main.Line;
import main.ProgInfo;

public class JADef extends JAObject {
	private	String	name;
	
	public JADef(ProgInfo l_pi, Line l_line) {
		String parts[] = l_line.get_value().split("=");
		String tmp = parts[0x00].trim().toLowerCase();
		if(0x02 == parts.length && !tmp.isEmpty() && tmp.replaceAll(REGEX_CONST_NAME, "").isEmpty()) {
			name = tmp;

			Constant constant = l_pi.get_constant(name);
			if(null == constant) {
				if(!name.startsWith("r") || name.length() <= 0x01 || name.length() > 0x03 || !name.substring(0x01).replaceAll("\\d", "").isEmpty()) {
					Macro macros = l_pi.get_macros().get(name);
					if(null == macros) {
						Integer register = l_pi.get_register(parts[0x01].trim().toLowerCase());
						if(null == register) {
							l_pi.print(EMsgType.MSG_ERROR, MSG_WRONG_REGISTER);
						}
						else {
							l_pi.put_register(name, register);
						}
					}
					else {
						l_pi.print(EMsgType.MSG_ERROR, MSG_ALREADY_DEFINED, "at '" + macros.get_line().get_location() + "'");
					}
				}
				else {
					l_pi.print(EMsgType.MSG_ERROR, MSG_ALREADY_DEFINED);
				}
			}
			else {
				l_pi.print(EMsgType.MSG_ERROR, MSG_ALREADY_DEFINED, "at '" + constant.get_line().get_location() + "'");
			}
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, MSG_INVALID_SYNTAX);
		}
	}
}
