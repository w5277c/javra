/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package common;

import enums.EMsgType;
import java.util.HashMap;
import java.util.LinkedList;
import main.Constant;
import main.Line;
import main.Parser;
import main.ProgInfo;

public class Macro {
	private	String							name;
	private	Line								line;
	private	Integer							size		= null;
	private	LinkedList<Line>				body		= new LinkedList<>();
	private	HashMap<String,Constant>	labels	= new HashMap<>();
	
	public Macro(Line l_line, String l_name) {
		name = l_name;
	}
	
	public void parse(ProgInfo l_pi, String l_value) {
		LinkedList<String> params = new LinkedList<>();
		if(null != l_value && !l_value.isEmpty()) {
			for(String _str : l_value.split("\\s")) {
				Integer register = l_pi.get_register(_str);
				if(null != register) {
					params.add("r" + register);
				}
				else {
					Long value = Expr.parse(l_pi, _str);
					if(null != value) {
						params.add(value.toString());
					}
				}
			}
		}
		
		if(10 < params.size()) {
			l_pi.print(EMsgType.MSG_WARNING, "AVRASM2 only supportrts 10 parameters");
		}
		
		for(Line m_line : body) {
			String str = m_line.get_text();
			for(int param_id=0x00; param_id < params.size(); param_id++) {
				if(str.contains("@" + param_id)) {
					str = str.replaceAll("@" + param_id, params.get(param_id));
				}
			}

			Parser.line_parse(l_pi, new Line(l_pi.get_cur_line().get_filename(), l_pi.get_cur_line().get_number(), str));
		}
	}
	
	public Line get_line() {
		return line;
	}
	
	public LinkedList<Line> get_body() {
		return body;
	}
	
	public HashMap<String,Constant> get_labels() {
		return labels;
	}
}
