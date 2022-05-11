/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package common;

import enums.EDevice;
import enums.EMnemonic;
import enums.EMsgType;
import enums.EPass;
import main.ProgInfo;

public class Mnemonic {
	private	static	final	int	IND_X		= 0x00;	//X
	private	static	final	int	IND_XP	= 0x01;	//X+
	private	static	final	int	IND_MX	= 0x02;	//-X
	private	static	final	int	IND_Y		= 0x03;	//Y
	private	static	final	int	IND_YP	= 0x04;	//Y+
	private	static	final	int	IND_MY	= 0x05;	//-Y
	private	static	final	int	IND_Z		= 0x06;	//Z
	private	static	final	int	IND_ZP	= 0x07;	//Z+
	private	static	final	int	IND_MZ	= 0x08;	//-Z
	
	
	
	public static void parse(ProgInfo l_pi, EMnemonic l_em, String l_value) {
		System.out.println("@@@ " + String.format("%04X", l_pi.get_cseg().get_datablock().get_addr()) + ": " + l_em.get_name() + " " + l_value);
		
		EMnemonic em = l_em;
		long opcode = 0x00;
		
		String[] params = l_value.split(",");
		
		if(0x02 < params.length) {
			l_pi.print(EMsgType.MSG_DERROR, "Garbage after instruction " + em + ":" + l_value);
			return;
		}

		String param1 = (0x00 < params.length ? params[0x00].toLowerCase() : "").replaceAll("\\s", "");
		String param2 = (0x01 < params.length ? params[0x01].toLowerCase() : "").replaceAll("\\s", "");
		
		if(EPass.PASS_1 == l_pi.get_pass()) {
			l_pi.get_cseg().get_datablock().skip(em.get_opcode_wsize()*2);
		}
		else if(EPass.PASS_2 == l_pi.get_pass()) {
			if(em.get_id() <= EMnemonic.MN_BREAK.get_id()) {
				if(0 != params.length) {
					l_pi.print(EMsgType.MSG_DERROR, "Garbage after instruction " + em + ":" + l_value);
					return;
				}
			}
			else if(em.get_id() <= EMnemonic.MN_ELPM.get_id()) {
				if(2 == params.length) {
					Integer register = l_pi.get_register(param1);
					if(null != register) {
						opcode = register << 0x04;
					}
					Integer ind = get_indirect(l_pi, param2);
					if(null == ind) return;
					if(IND_Z == ind) {
						if(EMnemonic.MN_LPM == em) {
							em = EMnemonic.MN_LPM_Z;
						}
						else if(EMnemonic.MN_ELPM == em) {
							em = EMnemonic.MN_ELPM_Z;
						}
					}
					else if(IND_ZP == ind) {
						if(EMnemonic.MN_LPM == em) {
							em = EMnemonic.MN_LPM_ZP;
						}
						else if(EMnemonic.MN_ELPM == em) {
							em = EMnemonic.MN_ELPM_ZP;
						}
					}
					else {
						l_pi.print(EMsgType.MSG_DERROR, "unsupported operand: " + param2);
					}
				}
				else if(0 != params.length) {
					l_pi.print(EMsgType.MSG_DERROR, "no one or two operand excpected: " + l_value);
				}
			}
			//TODO ...
		}
	}
	
	private static Integer get_indirect(ProgInfo l_pi, String l_param) {
		Integer result = null;
		
		String param = l_param;
		switch(l_param.charAt(0x00)) {
			case '-':
				if(0x02 == l_param.length()) {
					switch(l_param.charAt(0x01)) {
						case 'x':
							if (0 == (l_pi.get_device().get_flags() & EMnemonic.DF_NO_XREG)) result = IND_MX;
							break;
						case 'y':
							if (0 == (l_pi.get_device().get_flags() & EMnemonic.DF_NO_YREG)) result = IND_MY;
							break;
						case 'z':
							result = IND_MZ;
							break;
					}
				}
			case 'x':
				if (0 == (l_pi.get_device().get_flags() & EMnemonic.DF_NO_XREG)) {
					if(0x01 == l_param.length()) result = IND_X;
					else if(0x02 == l_param.length() && '+' == l_param.charAt(0x01)) result = IND_XP;
				}
				break;
			case 'y':
				if (0 == (l_pi.get_device().get_flags() & EMnemonic.DF_NO_YREG)) {
					if(0x01 == l_param.length()) result = IND_Y;
					else if(0x02 == l_param.length() && '+' == l_param.charAt(0x01)) result = IND_YP;
				}
				break;
			case 'z':
				if(0x01 == l_param.length()) result = IND_Z;
				else if(0x02 == l_param.length() && '+' == l_param.charAt(0x01)) result = IND_ZP;
				break;
		}
		
		if(null == result) {
			l_pi.print(EMsgType.MSG_ERROR, "Garbage in operand " + l_param);
		}
		
		return result;
	}
}
