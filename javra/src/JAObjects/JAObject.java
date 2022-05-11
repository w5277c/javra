/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/

package JAObjects;

import common.Mnemonic;
import enums.EMnemonic;
import enums.EMsgType;
import main.Constant;
import main.Line;
import common.Macro;
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
			  
	public	final	static	String	REGEX_CONST_NAME			= "[_|a-z][_\\d|a-z]*";
	public	final	static	String	REGEX_LABEL_NAME			= "[_|a-z][_\\d|a-z]*:";
	
	protected JAObject() {
	}
	
	public static JAObject parse(ProgInfo l_pi, Line l_line) throws Exception {
		String tmp = l_line.get_key().trim().toLowerCase();
		if(!tmp.isEmpty()) {
			if(tmp.equals("#")) {
				return null;
			}
			
			if(l_pi.get_ii().is_blockskip()) {
				switch(tmp) {
					case ".ifdef":
						return new JAIfDef(l_pi, l_line);
					case ".ifndef":
						return new JAIfNDef(l_pi, l_line);
					case ".if":
						return new JAIf(l_pi, l_line);
					case ".endif":
						return new JAEndIf(l_pi, l_line);
					case ".else":
						return new JAElse(l_pi, l_line);
					default:
						int g = 0;
				}
			}
			else {
				switch(tmp) {
					case ".equ":
						return new JAEQU(l_pi, l_line);
					case ".set":
						return new JASet(l_pi, l_line);
					case ".org":
						return new JAORG(l_pi, l_line);
					case ".include":
						return new JAInclude(l_pi, l_line);
					case ".device":
						return new JADevice(l_pi, l_line);
					case ".ifdef":
						return new JAIfDef(l_pi, l_line);
					case ".ifndef":
						return new JAIfNDef(l_pi, l_line);
					case ".endif":
						return new JAEndIf(l_pi, l_line);
					case ".else":
						return new JAElse(l_pi, l_line);
					case ".if":
						return new JAIf(l_pi, l_line);
					case ".message":
						return new JAMessage(l_pi, l_line, EMsgType.MSG_DMESSAGE);
					case ".warning":
						return new JAMessage(l_pi, l_line, EMsgType.MSG_DWARNING);
					case ".error":
						return new JAMessage(l_pi, l_line, EMsgType.MSG_DERROR);
					case ".def":
						return new JADef(l_pi, l_line);
					case ".undef":
						return new JAUndef(l_pi, l_line);
					case ".macro":
						return new JAMacro(l_pi, l_line, true);
					case ".endm":
					case ".endmacro":
						return new JAMacro(l_pi, l_line, false);
					case ".db":
						return new JAData(l_pi, l_line, 0x01);
					case ".dw":
						return new JAData(l_pi, l_line, 0x02);
					case ".dd":
						return new JAData(l_pi, l_line, 0x04);
					case ".dq":
						return new JAData(l_pi, l_line, 0x08);
					case ".exit":
						return new JAExit(l_pi, l_line);
					case ".list":
						return new JAList(l_pi, l_line);
					case ".nolist":
						return new JANoList(l_pi, l_line);
					case ".listmac":
						return new JAListMac(l_pi, l_line);
					case ".overlap":
						return new JAOverlap(l_pi, l_line);
					case ".nooverlap":
						return new JANoOverlap(l_pi, l_line);

						//TODO .byte, .cseg, .dseg, .eseg, .csegsize(for AT94K), .elif
						
					default:
						if(tmp.startsWith("#")) {
							l_pi.print(EMsgType.MSG_WARNING, MSG_UNSUPPORTED);
						}
						else if(tmp.replaceAll(REGEX_LABEL_NAME, "").isEmpty()) {
							return new JALabel(l_pi, l_line, tmp.substring(0x00, tmp.length()-0x01));
						}
						else {
							EMnemonic em = EMnemonic.fromName(tmp);
							if(null != em) {
								Mnemonic.parse(l_pi, em, l_line.get_value().trim().toLowerCase());
							}
							else {
								Macro macro = l_pi.get_macros().get(tmp);
								if(null != macro) {
									macro.parse(l_pi, l_line.get_value().trim().toLowerCase());
								}
								else {
									l_pi.print(EMsgType.MSG_ERROR, MSG_UNKNOWN_LEXEME);
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
}
