/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
----------------------------------------------------------------------------------------------------------------------------------------------------------------
TODO:defined(__ATmega48__)
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import common.Expr;
import enums.EMsgType;
import main.Line;
import main.ProgInfo;

public class JAIf extends JAObject {
	public JAIf(ProgInfo l_pi, Line l_line, String l_value) throws Exception {
		super(l_pi, l_line, l_value);
		
		parse();
	}
	
	@Override
	public void parse() {
		line.set_unparsed(false);
		
		if(!value.isEmpty()) {
			if(pi.get_ii().is_blockskip()) {
				pi.get_ii().block_start(line, false);
			}
			else {
				Long _tmp = Expr.parse(pi, line, value);
				if(null == _tmp) {
					line.set_unparsed(true);
				}
				else {
					pi.get_ii().block_start(line, 0x00 == _tmp);
				}
			}
		}
		else {
			pi.print(EMsgType.MSG_ERROR, line, MSG_MISSING_PARAMETERS);
		}
	}
}
