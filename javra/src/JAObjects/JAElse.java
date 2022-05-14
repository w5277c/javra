/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import enums.EMsgType;
import main.Line;
import main.ProgInfo;

public class JAElse extends JAObject {
	public JAElse(ProgInfo l_pi, Line l_line, String l_value) throws Exception {
		super(l_pi, l_line, l_value);
		
		if(value.isEmpty()) {
			l_pi.get_ii().block_skip_invert(line);
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, line, MSG_DIRECTIVE_GARBAGE);
		}
	}
}
