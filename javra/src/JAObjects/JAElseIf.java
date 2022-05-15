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

public class JAElseIf extends JAObject {
	public JAElseIf(ProgInfo l_pi, Line l_line, String l_value) throws Exception {
		super(l_pi, l_line, l_value);
		
		parse();
	}
	
	@Override
	public void parse() {
		super.parse();
		
		if(!value.isEmpty()) {
			Long _value = Expr.parse(pi, line, value);
			if(null == _value) {
				expr_fail = true;
			}
			else {
				pi.get_ii().block_elseif(0x00 == _value);
			}
		}
		else {
			pi.print(EMsgType.MSG_ERROR, line, MSG_MISSING_PARAMETERS);
		}
	}
}
