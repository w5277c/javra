/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import enums.EDevice;
import enums.EMsgType;
import main.Line;
import main.ProgInfo;

public class JADevice extends JAObject {
	public JADevice(ProgInfo l_pi, Line l_line, String l_value) throws Exception {
		super(l_pi, l_line, l_value);
		
		if(!value.isEmpty()) {
			EDevice device = null;
			try {
				device = EDevice.valueOf("DEV_" + value.toUpperCase());
			}
			catch(Exception ex) {
			}
			if(null != device) {
				l_pi.set_device(line, device);
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
				l_pi.print(EMsgType.MSG_ERROR, line, "Unknown .DEVICE: " + value + ", supported:\n" + sb.toString().substring(0x00, sb.length()-0x01));
			}
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, line, MSG_MISSING_PARAMETERS);
		}
	}
}
