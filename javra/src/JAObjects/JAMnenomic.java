/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
14.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package JAObjects;

import common.Expr;
import enums.EMsgType;
import enums.EMnemonic;
import java.io.OutputStream;
import main.Line;
import main.ProgInfo;

public class JAMnenomic extends JAObject {
	private	static	final	int	IND_X		= 0x00;	//X
	private	static	final	int	IND_XP	= 0x01;	//X+
	private	static	final	int	IND_MX	= 0x02;	//-X
	private	static	final	int	IND_Y		= 0x03;	//Y
	private	static	final	int	IND_YP	= 0x04;	//Y+
	private	static	final	int	IND_MY	= 0x05;	//-Y
	private	static	final	int	IND_Z		= 0x06;	//Z
	private	static	final	int	IND_ZP	= 0x07;	//Z+
	private	static	final	int	IND_MZ	= 0x08;	//-Z

	private	EMnemonic	em;
	private	int			address;
	private	Integer		opcode1		= 0x00;
	private	Integer		opcode2		= null;

	public JAMnenomic(ProgInfo l_pi, Line l_line, String l_value, EMnemonic l_em) {
		super(l_pi, l_line, l_value);

		em = l_em;

		parse(value);
	}
	
	
	public void parse(String l_value) {
		String[] params = l_value.split(",");

		address = pi.get_cseg().get_cur_block().get_addr();
		
//System.out.println(	"@@@ " + String.format("%04X", pi.get_cseg().get_cur_datablock().get_waddr()) +
//									": " + l_em.get_name() + " " + l_value);
		
/*		if(null != line.get_addr()) {
			if(line.get_text().equalsIgnoreCase("BREQ _C5_DISPATCHER_EVENT__CHECK_WAITTIME")) {
				int t =1;
			}

			pi.get_cseg().set_addr(line.get_addr());
		}
*/
		
		if(0x02 < params.length) {
			pi.print(EMsgType.MSG_ERROR, line, "Garbage after instruction " + em + ":" + l_value);
		}
		else {
			String param1 = (0x00 < params.length ? params[0x00].toLowerCase().replaceAll("\\s", "") : null);
			if(null != param1 && param1.isEmpty()) param1 = null;
			String param2 = (0x01 < params.length ? params[0x01].toLowerCase().replaceAll("\\s", "") : null);
			if(null != param2 && param2.isEmpty()) param2 = null;

			if(em.get_id() <= EMnemonic.MN_BREAK.get_id()) {
				garbage_check(pi, em, param1, param2, 0);
			}
			else if(em.get_id() <= EMnemonic.MN_ELPM.get_id()) {
				if(garbage_check(pi, em, param1, param2, 1, 2)) {
					if(2 == params.length) {
						Integer register = pi.get_register(param1);
						if(null != register) {
							opcode1 = register << 0x04;
						}
						Integer ind = get_indirect(pi, param2);
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
							pi.print(EMsgType.MSG_ERROR, line, "unsupported operand: " + param2);
						}
					}
				}
			}
			else {
				if(em.get_id() >= EMnemonic.MN_BRBS.get_id()) {
					garbage_check(pi, em, param1, param2, 2);
				}
				if(em.get_id() <= EMnemonic.MN_BCLR.get_id()) {
					if(garbage_check(pi, em, param1, param2, 1)) {
						Integer bitnum = get_bitnum(pi, em, param1);
						if(null != bitnum) {
							opcode1 = (bitnum << 0x04);
						}
					}
				}
				else if(em.get_id() <= EMnemonic.MN_ROL.get_id()) {
					if(garbage_check(pi, em, param1, param2, 1)) {
						Integer register = get_register(pi, em, param1);
						if(em.get_id() == EMnemonic.MN_SER.get_id()) {
							regrange_check(pi, em, register, 16, 31);
						}
						opcode1 = (register << 0x04);
						if(em.get_id() == EMnemonic.MN_TST.get_id()) {
							opcode1 |= ((register & 0x10)<<0x05) | (register & 0x0f);
						}
					}
				}
				else if(em.get_id() <= EMnemonic.MN_RCALL.get_id()) {
					if(garbage_check(pi, em, param1, param2, 1)) {
						Long value = Expr.parse(pi, line, param1);
						if(null != value) {
							value -= (pi.get_cseg().get_cur_block().get_addr() + 0x01);
							if(em.get_id() <= EMnemonic.MN_BRID.get_id()) {
								if(range_check(pi, value, 0x40, true)) {
									opcode1 = (int)((value & 0x7f) << 3);
								}
							}
							else {
								if(range_check(pi, value, 0x800, true)) {
									opcode1 = (int)((value & 0x0fff));
								}
							}
						}
					}
				}
				else if(em.get_id() <= EMnemonic.MN_CALL.get_id()) {
					if(garbage_check(pi, em, param1, param2, 1)) {
						Long value = Expr.parse(pi, line, param1);
						if(range_check(pi, value, 0x400000, false)) {
							opcode1 = (int)(((value & 0x3e0000) >> 13) | ((value & 0x010000) >> 16));
							opcode2 = (int)(value & 0xffff);
						}
					}
				}
				else if(em.get_id() <= EMnemonic.MN_BRBC.get_id()) {
					if(garbage_check(pi, em, param1, param2, 2)) {
						opcode1 = get_bitnum(pi, em, param1);
						if(null != opcode1) {
							Long value = Expr.parse(pi, line, param2);
							if(null != value) {
								value -= pi.get_cseg().get_cur_block().get_addr();
								if(range_check(pi, value, 0x40, true)) {
									opcode1 |= (int)(((value & 0x7f) << 0x03));
								}
							}
						}
					}
				}
				else if(em.get_id() <= EMnemonic.MN_MUL.get_id()) {
					if(garbage_check(pi, em, param1, param2, 2)) {
						Integer register1 = get_register(pi, em, param1);
						if(null != register1) {
							opcode1 = (int)(register1 << 0x04);
							Integer register2 = get_register(pi, em, param2);
							if(null != register2) {
								opcode1 = (int)(((register2 & 0x10) << 0x05) | (register2 & 0x0f));
							}
						}
					}
				}
				else if(em.get_id() <= EMnemonic.MN_MOVW.get_id()) {
					if(garbage_check(pi, em, param1, param2, 2)) {
						Integer register1 = get_register(pi, em, param1);
						if(null != register1) {
							if(0x01 == (register1 % 0x02)) {
								pi.print(EMsgType.MSG_ERROR, line, em + " must use a even numbered register for Rd");
							}
							else {
								opcode1 = (int)((register1 / 2) << 0x04);
								Integer register2 = get_register(pi, em, param2);
								if(null != register2) {
									if(0x01 == (register2 % 0x02)) {
										pi.print(EMsgType.MSG_ERROR, line, em + " must use a even numbered register for Rr");
									}
									else {
										opcode1 |= (int)(register2 / 2);
									}
								}
							}
						}
					}
				}
				else if(em.get_id() <= EMnemonic.MN_MULS.get_id()) {
					if(garbage_check(pi, em, param1, param2, 2)) {
						Integer register1 = get_register(pi, em, param1);
						if(regrange_check(pi, em, register1, 16, 31)) {
							opcode1 = (int)((register1 & 0x0f) << 0x04);
							Integer register2 = get_register(pi, em, param2);
							if(regrange_check(pi, em, register2, 16, 31)) {
								opcode1 |= (int)(register2 & 0x0f);
							}
						}
					}
				}
				else if(em.get_id() <= EMnemonic.MN_FMULSU.get_id()) {
					if(garbage_check(pi, em, param1, param2, 2)) {
						Integer register1 = get_register(pi, em, param1);
						if(regrange_check(pi, em, register1, 16, 23)) {
							opcode1 = (int)((register1 & 0x07) << 0x04);
							Integer register2 = get_register(pi, em, param2);
							if(regrange_check(pi, em, register2, 16, 23)) {
								opcode1 |= (int)(register2 & 0x07);
							}
						}
					}
				}
				else if(em.get_id() <= EMnemonic.MN_SBIW.get_id()) {
					if(garbage_check(pi, em, param1, param2, 2)) {
						Integer register1 = get_register(pi, em, param1);
						if(register1 != 24 && register1 != 26 && register1 != 28 && register1 != 30) {
							pi.print(EMsgType.MSG_ERROR, line, em + "  can only use registers R24, R26, R28 or R30");
						}
						else {
							opcode1 = (int)(((register1 - 24) / 0x02) << 0x04);
							Long value = Expr.parse(pi, line, param2);
							if(constrange_check(pi, em, value, 0, 63)) {
								opcode1 |= (int)(((value & 0x30) << 0x02) | (value & 0x0f));
							}
						}
					}
				}

			}

			if(0x00 != (pi.get_device().get_flags() & em.get_flags())) {
				pi.print(EMsgType.MSG_ERROR, line, em + " instruction is not supported on " + pi.get_device().get_name());
			}
		}
		opcode1 |= em.get_opcode();

