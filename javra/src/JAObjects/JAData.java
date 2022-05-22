/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
----------------------------------------------------------------------------------------------------------------------------------------------------------------
TODO: поддержка других кодировок, KOI8-R...
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import common.Expr;
import enums.EMsgType;
import enums.ESegmentType;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import main.CodeBlock;
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

		if(ESegmentType.DATA == pi.get_segment().get_type()) {
			pi.print(EMsgType.MSG_ERROR, line, "Can't use .DB/.DW/.DD directives in data segment (.DSEG) !");
		}
		else {
			parse();
		}
	}
	
	@Override
	public void parse() {
		super.parse();
		
		data = new byte[0x40];
		offset = 0x00;
		
		//TODO оттестировать и оптимизировать
		while(null != value && !value.isEmpty()) {
			int pos1 = value.indexOf("\"");
			int pos2 = (-1 == pos1 ? -1 : value.substring(pos1+0x01).indexOf("\""));
			
			if(-1 != pos1 && -1 == pos2) {
				pi.print(EMsgType.MSG_ERROR, line, MSG_INVALID_SYNTAX);
				break;
			}
			String part;
			if(-1 != pos1 && -1 != pos2) {
				part = value.substring(pos1+0x01, pos1+pos2+0x01).trim();
				value = value.substring(pos1+pos2+0x03).trim();
				try {
					byte[] _data = part.getBytes("ASCII");
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
				pos1 = value.indexOf(",");
				if(-1 == pos1) {
					part = value;
					value = null;
				}
				else {
					part = value.substring(0, pos1).trim();
					value = value.substring(pos1+0x01).trim();
				}

				Long value = Expr.parse(pi, line, part);
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

			block = pi.get_segment().get_cur_block(line);
			address = block.get_address();

			block.write(data, (block instanceof CodeBlock ? offset/2 + (offset%0x02) : offset));
		}

		if(!expr_fail) {
			block.set_addr(address);
			block.write(data, (block instanceof CodeBlock ? offset/2 + (offset%0x02) : offset));
		}
	}
	
	public byte[] get_data() {
		return data;
	}
	public int get_length() {
		return offset;
	}
	
	@Override
	public void write_list(OutputStream l_os) throws Exception {
		super.write_list(l_os);
		
		l_os.write(("C:" + String.format("%06X", address) + " " + Utils.printHexBinary(data, 0x00, offset).toUpperCase() + "\n").getBytes("UTF-8"));
	}

}
