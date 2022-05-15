/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
16.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import common.Expr;
import enums.EMsgType;
import enums.ESegmentType;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import main.DataBlock;
import main.Line;
import main.ProgInfo;
import main.Utils;

public class JAByte extends JAObject {
	private	DataBlock	block		= null;
	private	int			address;
	private	int			length;
	
	public JAByte(ProgInfo l_pi, Line l_line, String l_value) {
		super(l_pi, l_line, l_value);
		
		
		if(ESegmentType.DATA != pi.get_segment().get_type()) {
			pi.print(EMsgType.MSG_ERROR, line, ".BYTE directive can only be used in data segment (.DSEG)");
		}
		else {
			parse();
		}
	}
	
	@Override
	public void parse() {
		super.parse();
		
		length = 0x00;
		
		if(!value.isEmpty()) {
			Long _value = Expr.parse(pi, line, this.value);
			if(null == _value) {
				expr_fail = true;
				_value = 0l;
			}
			length+=_value;

			if(null == block) {
				block = pi.get_dseg().get_cur_block(line);
				address = block.get_address();
				block.allocate(length);
			}
			
			if(!expr_fail) {
				block.set_addr(address);
				block.allocate(length);
			}
		}
		else {
			pi.print(EMsgType.MSG_ERROR, line, MSG_MISSING_PARAMETERS);
		}
	}
	
	public int get_length() {
		return length;
	}
	
	@Override
	public void write_list(OutputStream l_os) throws Exception {
		l_os.write(("D:" + String.format("%06X", address) + " " + (line.get_text() + "\n")).getBytes("UTF-8"));
	}
}
