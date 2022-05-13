/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package enums;

import java.util.HashMap;
import java.util.Map;

public enum EMnemonic {
	MN_NOP("nop", 1, 0x0000, 0),
	MN_SEC("sec", 1, 0x9408, 0),
	MN_CLC("clc", 1, 0x9488, 0),
	MN_SEN("sen", 1, 0x9428, 0),
	MN_CLN("cln", 1, 0x94a8, 0),
	MN_SEZ("sez", 1, 0x9418, 0),
	MN_CLZ("clz", 1, 0x9498, 0),
	MN_SEI("sei", 1, 0x9478, 0),
	MN_CLI("cli", 1, 0x94f8, 0),
	MN_SES("ses", 1, 0x9448, 0),
	MN_CLS("cls", 1, 0x94c8, 0),
	MN_SEV("sev", 1, 0x9438, 0),
	MN_CLV("clv", 1, 0x94b8, 0),
	MN_SET("set", 1, 0x9468, 0),
	MN_CLT("clt", 1, 0x94e8, 0),
	MN_SEH("seh", 1, 0x9458, 0),
	MN_CLH("clh", 1, 0x94d8, 0),
	MN_SLEEP("sleep", 1, 0x9588, 0),
	MN_WDR("wdr", 1, 0x95a8, 0),
	MN_IJMP("ijmp", 1, 0x9409, EMnemonic.DF_TINY1X),
	MN_EJMP("eijmp", 1, 0x9419, EMnemonic.DF_NO_EIJMP),
	MN_ICALL("icall", 1, 0x9509, EMnemonic.DF_TINY1X),
	MN_EICALL("eicall", 1, 0x9519, EMnemonic.DF_NO_EICALL),
	MN_RET("ret", 1, 0x9508, 0),
	MN_RETI("reti", 1, 0x9518, 0),
	MN_SPM("spm", 1, 0x95e8, EMnemonic.DF_NO_SPM),
	MN_ESPM("espm", 1, 0x95f8, EMnemonic.DF_NO_ESPM),
	MN_BREAK("break", 1, 0x9598, EMnemonic.DF_NO_BREAK),
	MN_LPM("lpm", 1, 0x95c8, EMnemonic.DF_NO_LPM),
	MN_ELPM("elpm", 1, 0x95d8, EMnemonic.DF_NO_ELPM),
	MN_BSET("bset", 1, 0x9408, 0),
	MN_BCLR("bclr", 1, 0x9488, 0),
	MN_SER("ser", 1, 0xef0f, 0),
	MN_COM("com", 1, 0x9400, 0),
	MN_NEG("neg", 1, 0x9401, 0),
	MN_INC("inc", 1, 0x9403, 0),
	MN_DEC("dec", 1, 0x940a, 0),
	MN_LSR("lsr", 1, 0x9406, 0),
	MN_ROR("ror", 1, 0x9407, 0),
	MN_ASR("asr", 1, 0x9405, 0),
	MN_SWAP("swap", 1, 0x9402, 0),
	MN_PUSH("push", 1, 0x920f, EMnemonic.DF_TINY1X),
	MN_POP("pop", 1, 0x900f, EMnemonic.DF_TINY1X),
	MN_TST("tst", 1, 0x2000, 0),
	MN_CLR("clr", 1, 0x2400, 0),
	MN_LSL("lsl", 1, 0x0c00, 0),
	MN_ROL("rol", 1, 0x1c00, 0),
	MN_BREQ("breq", 1, 0xf001, 0),
	MN_BRNE("brne", 1, 0xf401, 0),
	MN_BRCS("brcs", 1, 0xf000, 0),
	MN_BRCC("brcc", 1, 0xf400, 0),
	MN_BRSH("brsh", 1, 0xf400, 0),
	MN_BRLO("brlo", 1, 0xf000, 0),
	MN_BRMI("brmi", 1, 0xf002, 0),
	MN_BRPL("brpl", 1, 0xf402, 0),
	MN_BRGE("brge", 1, 0xf404, 0),
	MN_BRLT("brlt", 1, 0xf004, 0),
	MN_BRHS("brhs", 1, 0xf005, 0),
	MN_BRHC("brhc", 1, 0xf405, 0),
	MN_BRTS("brts", 1, 0xf006, 0),
	MN_BRTC("brtc", 1, 0xf406, 0),
	MN_BRVS("brvs", 1, 0xf003, 0),
	MN_BRVC("brvc", 1, 0xf403, 0),
	MN_BRIE("brie", 1, 0xf007, 0),
	MN_BRID("brid", 1, 0xf407, 0),
	MN_RJMP("rjmp", 1, 0xc000, 0),
	MN_RCALL("rcall", 1, 0xd000, 0),
	MN_JMP("jmp", 2, 0x940c, EMnemonic.DF_NO_JMP),
	MN_CALL("call", 2, 0x940e, EMnemonic.DF_NO_JMP),
	MN_BRBS("brbs", 1, 0xf000, 0),
	MN_BRBC("brbc", 1, 0xf400, 0),
	MN_ADD("add", 1, 0x0c00, 0),
	MN_ADC("adc", 1, 0x1c00, 0),
	MN_SUB("sub", 1, 0x1800, 0),
	MN_SBC("sbc", 1, 0x0800, 0),
	MN_AND("and", 1, 0x2000, 0),
	MN_OR("or", 1, 0x2800, 0),
	MN_EOR("eor", 1, 0x2400, 0),
	MN_CP("cp", 1, 0x1400, 0),
	MN_CPC("cpc", 1, 0x0400, 0),
	MN_CPSE("cpse", 1, 0x1000, 0),
	MN_MOV("mov", 1, 0x2c00, 0),
	MN_MUL("mul", 1, 0x9c00, EMnemonic.DF_NO_MUL),
	MN_MOVW("movw", 1, 0x0100, EMnemonic.DF_NO_MOVW),
	MN_MULS("muls", 1, 0x0200, EMnemonic.DF_NO_MUL),
	MN_MULSU("mulsu", 1, 0x0300, EMnemonic.DF_NO_MUL),
	MN_FMUL("fmul", 1, 0x0308, EMnemonic.DF_NO_MUL),
	MN_FMULS("fmuls", 1, 0x0380, EMnemonic.DF_NO_MUL),
	MN_FMULSU("fmulsu", 1, 0x0388, EMnemonic.DF_NO_MUL),
	MN_ADIW("adiw", 1, 0x9600, EMnemonic.DF_TINY1X | EMnemonic.DF_AVR8L),
	MN_SBIW("sbiw", 1, 0x9700, EMnemonic.DF_TINY1X | EMnemonic.DF_AVR8L),
	MN_SUBI("subi", 1, 0x5000, 0),
	MN_SBCI("sbci", 1, 0x4000, 0),
	MN_ANDI("andi", 1, 0x7000, 0),
	MN_ORI("ori", 1, 0x6000, 0),
	MN_SBR("sbr", 1, 0x6000, 0),
	MN_CPI("cpi", 1, 0x3000, 0),
	MN_LDI("ldi", 1, 0xe000, 0),
	MN_CBR("cbr", 1, 0x7000, 0),
	MN_SBRC("sbrc", 1, 0xfc00, 0),
	MN_SBRS("sbrs", 1, 0xfe00, 0),
	MN_BST("bst", 1, 0xfa00, 0),
	MN_BLD("bld", 1, 0xf800, 0),
	MN_IN("in", 1, 0xb000, 0),
	MN_OUT("out", 1, 0xb800, 0),
	MN_SBIC("sbic", 1, 0x9900, 0),
	MN_SBIS("sbis", 1, 0x9b00, 0),
	MN_SBI("sbi", 1, 0x9a00, 0),
	MN_CBI("cbi", 1, 0x9800, 0),
	MN_LDS("lds", 2, 0x9000, EMnemonic.DF_TINY1X | EMnemonic.DF_AVR8L),
	MN_STS("sts", 2, 0x9200, EMnemonic.DF_TINY1X | EMnemonic.DF_AVR8L),
	MN_LD("ld", 1, 0, 0),
	MN_ST("st", 1, 0, 0),
	MN_LDD("ldd", 1, 0, EMnemonic.DF_TINY1X),
	MN_STD("std", 1, 0, EMnemonic.DF_TINY1X),
	MN_COUNT("count", 1, 0, 0),
	MN_LPM_Z("lpm", 1, 0x9004, EMnemonic.DF_NO_LPM|EMnemonic.DF_NO_LPM_X),
	MN_LPM_ZP("lpm", 1, 0x9005, EMnemonic.DF_NO_LPM|EMnemonic.DF_NO_LPM_X),
	MN_ELPM_Z("elpm", 1, 0x9006, EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ELPM_X),
	MN_ELPM_ZP("elpm", 1, 0x9007, EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ELPM_X),
	MN_LD_X("ld", 1, 0x900c, EMnemonic.DF_NO_XREG),
	MN_LD_XP("ld", 1, 0x900d, EMnemonic.DF_NO_XREG),
	MN_LD_MX("ld", 1, 0x900e, EMnemonic.DF_NO_XREG),
	MN_LD_Y("ld", 1, 0x8008, EMnemonic.DF_NO_YREG),
	MN_LD_YP("ld", 1, 0x9009, EMnemonic.DF_NO_YREG),
	MN_LD_MY("ld", 1, 0x900a, EMnemonic.DF_NO_YREG),
	MN_LD_Z("ld", 1, 0x8000, 0),
	MN_LD_ZP("ld", 1, 0x9001, EMnemonic.DF_TINY1X),
	MN_LD_MZ("ld", 1, 0x9002, EMnemonic.DF_TINY1X),
	MN_ST_X("st", 1, 0x920c, EMnemonic.DF_NO_XREG),
	MN_ST_XP("st", 1, 0x920d, EMnemonic.DF_NO_XREG),
	MN_ST_MX("st", 1, 0x920e, EMnemonic.DF_NO_XREG),
	MN_ST_Y("st", 1, 0x8208, EMnemonic.DF_NO_YREG),
	MN_ST_YM("st", 1, 0x9209, EMnemonic.DF_NO_YREG),
	MN_ST_MY("st", 1, 0x920a, EMnemonic.DF_NO_YREG),
	MN_ST_Z("st", 1, 0x8200, 0),
	MN_ST_ZP("st", 1, 0x9201, EMnemonic.DF_TINY1X),
	MN_ST_MZ("st", 1, 0x9202, EMnemonic.DF_TINY1X),
	MN_LDD_Y("ldd", 1, 0x8008, EMnemonic.DF_TINY1X),
	MN_LDD_Z("ldd", 1, 0x8000, EMnemonic.DF_TINY1X),
	MN_STD_Y("std", 1, 0x8208, EMnemonic.DF_TINY1X),
	MN_STD_Z("std", 1, 0x8200, EMnemonic.DF_TINY1X),
	MN_LDS_AVR8L("lds", 1, 0xa000, EMnemonic.DF_TINY1X),	//TODO похоже 16 bit LDS/STS поддерживают многие чипы...(а как угадать переход по PC в коде?)
	MN_STS_AVR8L("sts", 1, 0xa800, EMnemonic.DF_TINY1X);

