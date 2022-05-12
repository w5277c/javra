/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package common;

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
		long opcode1 = 0x00;
		long opcode2 = 0x00;
		
		String[] params = l_value.split(",");
		
		if(0x02 < params.length) {
			l_pi.print(EMsgType.MSG_ERROR, "Garbage after instruction " + em + ":" + l_value);
			return;
		}

		String param1 = (0x00 < params.length ? params[0x00].toLowerCase().replaceAll("\\s", "") : null);
		String param2 = (0x01 < params.length ? params[0x01].toLowerCase().replaceAll("\\s", "") : null);
		
		if(EPass.PASS_1 == l_pi.get_pass()) {
			l_pi.get_cseg().get_datablock().skip(em.get_opcode_wsize()*2);
		}
		else if(EPass.PASS_2 == l_pi.get_pass()) {
			if(em.get_id() <= EMnemonic.MN_BREAK.get_id()) {
				if(0 != params.length) {
					l_pi.print(EMsgType.MSG_ERROR, "Garbage after instruction " + em + ":" + l_value);
					return;
				}
			}
			else if(em.get_id() <= EMnemonic.MN_ELPM.get_id()) {
				if(2 == params.length) {
					Integer register = l_pi.get_register(param1);
					if(null != register) {
						opcode1 = register << 0x04;
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
						l_pi.print(EMsgType.MSG_ERROR, "unsupported operand: " + param2);
					}
				}
				else if(0 != params.length) {
					l_pi.print(EMsgType.MSG_ERROR, "no one or two operand excpected: " + l_value);
				}
			}
			else {
				if(null == param1) {
					l_pi.print(EMsgType.MSG_ERROR, em.get_name() + " needs an operand");
					return;
				}
				if(em.get_id() >= EMnemonic.MN_BRBS.get_id()) {
					if(null == param2) {
						l_pi.print(EMsgType.MSG_ERROR, em.get_name() + " needs a second operand");
						return;
					}
				}
				if(em.get_id() <= EMnemonic.MN_BCLR.get_id()) {
					Integer value = get_bitnum(l_pi, em, param2);
					if(null == value) {
						return;
					}
					opcode1 = (value << 0x04);
				}
				else if(em.get_id() <= EMnemonic.MN_ROL.get_id()) {
					Integer register = get_register(l_pi, em, param1);
					if(null == register) {
						return;
					}
					if(em.get_id() == EMnemonic.MN_SER.get_id() && register < 16) {
						l_pi.print(EMsgType.MSG_ERROR, em.get_name() + " can only use a high register (r16 - r31)");
						register = 0x00;
					}
					opcode1 = (register << 0x04);
					if(em.get_id() == EMnemonic.MN_TST.get_id()) {
						opcode1 |= ((register & 0x10)<<0x05) | (register & 0x0f);
					}
				}
				else if(em.get_id() <= EMnemonic.MN_RCALL.get_id()) {
					if(null != param2) {
						l_pi.print(EMsgType.MSG_ERROR, em.get_name() + " Garbage in second operand " + param2);
						return;
					}
					Long value = Expr.parse(l_pi, param1);
					if(null == value) {
						return;
					}
					if(em.get_id() <= EMnemonic.MN_BRID.get_id()) {
						if(!range_check(l_pi, value, 64, true)) {
							return;
						}
						opcode1 = (value & 0x7f) << 3;
					}
					else {
						if(!range_check(l_pi, value, 2048, true)) {
							return;
						}
						opcode1 = (value & 0x0fff);
					}
				}
				else if(em.get_id() <= EMnemonic.MN_CALL.get_id()) {
					if(null != param2) {
						l_pi.print(EMsgType.MSG_ERROR, em.get_name() + " Garbage in second operand " + param2);
						return;
					}
					Long value = Expr.parse(l_pi, param1);
					if(null == value) {
						return;
					}
					if(!range_check(l_pi, value, 0x400000, false)) {
						return;
					}
					opcode1 = ((value & 0x3e0000) >> 13) | ((value & 0x010000) >> 16);
					opcode2 = value & 0xffff;
				}

			}
			//TODO ...
		}
	}
	
	private static boolean range_check(ProgInfo l_pi, long l_offset, int l_range, boolean l_signed) {
		if(l_signed && ((l_range-0x01) < l_offset || (l_range*(-1)) > l_offset)) {
			l_pi.print(EMsgType.MSG_ERROR, " address ouf of range (" + (l_range*(-1)) + " <= " + l_offset + " <= " + (l_range-0x01) + ")");
			return false;
		}
		if(!l_signed && (0 > l_offset || (l_range-0x01) < l_offset )) {
			l_pi.print(EMsgType.MSG_ERROR, " address ouf of range (0 <= " + l_offset + " <= " + (l_range-0x01) + ")");
			return false;
		}
		int pc = l_pi.get_cseg().get_datablock().get_addr();
		if((0 > (pc+l_offset) || l_pi.get_device().get_flash_size() < (pc+l_offset))) {
			l_pi.print(EMsgType.MSG_ERROR, " address ouf of flash range '" + (pc+l_offset) + "'");
			return false;
		}
		return true;
	}
	
	private static Integer get_bitnum(ProgInfo l_pi, EMnemonic l_em, String l_param) {
		Long expr = Expr.parse(l_pi, l_param);
		if(null == expr) {
			l_pi.print(EMsgType.MSG_ERROR, l_em.get_name() + " invalid expression in operand '" + l_param + "'");
			return null;
		}
		if(0>expr || 7<expr) {
			l_pi.print(EMsgType.MSG_ERROR, l_em.get_name() + " operand '" + l_param + "' out of range (0 <= s <= 7)");
			return null;
		}
		return expr.intValue();
	}

	
	private	static Integer get_register(ProgInfo l_pi, EMnemonic l_em, String l_param) {
		String param = l_param;
		int pos = l_param.indexOf(":");
		if(-1 != pos) {
			param = param.substring(pos);
		}
		
		Integer register = l_pi.get_register(param);
		if(null == register) {
			l_pi.print(EMsgType.MSG_ERROR, l_em.get_name() + " invalid register '" + l_param + "'");
		}
		return register;
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
