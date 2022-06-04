/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/

package JAObjects;

import enums.EMnemonic;
import enums.EMsgType;
import main.Line;
import java.io.OutputStream;
import main.ProgInfo;

public class JAObject {
	public	final	static	String	MSG_INVALID_NUMBER		= "invalid number";
	public	final	static	String	MSG_INVALID_SYNTAX		= "invalid syntax";
	public	final	static	String	MSG_ALREADY_DEFINED		= "already defined";
	public	final	static	String	MSG_UNKNOWN_LEXEME		= "Found no label/variable/constant named";
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
	
	protected					ProgInfo	pi;
	protected					Line		line;
	protected					String	value;
	protected					boolean	expr_fail	= false;
	
	protected JAObject(ProgInfo l_pi, Line l_line, String l_value) {
		pi = l_pi;
		line = l_line;
		value = l_value;
	}
	
	public void parse() {
		expr_fail = false;
	}
	
	public static JAObject parse(ProgInfo l_pi, Line l_line) throws Exception {
		String parts[] = l_line.get_text().split("\\s", 0x02);
		String name = parts[0x00].trim().toLowerCase();
		String value = (0x02 == parts.length ? value = parts[0x01].trim() : "");

		if(!name.isEmpty()) {
			if(name.equals("#")) {
				return null;
			}
			
			if(null != l_pi.get_ii() && l_pi.get_ii().is_blockskip()) {
				switch(name) {
					case ".ifdef":
						return new JAIfDef(l_pi, l_line, value.toLowerCase());
					case ".ifndef":
						return new JAIfNDef(l_pi, l_line, value.toLowerCase());
					case ".if":
						return new JAIf(l_pi, l_line, value.toLowerCase());
					case ".endif":
						return new JAEndIf(l_pi, l_line, value.toLowerCase());
					case ".else":
						return new JAElse(l_pi, l_line, value.toLowerCase());
					case ".elseif":
						return new JAElseIf(l_pi, l_line, value.toLowerCase());
					default:
				}
			}
			else {
				switch(name) {
					case ".equ":
						return new JAEQU(l_pi, l_line, value.toLowerCase());
					case ".set":
						return new JASet(l_pi, l_line, value.toLowerCase());
					case ".org":
						return new JAORG(l_pi, l_line, value.toLowerCase());
					case ".include":
						return new JAInclude(l_pi, l_line, value);
					case ".device":
						return new JADevice(l_pi, l_line, value.toLowerCase());
					case ".ifdef":
						return new JAIfDef(l_pi, l_line, value.toLowerCase());
					case ".ifndef":
						return new JAIfNDef(l_pi, l_line, value.toLowerCase());
					case ".endif":
						return new JAEndIf(l_pi, l_line, value.toLowerCase());
					case ".if":
						return new JAIf(l_pi, l_line, value.toLowerCase());
					case ".else":
						return new JAElse(l_pi, l_line, value.toLowerCase());
					case ".elseif":
					case ".elif":
						return new JAElseIf(l_pi, l_line, value.toLowerCase());
					case ".message":
						return new JAMessage(l_pi, l_line, value, EMsgType.MSG_DMESSAGE);
					case ".warning":
						return new JAMessage(l_pi, l_line, value, EMsgType.MSG_DWARNING);
					case ".error":
						return new JAMessage(l_pi, l_line, value, EMsgType.MSG_DERROR);
					case ".def":
						return new JADef(l_pi, l_line, value.toLowerCase());
					case ".undef":
						return new JAUndef(l_pi, l_line, value.toLowerCase());
					case ".macro":
						return new JAMacro(l_pi, l_line, value.toLowerCase(), true);
					case ".endm":
					case ".endmacro":
						return new JAMacro(l_pi, l_line, value.toLowerCase(), false);
					case ".db":
						return new JAData(l_pi, l_line, value, 0x01);
					case ".dw":
						return new JAData(l_pi, l_line, value, 0x02);
					case ".dd":
						return new JAData(l_pi, l_line, value, 0x04);
					case ".dq":
						return new JAData(l_pi, l_line, value, 0x08);
					case ".exit":
						return new JAExit(l_pi, l_line, value.toLowerCase());
					case ".list":
						return new JAList(l_pi, l_line, value.toLowerCase());
					case ".nolist":
						return new JANoList(l_pi, l_line, value.toLowerCase());
					case ".listmac":
						return new JAListMac(l_pi, l_line, value.toLowerCase());
					case ".overlap":
						return new JAOverlap(l_pi, l_line, value.toLowerCase());
					case ".nooverlap":
						return new JANoOverlap(l_pi, l_line, value.toLowerCase());
					case ".byte":
						return new JAByte(l_pi, l_line, value);
					case ".cseg":
						return new JACSeg(l_pi, l_line, value.toLowerCase());
					case ".dseg":
						return new JADSeg(l_pi, l_line, value.toLowerCase());
					case ".eseg":
						return new JAESeg(l_pi, l_line, value.toLowerCase());

						//TODO .csegsize(for AT94K),
						
					default:
						if(name.startsWith("#")) {
							l_pi.print(EMsgType.MSG_WARNING, l_line, MSG_UNSUPPORTED, name);
						}
						else if(name.replaceAll(REGEX_LABEL_NAME, "").isEmpty()) {
							return new JALabel(l_pi, l_line, value.toLowerCase(), name.substring(0x00, name.length()-0x01));
						}
						else {
							EMnemonic em = EMnemonic.fromName(name);
							if(null != em) {
								return new JAMnenomic(l_pi, l_line, value.toLowerCase(), em);
							}
							else {
								JAMacro macro = l_pi.get_macro(name);
								if(null != macro) {
									return new JAMacroBlock(l_pi, l_line, value.toLowerCase(), macro);
								}
								else {
									l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_UNKNOWN_LEXEME, " '" + name + "'");
								}
							}
						}
				}
			}
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
	public void set_line(Line l_line) {
		line = l_line;
	}
	
	public void write_list(OutputStream l_os) throws Exception {
		l_os.write((line.get_text() + "\n").getBytes("UTF-8"));
	}
	
	public boolean is_expr_fail() {
		return expr_fail;
	}

}
