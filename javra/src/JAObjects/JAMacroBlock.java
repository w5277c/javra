/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
15.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import common.Expr;
import enums.EMsgType;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import main.Constant;
import main.Label;
import main.Line;
import main.Parser;
import main.ProgInfo;

public class JAMacroBlock extends JAObject {
	private	JAMacro							macro;
	private	Integer							address;
	private	LinkedList<JAObject>			objects		= new LinkedList<>();
	private	HashMap<String,Constant>	constants	= new HashMap<>();
	private	HashMap<String,Label>		labels		= new HashMap<>();

	public JAMacroBlock(ProgInfo l_pi, Line l_line, String l_value, JAMacro l_macro) {
		super(l_pi, l_line, l_value);
		
		macro = l_macro;
		
		parse();
	}
	
	@Override
	public void parse() {
		super.parse();
		
		objects.clear();
		LinkedList<String> params = new LinkedList<>();

		if(null != value && !value.isEmpty()) {
			for(String _str : value.split("\\s")) {
				Integer register = pi.get_register(_str);
				if(null != register) {
					params.add("r" + register);
				}
				else {
					Long value = Expr.parse(pi, line, _str);
					if(null != value) {
						params.add(value.toString());
					}
					else {
						expr_fail = true;
						params.add(_str);
					}
				}
			}
		}

		if(null == address) {
			address = pi.get_cseg().get_cur_block(line).get_address();
		}
		else {
			pi.get_cseg().get_cur_block(line).set_addr(address);
		}
				
		
		if(!expr_fail) {
			if(10 < params.size()) {
				pi.print(EMsgType.MSG_WARNING, line, "AVRASM2 only supportrts 10 parameters");
			}
		}

		for(int index = 1; index <= macro.get_body().size(); index++) {
			String str = macro.get_body().get(index-0x01).get_text();
			for(int param_id=0x00; param_id < params.size(); param_id++) {
				if(str.contains("@" + param_id)) {
					str = str.replaceAll("@" + param_id, params.get(param_id));
				}
			}
			
			pi.set_macroblock(this);
			JAObject obj = Parser.line_parse(pi, new Line(line.get_filename(), line.get_line_number(), index, str));
			if(null != obj) {
				objects.add(obj);
			}
			pi.set_macroblock(null);
		}
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
	
	@Override
	public void write_list(OutputStream l_os) throws Exception {
		l_os.write(("C:" + String.format("%06X", address) + "   +    " + line.get_text() + "\n").getBytes("UTF-8"));
		for(JAObject obj : objects) {
			obj.write_list(l_os);
		}
		l_os.write((";ENDM\n").getBytes("UTF-8"));
	}
}
