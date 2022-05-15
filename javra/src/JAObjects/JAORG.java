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
		
		parse();
	}
	
	@Override
	public void parse() {
		line.set_unparsed(false);
		
		if(!value.isEmpty()) {
			Long addr = Expr.parse(pi, line, value);
			if(null == addr) {
				pi.print(EMsgType.MSG_ERROR, line, MSG_INVALID_NUMBER);
			}
			else {
				DataBlock exist_datablock = pi.get_cur_segment().set_block(addr.intValue());
				if(null != exist_datablock) {
					pi.print(EMsgType.MSG_ERROR, line, "ORG " + addr + " already defined");
				}
			}
		}
		else {
			pi.print(EMsgType.MSG_ERROR, line, MSG_MISSING_PARAMETERS);
		}
	}
}
