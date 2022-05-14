/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
----------------------------------------------------------------------------------------------------------------------------------------------------------------
//TODO '\ooo' (ooo = octal number) and '\xhh' (hh = hex number) are also recognized.
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import common.Expr;
import enums.EMsgType;
import main.Line;
import main.ProgInfo;

public class JAMessage extends JAObject {
	public JAMessage(ProgInfo l_pi, Line l_line, String l_value, EMsgType l_msg_type) throws Exception {
		super(l_pi, l_line, l_value);
		
		StringBuilder sb = new StringBuilder();
		
		if(!value.isEmpty()) {
			while(!value.isEmpty()) {
				int str_length = -1;
				if('\"' == value.charAt(0x00)) {
					for (int _pos = 0x01; _pos < value.length(); _pos++) {
						if ('\"' == value.charAt(_pos) && (0x01 == _pos || '\\' != value.charAt(_pos-0x01))) {
							str_length = _pos;
							break;
						}
					}
					if(-1 == str_length) {
						l_pi.print(EMsgType.MSG_ERROR, line, JAObject.MSG_INVALID_SYNTAX);
						sb = null;
						break;
					}
				}
				if(-1 != str_length) {
					sb.append(value.substring(0x01, str_length));
					value = value.substring(0x01+str_length).trim();
				}
				else {
					int expr_length = value.indexOf(',');
					if(-1 == expr_length) {
						Long expr = Expr.parse(l_pi, line, value.toLowerCase().trim().toLowerCase());
						if(null != expr) {
							sb.append(expr);
						}
						else {
							line.set_unparsed();
						}
						value = "";
					}
					else {
						Long expr = Expr.parse(l_pi, line, value.substring(0x00, expr_length).trim().toLowerCase());
						if(null != expr) {
							sb.append(expr);
						}
						else {
							line.set_unparsed();
						}
						value = value.substring(expr_length);
					}
				}
				if(!value.isEmpty()) {
					if(',' != value.charAt(0x00)) {
						l_pi.print(EMsgType.MSG_ERROR, line, JAObject.MSG_INVALID_SYNTAX);
						sb = null;
						break;
					}
					value = value.substring(0x01).trim();
				}
			}
			if(null != sb && !line.is_unparsed()) {
				l_pi.print(l_msg_type, line, sb.toString());
			}
		}
		else {
			pi.print(EMsgType.MSG_ERROR, line, MSG_MISSING_PARAMETERS);
		}
	}
}
