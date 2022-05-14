/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/

package JAObjects;

import enums.EMnemonic;
import enums.EMsgType;
import main.Line;
import common.Macro;
import java.io.OutputStream;
import main.ProgInfo;

public class JAObject {
	public	final	static	String	MSG_INVALID_NUMBER		= "invalid number";
	public	final	static	String	MSG_INVALID_SYNTAX		= "invalid syntax";
	public	final	static	String	MSG_ALREADY_DEFINED		= "already defined";
	public	final	static	String	MSG_UNKNOWN_LEXEME		= "unknown lexeme";
	public	final	static	String	MSG_UNKNOWN_CONSTANT		= "unknown constant";
	public	final	static	String	MSG_ABSENT_FILE			= "absent_file";
	public	final	static	String	MSG_MISSING					= "missing ";
	public	final	static	String	MSG_UNSUPPORTED			= "unsupported ";
	public	final	static	String	MSG_ILLEGAL_OPERATOR		= "illegal operator: ";
	public	final	static	String	MSG_DIVISION_BY_ZERO		= "division by zero";
	public	final	static	String	MSG_WRONG_REGISTER		= "wrong register";
	
	public	final	static	String	MSG_DIRECTIVE_GARBAGE	= "Garbage after directive";
	public	final	static	String	MSG_MISSING_PARAMETERS	= "Directive missing parameter";
	public	final	static	String	MSG_LABEL_GARBAGE			= "Garbage after label";
	public	final	static	String	REGEX_CONST_NAME			= "[_|a-z][_\\d|a-z]*";
	public	final	static	String	REGEX_LABEL_NAME			= "[_|a-z][_\\d|a-z]*:";
	
	protected	ProgInfo	pi;
	protected	Line		line;
	protected	String	value;
	
	protected JAObject(ProgInfo l_pi, Line l_line, String l_value) {
		pi = l_pi;
		line = l_line;
		value = l_value;
	}
	
	public static JAObject parse(ProgInfo l_pi, Line l_line) throws Exception {
		String parts[] = l_line.get_text().split("\\s", 0x02);
		String name = parts[0x00].trim().toLowerCase();
		String value = (0x02 == parts.length ? value = parts[0x01].trim().toLowerCase() : "");

		if(!name.isEmpty()) {
			if(name.equals("#")) {
				return null;
			}
			
			if(null != l_pi.get_ii() && l_pi.get_ii().is_blockskip()) {
				switch(name) {
					case ".ifdef":
						return new JAIfDef(l_pi, l_line, value);
					case ".ifndef":
						return new JAIfNDef(l_pi, l_line, value);
					case ".if":
						return new JAIf(l_pi, l_line, value);
					case ".endif":
						return new JAEndIf(l_pi, l_line, value);
					case ".else":
						return new JAElse(l_pi, l_line, value);
					case ".elseif":
						return new JAElseIf(l_pi, l_line, value);
					default:
						int g = 0;
				}
			}
			else {
				switch(name) {
					case ".equ":
						return new JAEQU(l_pi, l_line, value);
					case ".set":
						return new JASet(l_pi, l_line, value);
					case ".org":
						return new JAORG(l_pi, l_line, value);
					case ".include":
						return new JAInclude(l_pi, l_line, value);
					case ".device":
						return new JADevice(l_pi, l_line, value);
					case ".ifdef":
						return new JAIfDef(l_pi, l_line, value);
					case ".ifndef":
						return new JAIfNDef(l_pi, l_line, value);
					case ".endif":
						return new JAEndIf(l_pi, l_line, value);
					case ".if":
						return new JAIf(l_pi, l_line, value);
					case ".else":
						return new JAElse(l_pi, l_line, value);
					case ".elseif":
						return new JAElseIf(l_pi, l_line, value);
					case ".message":
						return new JAMessage(l_pi, l_line, value, EMsgType.MSG_DMESSAGE);
					case ".warning":
						return new JAMessage(l_pi, l_line, value, EMsgType.MSG_DWARNING);
					case ".error":
						return new JAMessage(l_pi, l_line, value, EMsgType.MSG_DERROR);
					case ".def":
						return new JADef(l_pi, l_line, value);
					case ".undef":
						return new JAUndef(l_pi, l_line, value);
					case ".macro":
						return new JAMacro(l_pi, l_line, value, true);
					case ".endm":
					case ".endmacro":
						return new JAMacro(l_pi, l_line, value, false);
					case ".db":
						return new JAData(l_pi, l_line, value, 0x01);
					case ".dw":
						return new JAData(l_pi, l_line, value, 0x02);
					case ".dd":
						return new JAData(l_pi, l_line, value, 0x04);
					case ".dq":
						return new JAData(l_pi, l_line, value, 0x08);
					case ".exit":
						return new JAExit(l_pi, l_line, value);
					case ".list":
						return new JAList(l_pi, l_line, value);
					case ".nolist":
						return new JANoList(l_pi, l_line, value);
					case ".listmac":
						return new JAListMac(l_pi, l_line, value);
					case ".overlap":
						return new JAOverlap(l_pi, l_line, value);
					case ".nooverlap":
						return new JANoOverlap(l_pi, l_line, value);

						//TODO .byte, .cseg, .dseg, .eseg, .csegsize(for AT94K), .elif
						
					default:
						if(name.startsWith("#")) {
							l_pi.print(EMsgType.MSG_WARNING, l_line, MSG_UNSUPPORTED);
						}
						else if(name.replaceAll(REGEX_LABEL_NAME, "").isEmpty()) {
							return new JALabel(l_pi, l_line, value, name.substring(0x00, name.length()-0x01));
						}
						else {
							EMnemonic em = EMnemonic.fromName(name);
							if(null != em) {
								return new JAMnenomic(l_pi, l_line, value, em);
							}
							else {
								Macro macro = l_pi.get_macros().get(name);
								if(null != macro) {
									macro.parse(l_pi, value);
								}
								else {
									l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_UNKNOWN_LEXEME);
								}
							}
						}
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
	
	public Line get_line() {
		return line;
	}
	
	public void write_list(OutputStream l_os) throws Exception {
		l_os.write((line.get_text() + "\n").getBytes("UTF-8"));
	}
}
