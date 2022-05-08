/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAObjects.Directives;

import JAObjects.JAObject;
import enums.EMsgType;
import java.io.File;
import java.io.FileNotFoundException;
import main.Constant;
import main.Line;
import main.ORGInfo;
import main.Parser;
import main.ProgInfo;

/**
 *
 * @author kostas
 */
public class JADInclude extends JADirective {
	private	String	name;
	private	Constant	constant;
	private	long		num;
	
	public JADInclude(ProgInfo l_pi, Line l_line) throws Exception {
		String filename = parse_string(l_line.get_value());
		if(null != filename) {
			File file = new File(l_pi.get_root_path() + filename);
			
			if(!file.exists()) {
				for(String lib_path : l_pi.get_lib_paths()) {
					file = new File(lib_path + filename);
					if(file.exists()) {
						break;
					}
				}
			}				
			if(file.exists()) {
				Parser parser = new Parser(l_pi, file);
				
				
				
				
			}
			else {
				l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_ABSENT_FILE);
			}
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_INVALID_SYNTAX);
		}
	}
}
