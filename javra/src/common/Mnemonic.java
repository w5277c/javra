/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package common;

import enums.EMnemonic;
import enums.EPass;
import main.Line;
import main.ProgInfo;

public class Mnemonic {
	
	public static void parse(ProgInfo l_pi, EMnemonic l_em, String l_value) {
		if(EPass.PASS_1 == l_pi.get_pass()) {
			l_pi.get_cseg().get_datablock().skip(l_em.get_opcode_wsize()*2);
		}
		else {
			//TODO
		}
	}
}
