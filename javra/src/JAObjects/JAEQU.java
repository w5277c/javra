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
	public JAEQU(ProgInfo l_pi, Line l_line, String l_value) {
		super(l_pi, l_line, l_value);
	
		parse();
	}
	
	@Override
	public void parse() {
		line.set_unparsed(false);
		
		String parts[] = value.split("=");
		String tmp = parts[0x00].trim();
		if(0x02 == parts.length && !tmp.isEmpty() && tmp.replaceAll(REGEX_CONST_NAME, "").isEmpty()) {
			String name = tmp;

			Integer register_id = pi.get_register(name);
			if(null != register_id) {
				pi.print(EMsgType.MSG_ERROR, line, JAObject.MSG_ALREADY_DEFINED, "as 'r" + Integer.toString(register_id) + "'");
				return;
			}
			Macro macros = pi.get_macros().get(name);
			if(null != macros) {
				pi.print(EMsgType.MSG_ERROR, line, JAObject.MSG_ALREADY_DEFINED, "at '" + macros.get_line().get_location() + "'");
				return;
			}

			tmp = parts[0x01].trim();
			Long num = Expr.parse(pi, line, tmp);
			if(null != num) {
				pi.add_constant(line, name, num, false);
			}
		}
		else {
			line.set_unparsed(true);
		}
	}
}