		pi.get_cseg().get_cur_block().write_opcode(opcode1);
		if(null != opcode2) {
			pi.get_cseg().get_cur_block().write_opcode(opcode2);
		}
//TODO ...

	}
	
	private boolean constrange_check(ProgInfo pi, EMnemonic l_em, Long l_value, int l_min, int l_max) {
		if(null == l_value) return false;

		if(l_min > l_value || l_max < l_value) {
			pi.print(	EMsgType.MSG_ERROR, line, l_em + " Constan out of range(" + l_min + " <= " + l_value + " <=" + l_max + ")");
			return false;
		}
		return true;
	}

	private boolean regrange_check(ProgInfo pi, EMnemonic l_em, Integer l_register, int l_min, int l_max) {
		if(null == l_register) return false;

		if(l_min > l_register || l_max < l_register) {
			pi.print(	EMsgType.MSG_ERROR, line, l_em + " register out of range(r" + l_min + " - r" + l_max + ")");
			return false;
		}
		return true;
	}

	private boolean garbage_check(ProgInfo pi, EMnemonic l_em, String l_param1, String l_param2,  int... l_operand_qnt) {
		boolean result = false;
		for(int operand_qnt : l_operand_qnt) {
			if(0 == operand_qnt && null == l_param1 && null == l_param2) result = true;
			if(1 == operand_qnt && null != l_param1 && null == l_param2) result = true;
			if(2 == operand_qnt && null != l_param1 && null != l_param2) result = true;
		}
		if(!result) {
			pi.print(	EMsgType.MSG_ERROR, line, l_em + " invalid operands quantity" +
							(null != l_param2 ? ":" + l_param1 + "," + l_param2 : (null == l_param1 ? "" : ":" + l_param1)));
		}
		return result;
	}
	
	private boolean range_check(ProgInfo pi, Long l_offset, int l_range, boolean l_relative) {
		if(null == l_offset) {
			return false;
		}
		if(l_relative && ((l_range-0x01) < l_offset || (l_range*(-1)) > l_offset)) {
			pi.print(EMsgType.MSG_ERROR, line, " address ouf of range (" + (l_range*(-1)) + " <= " + l_offset + " <= " + (l_range-0x01) + ")");
			return false;
		}
		if(!l_relative && (0 > l_offset || (l_range-0x01) < l_offset )) {
			pi.print(EMsgType.MSG_ERROR, line, " address ouf of range (0 <= " + l_offset + " <= " + (l_range-0x01) + ")");
			return false;
		}
		int pc = pi.get_cseg().get_cur_block().get_addr();
		if(l_relative && (0 > (pc+l_offset) || pi.get_device().get_flash_size() <= (pc+l_offset))) {
			pi.print(EMsgType.MSG_ERROR, line, " address ouf of flash range '" + (pc+l_offset) + "'");
			return false;
		}
		if(!l_relative &&	(0 > l_offset || pi.get_device().get_flash_size() <= l_offset)) {
			pi.print(EMsgType.MSG_ERROR, line, " address ouf of flash range '" + (l_offset) + "'");
			return false;
		}
		return true;
	}
	
	private Integer get_bitnum(ProgInfo pi, EMnemonic l_em, String l_param) {
		Long expr = Expr.parse(pi, line, l_param);
		if(null == expr) {
			pi.print(EMsgType.MSG_ERROR, line, l_em + " invalid expression in operand '" + l_param + "'");
			return null;
		}
		if(0>expr || 7<expr) {
			pi.print(EMsgType.MSG_ERROR, line, l_em + " operand '" + l_param + "' out of range (0 <= s <= 7)");
			return null;
		}
		return expr.intValue();
	}

	
	private Integer get_register(ProgInfo pi, EMnemonic l_em, String l_param) {
		String param = l_param;
		int pos = l_param.indexOf(":");
		if(-1 != pos) {
			param = param.substring(pos);
		}
		
		Integer register = pi.get_register(param);
		if(null == register) {
			pi.print(EMsgType.MSG_ERROR, line, l_em + " invalid register '" + l_param + "'");
		}
		return register;
	}
	
	private Integer get_indirect(ProgInfo pi, String l_param) {
		Integer result = null;
		
		String param = l_param;
		switch(l_param.charAt(0x00)) {
			case '-':
				if(0x02 == l_param.length()) {
					switch(l_param.charAt(0x01)) {
						case 'x':
							if (0 == (pi.get_device().get_flags() & EMnemonic.DF_NO_XREG)) result = IND_MX;
							break;
						case 'y':
							if (0 == (pi.get_device().get_flags() & EMnemonic.DF_NO_YREG)) result = IND_MY;
							break;
						case 'z':
							result = IND_MZ;
							break;
					}
				}
			case 'x':
				if (0 == (pi.get_device().get_flags() & EMnemonic.DF_NO_XREG)) {
					if(0x01 == l_param.length()) result = IND_X;
					else if(0x02 == l_param.length() && '+' == l_param.charAt(0x01)) result = IND_XP;
				}
				break;
			case 'y':
				if (0 == (pi.get_device().get_flags() & EMnemonic.DF_NO_YREG)) {
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
			pi.print(EMsgType.MSG_ERROR, line, MSG_DIRECTIVE_GARBAGE);
		}
		
		return result;
	}

	public int get_address() {
		return address;
	}
	
	@Override
	public void write_list(OutputStream l_os) throws Exception {
		if(null == opcode2) {
			l_os.write(("C:" + String.format("%06X", address) + " " + String.format("%04X", opcode1) + "\t" + line.get_text() + "\n").getBytes("UTF-8"));
		}
		else {
			l_os.write(	("C:" + String.format("%06X", address) + " " + String.format("%04X", opcode1) + " " + String.format("%04X", opcode2) + "\t" +
								line.get_text() + "\n").getBytes("UTF-8"));
		}
	}
}
