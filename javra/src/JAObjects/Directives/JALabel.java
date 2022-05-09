/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects.Directives;

import enums.EMsgType;
import main.Constant;
import main.Line;
import main.ProgInfo;

public class JALabel extends JADirective {
	public JALabel(ProgInfo l_pi, Line l_line, String l_name) throws Exception {
		if(is_undefined(l_pi, l_line, l_name)) {
			l_pi.get_constants().put(l_name, new Constant(l_line, l_name, l_pi.get_cur_segment().get_datablock().get_addr()));
		}
	}
}
