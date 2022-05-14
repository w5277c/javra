/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

public class DataBlock {
	protected	int			start;
	protected	int			offset	= 0;
	protected	int			length	= 0;
	protected	byte[]		data		= new byte[0x40];
	protected	int			overlap	= 0;
	
	public DataBlock(int l_start) {
		start = l_start;
	}

	public void write(byte[] l_bdata, int l_size) {
		if((data.length - offset) < l_size) {
			byte[] _data = new byte[offset + l_size + 0x40];
			System.arraycopy(data, 0x00, _data, 0x00, offset);
			data = _data;
		}
		System.arraycopy(l_bdata, 0x00, data, offset, l_size);
		offset += l_size;
		if(offset > length) {
			length = offset;
		}
	}

	public int get_addr() {
		return start + offset;
	}
	public void set_addr(int l_addr) {
		offset = l_addr-start;
	}

	public int get_length() {
		return length;
	}

	public int get_start() {
		return start;
	}

	public void set_overlap(int l_addr) {
		int delta = (start + length - 0x01) - l_addr;
		overlap = (0 < delta ? delta : 0);
	}

	public int get_overlap() {
		return overlap;
	}
	
	public byte[] get_data() {
		return data;
	}
}
