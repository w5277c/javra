/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import common.Expr;
import JAObjects.JAObject;
import static JAObjects.JAObject.MSG_ALREADY_DEFINED;
import enums.EMsgType;
import main.Constant;
import main.Line;
import main.Macro;
import main.ProgInfo;

public class JAData extends JAObject {
	private	String	name;
	private	Constant	constant;
	private	long		num;
	
	public JAData(ProgInfo l_pi, Line l_line, int l_size) {
		String parts[] = l_line.get_value().split(",");
		if(0x00 != parts.length) {
			for(String part : parts) {
				String tmp = part.trim().toLowerCase();
				if(tmp.startsWith("\"") && tmp.endsWith("\"")) {
					try {
						byte[] data = tmp.substring(0x01, tmp.length()-0x01).getBytes("ASCII");
						l_pi.get_cur_segment().get_datablock().write(data, 0x00, data.length);
					}
					catch(Exception ex) {
						ex.printStackTrace();
					}
				}
				else {
					Long value = Expr.parse(l_pi, l_line, tmp);
					if(null != value) {
						l_pi.get_cur_segment().get_datablock().write(value, l_size);
					}
				}
			}
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, MSG_INVALID_SYNTAX);
		}
	}
}
