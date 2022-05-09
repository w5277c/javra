/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import JAObjects.JAObject;
import enums.EDevice;
import enums.EMsgType;
import main.Line;
import main.ProgInfo;

public class JADevice extends JAObject {
	public JADevice(ProgInfo l_pi, Line l_line) throws Exception {
		String tmp = l_line.get_value().trim().toLowerCase();
		if(!tmp.isEmpty()) {
			EDevice device = null;
			try {
				device = EDevice.valueOf("DEV_" + tmp.toUpperCase());
			}
			catch(Exception ex) {
			}
			if(null != device) {
				l_pi.set_device(l_line, device);
			}
			else {
				StringBuilder sb = new StringBuilder("\t");
				int column = 0;
				for(EDevice ed : EDevice.values()) {
					sb.append(ed.get_name()).append(",");
					if(0x03 == (column++)%4) {
						sb.append("\n\t");
					}
				}
				l_pi.print(EMsgType.MSG_ERROR, l_line, "Unknown .DEVICE: " + tmp + ", supported:\n" + sb.toString().substring(0x00, sb.length()-0x01));
			}
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_INVALID_SYNTAX, "needs operand");
		}
	}
}
