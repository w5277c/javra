/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
15.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import common.Expr;
import enums.EMsgType;
import java.io.OutputStream;
import java.util.LinkedList;
import main.Line;
import main.Parser;
import main.ProgInfo;

public class JAMacroBlock extends JAObject {
	private	JAMacro					macro;
	private	Integer					address;
	private	LinkedList<JAObject>	objects	= new LinkedList<>();
	
	public JAMacroBlock(ProgInfo l_pi, Line l_line, String l_value, JAMacro l_macro) {
		super(l_pi, l_line, l_value);
		
		macro = l_macro;
		
		parse();
	}
	
	@Override
	public void parse() {
		super.parse();
		
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
			address = pi.get_cseg().get_cur_block().get_addr();
		}
		else {
			pi.get_cseg().get_cur_block().set_addr(address);
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
			if(str.contains("?_c5_suspend__body?")) {
				int t =1;
			}
			
			JAObject obj = Parser.line_parse(pi, new Line(line.get_filename(), line.get_line_number(), index, str));
			if(null != obj) {
				objects.add(obj);
			}
		}
	}
	
	@Override
	public void write_list(OutputStream l_os) throws Exception {
		l_os.write(("C:" + String.format("%06X", address) + "   + " + line.get_text() + "\n").getBytes("UTF-8"));
		for(JAObject obj : objects) {
			obj.write_list(l_os);
		}
		l_os.write((";ENDM\n").getBytes("UTF-8"));
	}
}
