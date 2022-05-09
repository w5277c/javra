/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import common.Expr;
import JAObjects.JAObject;
import static JAObjects.JAObject.MSG_ALREADY_DEFINED;
import enums.EMsgType;
import main.Constant;
import main.Line;
import main.Macro;
import main.ProgInfo;

public class JASet extends JAObject {
	private	String	name;
	private	Constant	constant;
	private	long		num;
	
	public JASet(ProgInfo l_pi, Line l_line) {
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
				l_pi.get_constants().put(name, new Constant(l_line, name, (null == constant ? num : constant.get_num()), true));
			}
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_INVALID_SYNTAX);
		}
	}
	
	@Override
	protected boolean is_undefined(ProgInfo l_pi, Line l_line, String l_name) {
		Constant contant = l_pi.get_constants().get(l_name);
		if(null != contant && !contant.is_redef()) {
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_ALREADY_DEFINED, "at '" + contant.get_line().get_location() + "'");
			return false;
		}
		Integer register_id = get_register(l_pi, l_name);
		if(null != register_id) {
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_ALREADY_DEFINED, "as 'r" + Integer.toString(register_id) + "' register");
			return false;
		}
		Macro macros = l_pi.get_macros().get(l_name);
		if(null != macros) {
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_ALREADY_DEFINED, "at '" + macros.get_line().get_location() + "'");
			return false;
		}
		
		//TODO добавить остальне проверки
		
		return true;
	}

}
