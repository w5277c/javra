/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import common.Expr;
import JAObjects.JAObject;
import enums.EMsgType;
import main.Constant;
import main.Line;
import main.ProgInfo;

public class JASet extends JAObject {
	public JASet(ProgInfo l_pi, Line l_line) {
		String parts[] = l_line.get_value().split("=");
		String tmp = parts[0x00].trim().toLowerCase();
		if(0x02 == parts.length && !tmp.isEmpty() && tmp.replaceAll(REGEX_CONST_NAME, "").isEmpty()) {
			String name = tmp;
			if(is_undefined(l_pi, name)) {
				tmp = parts[0x01].trim().toLowerCase();
				Long num = Expr.parse(l_pi, l_line, tmp);
				if(null != num) {
					l_pi.add_constant(new Constant(l_line, name, num, true));
				}
			}
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, MSG_INVALID_SYNTAX);
		}
	}
}
