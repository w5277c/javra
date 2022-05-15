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
import main.DataBlock;
import main.Line;
import main.ProgInfo;
import main.Utils;

public class JAData extends JAObject {
	private	int			size;
	private	DataBlock	block		= null;
	private	int			address;
	private	byte[]		data;
	private	int			offset;
	
	public JAData(ProgInfo l_pi, Line l_line, String l_value, int l_size) {
		super(l_pi, l_line, l_value);
		
		size = l_size;
		
		parse();
	}
	
	@Override
	public void parse() {
		super.parse();
		
		data = new byte[0x40];
		offset = 0x00;
		
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
					Long value = Expr.parse(pi, line, tmp);
					if(null == value) {
						expr_fail = true;
						value = 0l;
					}
					if((data.length-offset) < size) {
						byte[] _tmp = new byte[offset + size + 0x40];
						System.arraycopy(data, 0x00, _tmp, 0x00, offset);
						data = _tmp;
					}
					ByteBuffer bb = ByteBuffer.allocate(Long.BYTES).order(ByteOrder.LITTLE_ENDIAN);
					bb.putLong(value);
					System.arraycopy(bb.array(), 0x00, data, offset, size);
					offset+=size;
				}
			}

			if(null == block) {
				if(0x00 != (offset%0x02)) {
					pi.print(EMsgType.MSG_WARNING, line, "A .DB segment with an odd number of bytes is detected. A zero byte is added.");
				}

				block = pi.get_cur_segment().get_cur_block();
				address = block.get_addr();
				block.write(data, offset/2 + (offset%0x02));
			}
			
			if(!expr_fail) {
				block.set_addr(address);
				block.write(data, offset/2 + (offset%0x02));
			}
		}
		else {
			pi.print(EMsgType.MSG_ERROR, line, MSG_MISSING_PARAMETERS);
		}
	}
	
	@Override
	public void write_list(OutputStream l_os) throws Exception {
		super.write_list(l_os);
		
		l_os.write(("C:" + String.format("%06X", address) + " " + Utils.printHexBinary(data, 0x00, offset).toUpperCase() + "\n").getBytes("UTF-8"));
	}

}
