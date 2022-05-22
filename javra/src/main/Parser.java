/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
07.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import JAObjects.JAExit;
import JAObjects.JAMacro;
import JAObjects.JAObject;
import enums.EMsgType;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Parser {
	public	static	final	String	COMMENT1			= "//";
	public	static	final	String	COMMENT2			= ";";
	public	static	final	String	COMMENT_START	= "/*";
	public	static	final	String	COMMENT_END		= "*/";
	public	static	final	String	MULTILINE		= "\\";
	
	private	static			int		line_cntr	= 0;
	
	public Parser(ProgInfo l_pi, String l_filename, File l_file) throws Exception {
		boolean comment_block = false;
		
		IncludeInfo ii = l_pi.exch_ii(new IncludeInfo(l_filename));

		Scanner scanner = new Scanner(l_file, StandardCharsets.UTF_8.name());
		int line_number = 0x01;
		
		Line line = null;
		while (scanner.hasNextLine()) {
			String str = scanner.nextLine().trim();
			int comment_pos = get_pos(str, COMMENT1, COMMENT2);
			if(-1 != comment_pos) {
				str=str.substring(0x00, comment_pos).trim();
			}
			
			if(!comment_block) {
				comment_pos = get_pos(str, COMMENT_START);
				if(-1 != comment_pos) {
					str=str.substring(0x00, comment_pos).trim();
					comment_block = true;
				}
			}
			else {
				comment_pos = get_pos(str, COMMENT_END);
				if(-1 != comment_pos) {
					str=str.substring(0, comment_pos).trim();
					comment_block = false;
				}
				else {
					line_number++;
					continue;
				}
			}

			if(str.isEmpty()) {
				line_number++;
				continue;
			}
			
			if(null == line) {
				line = new Line(l_filename, line_number++, str);
				
				JAObject jaobj = line_parse(l_pi, line);
				if(null != jaobj) {
					line_cntr++;
					
					l_pi.add_object(jaobj);
					
					if(jaobj instanceof JAExit) {
						break;
					}
				}
			}
			else {
				line.append(str);
				line_number++;
			}

			if(!str.endsWith(MULTILINE)) {
				line = null;
			}
			
			if(l_pi.is_terminating()) {
				break;
			}
		}
		scanner.close();
		
		ii = l_pi.exch_ii(ii);
		if(!l_pi.is_terminating() && 0 != ii.get_blockcntr()) {
			l_pi.print(EMsgType.MSG_WARNING, null, "some of.if/.ifdef/.ifndef not correctly closed(cntr:" + ii.get_blockcntr() + ")");
		}
	}
	
	public static JAObject line_parse(ProgInfo l_pi, Line l_line) {
		JAMacro cur_macro = l_pi.get_cur_macro();
		if(null != cur_macro && !l_line.get_text().equalsIgnoreCase(".endmacro") && !l_line.get_text().equalsIgnoreCase(".endm")) {
			cur_macro.add_line(l_line);
		}
		else {
			try {
				return JAObject.parse(l_pi, l_line);
			}
			catch(Exception ex) {
				System.out.println("Exception at " + l_line.get_location());
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	public static int get_pos(String l_str, String... l_substrs) {
		int pos = -1;
		for(String substr : l_substrs) {
			int _pos = l_str.indexOf(substr);
			if(-1 != _pos && (-1 == pos || _pos < pos)) {
				pos = _pos;
			}
		}
		return pos;
	}
	
	public static int get_line_qnt() {
		return line_cntr;
	}
}
