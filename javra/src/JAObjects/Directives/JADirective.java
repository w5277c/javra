/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects.Directives;

import JAObjects.JAObject;
import enums.EMsgType;
import main.Line;
import main.ProgInfo;

public class JADirective extends JAObject {
	public static JADirective parse(ProgInfo l_pi, Line l_line) throws Exception {
		if(l_pi.get_ii().is_blockskip()) {
			switch(l_line.get_key().toLowerCase()) {
				case ".ifdef":
					return new JADIfDef(l_pi, l_line);
				case ".ifndef":
					return new JADIfNDef(l_pi, l_line);
				case ".if":
					return new JADIf(l_pi, l_line);
				case ".endif":
					return new JADEndIf(l_pi, l_line);
				case ".else":
					return new JADElse(l_pi, l_line);
				default:
					int g = 0;
			}
		}
		else {
			switch(l_line.get_key().toLowerCase()) {
				case ".equ":
					return new JADEQU(l_pi, l_line);
				case ".set":
					return new JADSET(l_pi, l_line);
				case ".org":
					return new JADORG(l_pi, l_line);
				case ".include":
					return new JADInclude(l_pi, l_line);
				case ".device":
					return new JADDevice(l_pi, l_line);
				case ".ifdef":
					return new JADIfDef(l_pi, l_line);
				case ".ifndef":
					return new JADIfNDef(l_pi, l_line);
				case ".endif":
					return new JADEndIf(l_pi, l_line);
				case ".else":
					return new JADElse(l_pi, l_line);
				case ".if":
					return new JADIf(l_pi, l_line);
				case ".message":
					return new JADMessage(l_pi, l_line);
				case ".def":
					return new JADDef(l_pi, l_line);
				case ".macro":
					return new JADMacro(l_pi, l_line, true);
				case ".endmacro":
					return new JADMacro(l_pi, l_line, false);
			}
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_UNKNOWN_DIRECTIVE);
		}
		return null;
	}
}
