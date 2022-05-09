/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects.Directives;

import enums.EMsgType;
import java.io.File;
import main.Constant;
import main.Line;
import main.Parser;
import main.ProgInfo;

public class JADInclude extends JADirective {
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
