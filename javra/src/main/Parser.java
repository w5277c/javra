/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
07.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import JAObjects.JAExit;
import common.Macro;
import JAObjects.JAObject;
import enums.EMsgType;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Parser {
	public	static	final	String	COMMENT1		= "//";
	public	static	final	String	COMMENT2		= ";";
	public	static	final	String	MULTILINE	= "\\";

	public Parser(ProgInfo l_pi, String l_filename, File l_file) throws Exception {
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
			if(str.isEmpty()) {
				line_number++;
				continue;
			}
			
			if(null == line) {
				line = new Line(l_filename, line_number++, str);
				if(!line_parse(l_pi, line)) {
					break;
				}
			}
			else {
				line.append(str);
				line_number++;
			}

			if(!str.endsWith(MULTILINE)) {
				line = null;
			}
		}
		scanner.close();
		
		ii = l_pi.exch_ii(ii);
		if(0 != ii.get_blockcntr()) {
			l_pi.print(EMsgType.MSG_WARNING, null, "some of.if/.ifdef/.ifndef not correctly closed(cntr:" + ii.get_blockcntr() + ")");
		}
	}
	
	public static boolean line_parse(ProgInfo l_pi, Line l_line) {
		Macro cur_macros = l_pi.get_cur_macros();
		if(null != cur_macros && !l_line.get_text().equalsIgnoreCase(".endmacro") && !l_line.get_text().equalsIgnoreCase(".endm")) {
			cur_macros.get_body().add(l_line);
		}
		else {
			try {
				JAObject jaobj = JAObject.parse(l_pi, l_line);
				if(null != jaobj) {
					l_pi.add_object(jaobj);
				}
				
				if(jaobj instanceof JAExit) {
					return false;
				}
			}
			catch(Exception ex) {
				System.out.println("Exception at " + l_line.get_location());
				ex.printStackTrace();
			}
		}
		return !l_pi.is_terminating();
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
}
