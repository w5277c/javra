/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import main.Constant;
import main.Line;
import main.ProgInfo;

public class JALabel extends JAObject {
	public JALabel(ProgInfo l_pi, Line l_line, String l_name) throws Exception {
		if(is_undefined(l_pi, l_line, l_name)) {
			l_pi.add_constant(l_line, new Constant(l_line, l_name, l_pi.get_cur_segment().get_datablock().get_addr()));
		}
	}
}
