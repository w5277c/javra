/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import enums.EMsgType;
import java.io.File;
import main.Line;
import main.Parser;
import main.ProgInfo;

public class JAInclude extends JAObject {
	public JAInclude(ProgInfo l_pi, Line l_line, String l_value, String l_charset) throws Exception {
		super(l_pi, l_line, l_value);
		
		String filename = parse_string(value);
		if(null == filename) filename = value;
		
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
				new Parser(l_pi, filename, file, l_charset);
			}
			else {
				l_pi.print(EMsgType.MSG_ERROR, line, MSG_ABSENT_FILE + " " + file.getAbsolutePath());
			}
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, line, MSG_MISSING_PARAMETERS);
		}
	}
}
