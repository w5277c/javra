/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package enums;

public enum EInstr {
	MN_NOP("nop", 0x0000, 0),
	MN_SEC("sec", 0x9408, 0),
	MN_CLC("clc", 0x9488, 0),
	MN_SEN("sen", 0x9428, 0),
	MN_CLN("cln", 0x94a8, 0),
	MN_SEZ("sez", 0x9418, 0),
	MN_CLZ("clz", 0x9498, 0),
	MN_SEI("sei", 0x9478, 0),
	MN_CLI("cli", 0x94f8, 0),
	MN_SES("ses", 0x9448, 0),
	MN_CLS("cls", 0x94c8, 0),
	MN_SEV("sev", 0x9438, 0),
	MN_CLV("clv", 0x94b8, 0),
	MN_SET("set", 0x9468, 0),
	MN_CLT("clt", 0x94e8, 0),
	MN_SEH("seh", 0x9458, 0),
	MN_CLH("clh", 0x94d8, 0),
	MN_SLEEP("sleep", 0x9588, 0),
	MN_WDR("wdr", 0x95a8, 0),
	MN_IJMP("ijmp", 0x9409, EInstr.DF_TINY1X),
	MN_EJMP("eijmp", 0x9419, EInstr.DF_NO_EIJMP),
	MN_ICALL("icall", 0x9509, EInstr.DF_TINY1X),
	MN_EICALL("eicall",0x9519, EInstr.DF_NO_EICALL),
	MN_RET("ret", 0x9508, 0),
	MN_RETI("reti", 0x9518, 0),
	MN_SPM("spm", 0x95e8, EInstr.DF_NO_SPM),
	MN_ESPM("espm", 0x95f8, EInstr.DF_NO_ESPM),
	MN_BREAK("break", 0x9598, EInstr.DF_NO_BREAK),
	MN_LPM1("lpm", 0x95c8, EInstr.DF_NO_LPM),
	MN_ELPM1("elpm", 0x95d8, EInstr.DF_NO_ELPM),
	MN_BSET("bset", 0x9408, 0),
	MN_BCLR("bclr", 0x9488, 0),
	MN_SER("ser", 0xef0f, 0),
	MN_COM("com", 0x9400, 0),
	MN_NEG("neg", 0x9401, 0),
	MN_INC("inc", 0x9403, 0),
	MN_DEC("dec", 0x940a, 0),
	MN_LSR("lsr", 0x9406, 0),
	MN_ROR("ror", 0x9407, 0),
	MN_ASR("asr", 0x9405, 0),
	MN_SWAP("swap", 0x9402, 0),
	MN_PUSH("push", 0x920f, EInstr.DF_TINY1X),
	MN_POP("pop", 0x900f, EInstr.DF_TINY1X),
	MN_TST("tst", 0x2000, 0),
	MN_CLR("clr", 0x2400, 0),
	MN_LSL("lsl", 0x0c00, 0),
	MN_ROL("rol", 0x1c00, 0),
	MN_BREQ("breq", 0xf001, 0),
	MN_BRNE("brne", 0xf401, 0),
	MN_BRCS("brcs", 0xf000, 0),
	MN_BRCC("brcc", 0xf400, 0),
	MN_BRSH("brsh", 0xf400, 0),
	MN_BRLO("brlo", 0xf000, 0),
	MN_BRMI("brmi", 0xf002, 0),
	MN_BRPL("brpl", 0xf402, 0),
	MN_BRGE("brge", 0xf404, 0),
	MN_BRLT("brlt", 0xf004, 0),
	MN_BRHS("brhs", 0xf005, 0),
	MN_BRHC("brhc", 0xf405, 0),
	MN_BRTS("brts", 0xf006, 0),
	MN_BRTC("brtc", 0xf406, 0),
	MN_BRVS("brvs", 0xf003, 0),
	MN_BRVC("brvc", 0xf403, 0),
	MN_BRIE("brie", 0xf007, 0),
	MN_BRID("brid", 0xf407, 0),
	MN_RJMP("rjmp", 0xc000, 0),
	MN_RCALL("rcall", 0xd000, 0),
	MN_JMP("jmp", 0x940c, EInstr.DF_NO_JMP),
	MN_CALL("call", 0x940e, EInstr.DF_NO_JMP),
	MN_BRBS("brbs", 0xf000, 0),
	MN_BRBC("brbc", 0xf400, 0),
	MN_ADD("add", 0x0c00, 0),
	MN_ADC("adc", 0x1c00, 0),
	MN_SUB("sub", 0x1800, 0),
	MN_SBC("sbc", 0x0800, 0),
	MN_AND("and", 0x2000, 0),
	MN_OR("or", 0x2800, 0),
	MN_EOR("eor", 0x2400, 0),
	MN_CP("cp", 0x1400, 0),
	MN_CPC("cpc", 0x0400, 0),
	MN_CPSE("cpse", 0x1000, 0),
	MN_MOV("mov", 0x2c00, 0),
	MN_MUL("mul", 0x9c00, EInstr.DF_NO_MUL),
	MN_MOVW("movw", 0x0100, EInstr.DF_NO_MOVW),
	MN_MULS("muls", 0x0200, EInstr.DF_NO_MUL),
	MN_MULSU("mulsu", 0x0300, EInstr.DF_NO_MUL),
	MN_FMUL("fmul", 0x0308, EInstr.DF_NO_MUL),
	MN_FMULS("fmuls", 0x0380, EInstr.DF_NO_MUL),
	MN_FMULSU("fmulsu",0x0388, EInstr.DF_NO_MUL),
	MN_ADIW("adiw", 0x9600, EInstr.DF_TINY1X | EInstr.DF_AVR8L),
	MN_SBIW("sbiw", 0x9700, EInstr.DF_TINY1X | EInstr.DF_AVR8L),
	MN_SUBI("subi", 0x5000, 0),
	MN_SBCI("sbci", 0x4000, 0),
	MN_ANDI("andi", 0x7000, 0),
	MN_ORI("ori", 0x6000, 0),
	MN_SBR("sbr", 0x6000, 0),
	MN_CPI("cpi", 0x3000, 0),
	MN_LDI("ldi", 0xe000, 0),
	MN_CBR("cbr", 0x7000, 0),
	MN_SBRC("sbrc", 0xfc00, 0),
	MN_SBRS("sbrs", 0xfe00, 0),
	MN_BST("bst", 0xfa00, 0),
	MN_BLD("bld", 0xf800, 0),
	MN_IN("in", 0xb000, 0),
	MN_OUT("out", 0xb800, 0),
	MN_SBIC("sbic", 0x9900, 0),
	MN_SBIS("sbis", 0x9b00, 0),
	MN_SBI("sbi", 0x9a00, 0),
	MN_CBI("cbi", 0x9800, 0),
	MN_LDS1("lds", 0x9000, EInstr.DF_TINY1X | EInstr.DF_AVR8L),
	MN_STS1("sts", 0x9200, EInstr.DF_TINY1X | EInstr.DF_AVR8L),
	MN_LD("ld", 0, 0),
	MN_ST("st", 0, 0),
	MN_LDD("ldd", 0, EInstr.DF_TINY1X),
	MN_STD("std", 0, EInstr.DF_TINY1X),
	MN_COUNT("count", 0, 0),
	MN_LPM2("lpm", 0x9004, EInstr.DF_NO_LPM|EInstr.DF_NO_LPM_X),
	MN_LPM3("lpm", 0x9005, EInstr.DF_NO_LPM|EInstr.DF_NO_LPM_X),
	MN_ELPM2("elpm", 0x9006, EInstr.DF_NO_ELPM|EInstr.DF_NO_ELPM_X),
	MN_ELPM3("elpm", 0x9007, EInstr.DF_NO_ELPM|EInstr.DF_NO_ELPM_X),
	MN_LD1("ld", 0x900c, EInstr.DF_NO_XREG),
	MN_LD2("ld", 0x900d, EInstr.DF_NO_XREG),
	MN_LD3("ld", 0x900e, EInstr.DF_NO_XREG),
	MN_LD4("ld", 0x8008, EInstr.DF_NO_YREG),
	MN_LD5("ld", 0x9009, EInstr.DF_NO_YREG),
	MN_LD6("ld", 0x900a, EInstr.DF_NO_YREG),
	MN_LD7("ld", 0x8000, 0),
	MN_LD8("ld", 0x9001, EInstr.DF_TINY1X),
	MN_LD9("ld", 0x9002, EInstr.DF_TINY1X),
	MN_ST1("st", 0x920c, EInstr.DF_NO_XREG),
	MN_ST2("st", 0x920d, EInstr.DF_NO_XREG),
	MN_ST3("st", 0x920e, EInstr.DF_NO_XREG),
	MN_ST4("st", 0x8208, EInstr.DF_NO_YREG),
	MN_ST5("st", 0x9209, EInstr.DF_NO_YREG),
	MN_ST6("st", 0x920a, EInstr.DF_NO_YREG),
	MN_ST7("st", 0x8200, 0),
	MN_ST8("st", 0x9201, EInstr.DF_TINY1X),
	MN_ST9("st", 0x9202, EInstr.DF_TINY1X),
	MN_LDD1("ldd", 0x8008, EInstr.DF_TINY1X),
	MN_LDD2("ldd", 0x8000, EInstr.DF_TINY1X),
	MN_STD1("std", 0x8208, EInstr.DF_TINY1X),
	MN_STD2("std", 0x8200, EInstr.DF_TINY1X),
	MN_LDS2("lds", 0xa000, EInstr.DF_TINY1X),
	MN_STS2("sts", 0xa800, EInstr.DF_TINY1X);

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

	
	private	String	instr;
	private	int		word;
	private	int		flags;
	
	private EInstr(String l_instr, int l_word, int l_flags) {
		instr = l_instr;
		word = l_word;
		flags = l_flags;
	}
	

}
