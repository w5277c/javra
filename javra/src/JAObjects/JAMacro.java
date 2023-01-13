/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import enums.EMsgType;
import java.util.HashMap;
import java.util.LinkedList;
import main.Constant;
import main.IncludeInfo;
import main.Label;
import main.Line;
import main.ProgInfo;

public class JAMacro extends JAObject {
	private	IncludeInfo						ii;
	private	LinkedList<Line>				body			= new LinkedList<>();

	public JAMacro(ProgInfo l_pi, Line l_line, String l_value, boolean l_start) throws Exception {
		super(l_pi, l_line, l_value);
		
		ii = l_pi.get_ii();
		
		if(l_start) {
			if(!value.isEmpty()) {
				if(pi.is_undefined(line, value, false)) {
					if(!pi.create_macro(this, value)) {
						pi.print(EMsgType.MSG_ERROR, line, MSG_MISSING, "found no closing .macro");
					}
				}
			}
			else {
				l_pi.print(EMsgType.MSG_ERROR, line, MSG_MISSING_PARAMETERS);
			}
		}
		else {
			if(value.isEmpty()) {
				if(!pi.close_macro()) {
					pi.print(EMsgType.MSG_ERROR, line, MSG_MISSING, "no .MACRO found before .endmacro");
				}
			}
			else {
				pi.print(EMsgType.MSG_ERROR, line, MSG_DIRECTIVE_GARBAGE);
			}
		}
	}

	public void add_line(Line l_line) {
		body.add(l_line);
	}
	public LinkedList<Line> get_body() {
		return body;
	}
	
	public IncludeInfo get_ii() {
		return ii;
	}
}
