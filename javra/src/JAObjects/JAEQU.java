/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import common.Expr;
import enums.EMsgType;
import main.Line;
import common.Macro;
import main.ProgInfo;

public class JAEQU extends JAObject {
	public JAEQU(ProgInfo l_pi, Line l_line) {
		String parts[] = l_line.get_value().split("=");
		String tmp = parts[0x00].trim().toLowerCase();
		if(0x02 == parts.length && !tmp.isEmpty() && tmp.replaceAll(REGEX_CONST_NAME, "").isEmpty()) {
			String name = tmp;
			
			Integer register_id = l_pi.get_register(name);
			if(null != register_id) {
				l_pi.print(EMsgType.MSG_ERROR, JAObject.MSG_ALREADY_DEFINED, "as 'r" + Integer.toString(register_id) + "'");
				return;
			}
			Macro macros = l_pi.get_macros().get(name);
			if(null != macros) {
				l_pi.print(EMsgType.MSG_ERROR, JAObject.MSG_ALREADY_DEFINED, "at '" + macros.get_line().get_location() + "'");
				return;
			}

			tmp = parts[0x01].trim().toLowerCase();
			Long num = Expr.parse(l_pi, tmp);
			if(null != num) {
				l_pi.add_constant(name, num, false);
			}
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, MSG_INVALID_SYNTAX);
		}
	}
}
