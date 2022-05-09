/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/

package JAObjects;

import JAObjects.Directives.JADirective;
import JAObjects.Directives.JALabel;
import enums.EMsgType;
import main.Constant;
import main.Line;
import main.Macro;
import main.ProgInfo;

public class JAObject {
	public	final	static	String	MSG_INVALID_NUMBER		= "invalid number";
	public	final	static	String	MSG_INVALID_SYNTAX		= "invalid syntax";
	public	final	static	String	MSG_ALREADY_DEFINED		= "already defined";
	public	final	static	String	MSG_UNKNOWN_DIRECTIVE	= "unknown directive";
	public	final	static	String	MSG_UNKNOWN_CONSTANT		= "unknown constant";
	public	final	static	String	MSG_ABSENT_FILE			= "absent_file";
	public	final	static	String	MSG_MISSING					= "missing ";
	public	final	static	String	MSG_UNSUPPORTED			= "unsupported ";
	public	final	static	String	MSG_ILLEGAL_OPERATOR		= "illegal operator: ";
	public	final	static	String	MSG_DIVISION_BY_ZERO		= "division by zero";
	public	final	static	String	MSG_WRONG_REGISTER		= "wrong register";
			  
	public	final	static	String	REGEX_CONST_NAME			= "[_|a-z][_\\d|a-z]*";
	public	final	static	String	REGEX_LABEL_NAME			= "[_|a-z][_\\d|a-z]*:";
	
	protected JAObject() {
	}
	
	public static JAObject parse(ProgInfo l_pi, Line l_line) throws Exception {
		String tmp = l_line.get_key().trim().toLowerCase();
		if(!tmp.isEmpty()) {
			if(tmp.startsWith(".")) {
				return JADirective.parse(l_pi, l_line);
			}
			if(!l_pi.get_ii().is_blockskip()) {
				if(tmp.replaceAll(REGEX_LABEL_NAME, "").isEmpty()) {
					new JALabel(l_pi, l_line, tmp.substring(0x00, tmp.length()-0x01));
				}
			}
		}
		return null;
	}
	
	protected Long parse_addr(String l_value) {
		try {
			if(l_value.toLowerCase().startsWith("0x")) {
				return Long.parseLong(l_value.substring(0x02), 0x10);
			}
			else {
				return Long.parseLong(l_value);
			}
		}
		catch(Exception ex) {
		}
		return null;
	}
	
	protected Long parse_num(String l_value) {
		try {
			if(l_value.toLowerCase().startsWith("0x")) {
				return Long.parseLong(l_value.substring(0x02), 0x10);
			}
			else if(l_value.toLowerCase().startsWith("0b")) {
				return Long.parseLong(l_value.substring(0x02), 0x02);
			}
			else {
				return Long.parseLong(l_value);
			}
			//TODO ADD OCT
		}
		catch(Exception ex) {
		}
		return null;
	}
	
	protected String parse_string(String l_value) {
		try {
			String tmp = l_value.trim();
			if((tmp.startsWith("\"") && tmp.endsWith("\"")) || (tmp.startsWith("'") && tmp.endsWith("'"))) {
				return tmp.substring(0x01, tmp.length()-0x01);
			}
		}
		catch(Exception ex) {
		}
		return null;
	}
	
	protected Integer get_register(ProgInfo l_pi, String l_str) {
		if(l_str.startsWith("r") && l_str.length() > 0x01 && l_str.length() <= 0x03 && l_str.substring(0x01).replaceAll("\\d", "").isEmpty()) {
			return Integer.parseInt(l_str.substring(0x01));
		}
		else {
			for(int id=0x00; id < 0x20; id++) {
				
				if(l_pi.get_registers()[id].equals(l_str)) {
					return id;
				}
			}
		}
		return null;
	}
	
	protected boolean is_undefined(ProgInfo l_pi, Line l_line, String l_name) {
		Constant contant = l_pi.get_constants().get(l_name);
		if(null != contant) {
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_ALREADY_DEFINED, "at '" + contant.get_line().get_location() + "'");
			return false;
		}
		Integer register_id = get_register(l_pi, l_name);
		if(null != register_id) {
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_ALREADY_DEFINED, "as 'r" + Integer.toString(register_id) + "'");
			return false;
		}
		Macro macros = l_pi.get_macros().get(l_name);
		if(null != macros) {
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_ALREADY_DEFINED, "at '" + macros.get_line().get_location() + "'");
			return false;
		}
		
		//TODO добавить остальне проверки
		
		return true;
	}
}
