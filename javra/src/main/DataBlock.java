/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DataBlock {
	private	long			start;
	private	int			length	= 0;
	private	byte[]		data		= new byte[0x40];
	
	public DataBlock(long l_start) {
		start = l_start;
	}

	public void write(long l_data, int l_size) {
		ByteBuffer bb = ByteBuffer.allocate(Long.BYTES).order(ByteOrder.LITTLE_ENDIAN);
		bb.putLong(l_data);
		write(bb.array(), 0x00, l_size);
	}
	
	public void write(byte[] l_data, int l_offset, int l_size) {
		if((data.length - length) < l_size) {
			byte[] _data = new byte[data.length + (l_size + 0x40)];
			System.arraycopy(data, 0x00, _data, 0x00, length);
			data = _data;
		}
		System.arraycopy(l_data, l_offset, data, length, l_size);
		length+=l_size;
	}

	public long get_addr() {
		return start + length;
	}
	
	public int get_length() {
		return length;
	}

	public void skip(int l_size) {
		if((data.length - length) < l_size) {
			byte[] _data = new byte[data.length + (l_size + 0x40)];
			System.arraycopy(data, 0x00, _data, 0x00, length);
			data = _data;
		}
		length += l_size;
	}
}
