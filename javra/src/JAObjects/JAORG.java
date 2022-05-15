/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import common.Expr;
import enums.EMsgType;
import main.DataBlock;
import main.Line;
import main.ProgInfo;

public class JAORG extends JAObject {
	public JAORG(ProgInfo l_pi, Line l_line, String l_value) {
		super(l_pi, l_line, l_value);
		
		if(!value.isEmpty()) {
			Long _address = Expr.parse(pi, line, value);
			if(null != _address) {
				pi.get_segment().set_addr(line, _address.intValue());
			}
			else {
				pi.print(EMsgType.MSG_ERROR, line, JAObject.MSG_UNKNOWN_LEXEME, " '" + line.get_failpart() + "'");
			}
		}
		else {
			pi.print(EMsgType.MSG_ERROR, line, MSG_MISSING_PARAMETERS);
		}
	}
}
