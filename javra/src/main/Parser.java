/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
07.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import JAObjects.JAObject;
import enums.EMsgType;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Parser {
	public	static	final	String	COMMENT1		= "//";
	public	static	final	String	COMMENT2		= ";";
	public	static	final	String	MULTILINE	= "\\";

	public Parser(ProgInfo l_pi, File l_file) throws Exception {
		int blockcntr = l_pi.get_blockcntr();
		
		l_pi.print(EMsgType.MSG_INFO, null, "Enter " + l_file.getCanonicalPath());
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
				line = new Line(l_file.getName(), line_number++, str);
				line_parse(l_pi, line);
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
		if(0 != (l_pi.get_blockcntr() - blockcntr)) {
			l_pi.print(EMsgType.MSG_WARNING, null, "some of.if/.ifdef/.ifndef not correctly closed(cntr:" + (l_pi.get_blockcntr() - blockcntr) + ")");
		}
		l_pi.set_blockcntr(blockcntr);
		
		l_pi.print(EMsgType.MSG_INFO, null, "Exit " + l_file.getCanonicalPath());
	}
	
	private void line_parse(ProgInfo l_pi, Line l_line) throws Exception {
		//Парсим .EQU&etc, .INCLUDE, IFDEF&etc, MACROSES, LABEL, MNEMONICS
		
		if(l_line.get_text().contains("PC4")) {
			int t = 0;
		}
		l_line.parse();
		
		JAObject jaobj = JAObject.parse(l_pi, l_line);
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	public static int get_pos(String l_str, String... l_substrs) {
		int pos = -1;
		for(String substr : l_substrs) {
			int _pos = l_str.indexOf(substr);
			if(-1 != _pos) {
				pos = _pos;
				break;
			}
		}
		return pos;
	}
}
