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
import main.Label;
import main.Line;
import main.Parser;
import main.ProgInfo;

public class Macro {
	private	String							name;
	private	Line								line;
	private	Integer							size			= null;
	private	LinkedList<Line>				body			= new LinkedList<>();
	private	HashMap<String,Constant>	constants	= new HashMap<>();
	private	HashMap<String,Label>		labels		= new HashMap<>();
	
	public Macro(Line l_line, String l_name) {
		line = l_line;
		name = l_name;
	}
	
	public void parse(ProgInfo l_pi, String l_value) {
		line.set_unparsed(false);
		
		l_pi.set_expand_macro(this);
		
		LinkedList<String> params = new LinkedList<>();
		if(null != l_value && !l_value.isEmpty()) {
			for(String _str : l_value.split("\\s")) {
				Integer register = l_pi.get_register(_str);
				if(null != register) {
					params.add("r" + register);
				}
				else {
					Long value = Expr.parse(l_pi, line, _str);
					if(null != value) {
						params.add(value.toString());
					}
					else {
						//TODO UNPARSED???
						params.add(_str);
					}
				}
			}
		}
		
		if(10 < params.size()) {
			l_pi.print(EMsgType.MSG_WARNING, line, "AVRASM2 only supportrts 10 parameters");
		}
		
		for(Line m_line : body) {
			String str = m_line.get_text();
			for(int param_id=0x00; param_id < params.size(); param_id++) {
				if(str.contains("@" + param_id)) {
					str = str.replaceAll("@" + param_id, params.get(param_id));
				}
			}

			Parser.line_parse(l_pi, new Line(line.get_filename(), line.get_number(), str));
		}
		
		l_pi.set_expand_macro(null);		
	}
	
	public Line get_line() {
		return line;
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
