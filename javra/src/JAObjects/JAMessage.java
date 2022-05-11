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
	public JAMessage(ProgInfo l_pi, Line l_line, EMsgType l_msg_type) throws Exception {
		StringBuilder sb = new StringBuilder();
		
		String value = l_line.get_value();
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
					l_pi.print(EMsgType.MSG_ERROR, JAObject.MSG_INVALID_SYNTAX);
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
					sb.append(Expr.parse(l_pi, value.toLowerCase().trim().toLowerCase()));
					value = "";
				}
				else {
					sb.append(Expr.parse(l_pi, value.substring(0x00, expr_length).trim().toLowerCase()));
					value = value.substring(expr_length);
				}
			}
			if(!value.isEmpty()) {
				if(',' != value.charAt(0x00)) {
					l_pi.print(EMsgType.MSG_ERROR, JAObject.MSG_INVALID_SYNTAX);
					sb = null;
					break;
				}
				value = value.substring(0x01).trim();
			}
		}
		if(null != sb) {
			l_pi.print(l_msg_type, sb.toString());
		}
	}
}
