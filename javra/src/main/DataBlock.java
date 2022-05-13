/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DataBlock {
	private	int			wstart;
	private	int			woffset	= 0;
	private	int			wlength	= 0;
	private	byte[]		bdata		= new byte[0x40];
	
	public DataBlock(int l_wstart) {
		wstart = l_wstart;
	}

	public void write_opcode(int l_opcode) {
		ByteBuffer bb = ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN);
		bb.putInt(l_opcode);
		write(bb.array(), 0x01);
	}
	
	public void write(byte[] l_bdata, int l_wsize) {
		if((bdata.length - (woffset*2)) < (l_wsize*2)) {
			byte[] _data = new byte[woffset*2 + l_wsize*2 + 0x40];
			System.arraycopy(bdata, 0x00, _data, 0x00, woffset*2);
			bdata = _data;
		}
		System.arraycopy(l_bdata, 0x00, bdata, woffset*2, l_wsize*2);
		woffset += l_wsize;
		if(woffset > wlength) {
			wlength = woffset;
		}
	}

	public int get_waddr() {
		return wstart + woffset;
	}
	public void set_waddr(int l_waddr) {
		woffset = l_waddr-wstart;
	}

	public int get_wlength() {
		return wlength;
	}

	public long get_wstart() {
		return wstart;
	}
}
