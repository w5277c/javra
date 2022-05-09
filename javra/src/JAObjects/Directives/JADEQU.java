/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects.Directives;

import JAObjects.Expr;
import enums.EMsgType;
import main.Constant;
import main.Line;
import main.ProgInfo;

public class JADEQU extends JADirective {
	private	String	name;
	private	Constant	constant;
	private	long		num;
	
	public JADEQU(ProgInfo l_pi, Line l_line) {
		String parts[] = l_line.get_value().split("=");
		String tmp = parts[0x00].trim().toLowerCase();
		if(0x02 == parts.length && !tmp.isEmpty() && tmp.replaceAll(REGEX_CONST_NAME, "").isEmpty()) {
			name = tmp;
			if(is_undefined(l_pi, l_line, name)) {
				tmp = parts[0x01].trim().toLowerCase();
				Long _num = Expr.parse(l_pi, l_line, tmp);
				if(null == _num) {
					constant = l_pi.get_constants().get(tmp);
					if(null == constant) {
						l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_UNKNOWN_CONSTANT);
					}
				}
				else {
					num = _num;
				}
				l_pi.get_constants().put(name, new Constant(l_line, name, (null == constant ? num : constant.get_num())));
			}
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_INVALID_SYNTAX);
		}
	}
}
