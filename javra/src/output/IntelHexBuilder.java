/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
14.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import main.ProgInfo;
import static main.Utils.printHexBinary;

public class IntelHexBuilder implements HexBuilder {
	public static class Row {
		public	static	final	int	DATA				= 0x00;
		public	static	final	int	EOF				= 0x01;
		public	static	final	int	EXT_SEGMENT		= 0x02;
		public	static	final	int	EXT_LINEAR		= 0x04;
		public	static	final	int	START_LINEAR	= 0x05;

		public static void write(OutputStream l_os, int l_type, int l_address, byte[] l_data, int l_offset, int l_length) throws IOException {
			byte[] tmp = new byte[0x01 + 0x02 + 0x01 + l_length + 0x01];
			tmp[0x00] = (byte)l_length;
			tmp[0x01] = (byte)((l_address / 0x100) & 0x0ff);
			tmp[0x02] = (byte)(l_address & 0xff);
			tmp[0x03] = (byte)l_type;
			if(null != l_data && 0 != l_length) {
				System.arraycopy(l_data, l_offset, tmp, 0x04, l_length);
			}
			int checksum = 0x00;
			for(int pos = 0x00; pos < (tmp.length-0x01); pos++) {
				checksum += tmp[pos];
			}
			tmp[tmp.length-0x01] = (byte)((0x01 + ~(checksum )) & 0xff);
			l_os.write((":" + printHexBinary(tmp).toUpperCase()+"\r\n").getBytes());
		}
	}
   
	private	ProgInfo				pi;
	private	FileOutputStream	fos;
	
	public IntelHexBuilder(ProgInfo l_pi, String l_filename) throws Exception {
		pi = l_pi;
		fos = new FileOutputStream(new File(l_filename));
      Row.write(fos, Row.EXT_SEGMENT, 0x0000, new byte[]{0x00, 0x00}, 0x0000, 0x02);
	}
	
	@Override
	public void push(byte[] l_data, int l_address, int l_length) throws IOException {
      for(int offset = 0x00; offset < l_length; offset+=0x10) {
         Row.write(fos, Row.DATA, l_address + offset, l_data, offset, (l_length - offset) >= 0x10 ? 0x10 : (l_length - offset));
      }
	}
	
	public void close() throws IOException {
      Row.write(fos, Row.EOF, 0x0000, null, 0x0000, 0x00);
		
		if(null != fos) {
			try {
				fos.flush();
				fos.close();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
