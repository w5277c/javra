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
import main.Label;
import main.Line;
import main.ProgInfo;

public class JAMacro extends JAObject {
	private	LinkedList<Line>				body			= new LinkedList<>();
	private	HashMap<String,Constant>	constants	= new HashMap<>();
	private	HashMap<String,Label>		labels		= new HashMap<>();

	public JAMacro(ProgInfo l_pi, Line l_line, String l_value, boolean l_start) throws Exception {
		super(l_pi, l_line, l_value);
		
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
	
	public Label get_label(String l_name) {
		return labels.get(l_name);
	}
	public void add_label(Label l_label) {
		labels.put(l_label.get_name(), l_label);
	}
	
	public Constant get_constant(String l_name) {
		return constants.get(l_name);
	}
	public void add_constant(Constant l_constant) {
		constants.put(l_constant.get_name(), l_constant);
	}
}
