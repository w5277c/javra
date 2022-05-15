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

public class JASet extends JAObject {
	public JASet(ProgInfo l_pi, Line l_line, String l_value) {
		super(l_pi, l_line, l_value);
		
		parse();
	}
	
	@Override
	public void parse() {
		super.parse();
		
		String parts[] = value.split("=");
		String name = parts[0x00].trim();
		if(0x02 == parts.length && !name.isEmpty() && name.replaceAll(REGEX_CONST_NAME, "").isEmpty()) {
			Long num = Expr.parse(pi, line, parts[0x01].trim());
			if(null != num) {
				pi.add_constant(line, name, num, true);
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