	public	final	static	int	DF_NO_MUL	= 0x0001; /* No {,F}MUL{,S,SU} */
	public	final static	int	DF_NO_JMP	= 0x0002; /* No JMP, CALL */
	public	final static	int	DF_NO_XREG	= 0x0004; /* No X register */
	public	final static	int	DF_NO_YREG	= 0x0008; /* No Y register */
	public	final static	int	DF_TINY1X	= 0x0010; /* AT90S1200, ATtiny11/12  set: No ADIW, SBIW, IJMP, ICALL, LDD, STD, LDS, STS, PUSH, POP */
	public	final static	int	DF_NO_LPM	= 0x0020; /* No LPM instruction */
	public	final static	int	DF_NO_LPM_X	= 0x0040; /* No LPM Rd,Z or LPM Rd,Z+ instruction */
	public	final static	int	DF_NO_ELPM	= 0x0080; /* No ELPM instruction */
	public	final static	int	DF_NO_ELPM_X= 0x0100; /* No ELPM Rd,Z or LPM Rd,Z+ instruction */
	public	final static	int	DF_NO_SPM	= 0x0200; /* No SPM instruction */
	public	final static	int	DF_NO_ESPM	= 0x0400; /* No ESPM instruction */
	public	final static	int	DF_NO_MOVW	= 0x0800; /* No MOVW instruction */
	public	final static	int	DF_NO_BREAK	= 0x1000; /* No BREAK instruction */
	public	final static	int	DF_NO_EICALL= 0x2000; /* No EICALL instruction */
	public	final static	int	DF_NO_EIJMP	= 0x4000; /* No EIJMP instruction */
	public	final static	int	DF_AVR8L		= 0x8000; /* Also known as AVRrc (reduced core)? * ATtiny4,5,9,10,20,40,102,104: No ADIW, SBIW; one word LDS/STS */
	
	private static final Map<String, EMnemonic> ids = new HashMap<String, EMnemonic>();
   static {
      for (EMnemonic mnemonic : EMnemonic.values()) {
         if(!ids.containsKey(mnemonic.get_name())) {
				ids.put(mnemonic.get_name(), mnemonic);
			}
      }
   }

	private	int		id;
	private	String	name;
	private	int		opcode;
	private	int		opcode_wsize;
	private	int		flags;
	
	private EMnemonic(String l_name, int l_opcode_wsize, int l_opcode, int l_flags) {
		name = l_name;
		opcode_wsize = l_opcode_wsize;
		opcode = l_opcode;
		flags = l_flags;
	}
	
	public int get_id() {
		return ordinal();
	}
	
	public String get_name() {
		return name;
	}
	
	public int get_opcode_wsize() {
		return opcode_wsize;
	}
	
	public int get_flags() {
		return flags;
	}
	
	public int get_opcode() {
		return opcode;
	}
	
	@Override
	public String toString() {
		return name.toUpperCase();
	}
	
	public static EMnemonic fromName(String l_name) {
      return ids.get(l_name);
   }
}
