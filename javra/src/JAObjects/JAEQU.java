/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import common.Expr;
import enums.EMsgType;
import main.Line;
import main.ProgInfo;

public class JAEQU extends JAObject {
	public JAEQU(ProgInfo l_pi, Line l_line, String l_value) {
		super(l_pi, l_line, l_value);
	
		parse();
	}
	
	@Override
	public void parse() {
		super.parse();
		
		String parts[] = value.split("=");
		String name = parts[0x00].trim();
		if(0x02 == parts.length && !name.isEmpty() && name.replaceAll(REGEX_CONST_NAME, "").isEmpty()) {

			Integer register_id = pi.get_register(name);
			if(null != register_id) {
				pi.print(EMsgType.MSG_ERROR, line, JAObject.MSG_ALREADY_DEFINED, "as 'r" + Integer.toString(register_id) + "'");
				return;
			}
			JAMacro macro = pi.get_macro(name);
			if(null != macro) {
				pi.print(EMsgType.MSG_ERROR, line, JAObject.MSG_ALREADY_DEFINED, "at '" + macro.get_line().get_location() + "'");
				return;
			}

			Long _value = Expr.parse(pi, line, parts[0x01].trim());
			if(null != _value) {
				pi.add_constant(line, name, _value, false);
			}
			else {
				expr_fail = true;
			}
		}
		else {
			pi.print(EMsgType.MSG_ERROR, line, MSG_INVALID_SYNTAX);
		}
	}
}
