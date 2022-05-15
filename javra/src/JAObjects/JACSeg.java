/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
15.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import enums.EMsgType;
import main.Line;
import main.ProgInfo;

public class JACSeg extends JAObject {
	public JACSeg(ProgInfo l_pi, Line l_line, String l_value) {
		super(l_pi, l_line, l_value);
		
		if(!value.isEmpty()) {
			l_pi.print(EMsgType.MSG_ERROR, line, MSG_DIRECTIVE_GARBAGE);
		}
		else {
			pi.set_segment(pi.get_cseg());
		}
	}
}
