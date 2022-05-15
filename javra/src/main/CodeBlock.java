/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
14.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CodeBlock extends DataBlock {
	public CodeBlock(int l_start) {
		super(l_start);
	}

	@Override
	public void write_opcode(int l_opcode) {
		ByteBuffer bb = ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN);
		bb.putInt(l_opcode);
		write(bb.array(), 0x01);
	}
	
	@Override
	public void write(byte[] l_bdata, int l_size) {
		if((data.length - (offset*2)) < (l_size*2)) {
			byte[] _data = new byte[offset*2 + l_size*2 + 0x40];
			System.arraycopy(data, 0x00, _data, 0x00, offset*2);
			data = _data;
		}
		System.arraycopy(l_bdata, 0x00, data, offset*2, l_size*2);
		offset += l_size;
		if(offset > length) {
			length = offset;
		}
	}
}
