/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import common.Expr;
import enums.EMsgType;
import main.Line;
import main.ProgInfo;

public class JASet extends JAObject {
	public JASet(ProgInfo l_pi, Line l_line, String l_value) {
		super(l_pi, l_line, l_value);
		
		parse();
	}
	
	@Override
	public void parse() {
		line.set_unparsed(false);
		
		String parts[] = value.split("=");
		String tmp = parts[0x00].trim();
		if(0x02 == parts.length && !tmp.isEmpty() && tmp.replaceAll(REGEX_CONST_NAME, "").isEmpty()) {
			String name = tmp;
			tmp = parts[0x01].trim();
			Long num = Expr.parse(pi, line, tmp);
			if(null != num) {
				pi.add_constant(line, name, num, true);
			}
			else {
				line.set_unparsed(true);
			}
		}
		else {
			pi.print(EMsgType.MSG_ERROR, line, MSG_INVALID_SYNTAX);
		}
	}
}
