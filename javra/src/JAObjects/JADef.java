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
	public JADef(ProgInfo l_pi, Line l_line, String l_value) {
		super(l_pi, l_line, l_value);
	
		parse();
	}
	
	@Override
	public void parse() {
		String parts[] = value.split("=");
		String name = parts[0x00].trim();
		if(0x02 == parts.length && !name.isEmpty() && name.replaceAll(REGEX_CONST_NAME, "").isEmpty()) {
			Constant constant = pi.get_constant(name);
			if(null == constant) {
				if(!name.startsWith("r") || name.length() <= 0x01 || name.length() > 0x03 || !name.substring(0x01).replaceAll("\\d", "").isEmpty()) {
					Macro macros = pi.get_macros().get(name);
					if(null == macros) {
						Integer register = pi.get_register(parts[0x01].trim());
						if(null == register) {
							pi.print(EMsgType.MSG_ERROR, line, MSG_WRONG_REGISTER);
						}
						else {
							pi.put_register(line, name, register);
						}
					}
					else {
						pi.print(EMsgType.MSG_ERROR, line, MSG_ALREADY_DEFINED, "at '" + macros.get_line().get_location() + "'");
					}
				}
				else {
					pi.print(EMsgType.MSG_ERROR, line, MSG_ALREADY_DEFINED);
				}
			}
			else {
				pi.print(EMsgType.MSG_ERROR, line, MSG_ALREADY_DEFINED, "at '" + constant.get_line().get_location() + "'");
			}
		}
		else {
			pi.print(EMsgType.MSG_ERROR, line, MSG_INVALID_SYNTAX);
		}
	}
}
