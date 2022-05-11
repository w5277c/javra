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

public class JAORG extends JAObject {
	public JAORG(ProgInfo l_pi, Line l_line) {
		Long addr = Expr.parse(l_pi, l_line.get_value().trim().toLowerCase());
		if(null == addr) {
			l_pi.print(EMsgType.MSG_ERROR, MSG_INVALID_NUMBER);
		}
		else {
			l_pi.get_cur_segment().add_datablock(addr);
		}
	}
}
