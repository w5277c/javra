/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import common.Expr;
import enums.EMsgType;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import main.Line;
import main.ProgInfo;

public class JAData extends JAObject {
	private	int	size;
	
	public JAData(ProgInfo l_pi, Line l_line, String l_value, int l_size) {
		super(l_pi, l_line, l_value);
		
		size = l_size;
		
		byte[] data	= new byte[0x40];
		int offset = 0x00;
		
		String parts[] = value.split(",");
		if(0x00 != parts.length) {
			for(String part : parts) {
				String tmp = part.trim();
				if(tmp.startsWith("\"") && tmp.endsWith("\"")) {
					try {
						byte[] _data = tmp.substring(0x01, tmp.length()-0x01).getBytes("ASCII");
						if((data.length-offset) < _data.length) {
							byte[] _tmp = new byte[offset + _data.length + 0x40];
							System.arraycopy(data, 0x00, _tmp, 0x00, offset);
							data = _tmp;
						}
						System.arraycopy(_data, 0x00, data, offset, _data.length);
						offset+=_data.length;
					}
					catch(Exception ex) {
						ex.printStackTrace();
					}
				}
				else {
					Long value = Expr.parse(l_pi, line, tmp);
					if(null != value) {
						if((data.length-offset) < l_size) {
							byte[] _tmp = new byte[offset + l_size + 0x40];
							System.arraycopy(data, 0x00, _tmp, 0x00, offset);
							data = _tmp;
						}
						ByteBuffer bb = ByteBuffer.allocate(Long.BYTES).order(ByteOrder.LITTLE_ENDIAN);
						bb.putLong(value);
						System.arraycopy(bb.array(), 0x00, data, offset, l_size);
						offset+=l_size;
					}
					else {
						line.set_unparsed();
					}
				}
			}

			if(0x00 != (offset%0x02)) {
				l_pi.print(EMsgType.MSG_WARNING, line, "A .DB segment with an odd number of bytes is detected. A zero byte is added.");
			}
			l_pi.get_cur_segment().get_cur_block().write(data, offset/2 + (offset%0x02));
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, line, MSG_MISSING_PARAMETERS);
		}
	}
}
