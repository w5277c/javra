/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package enums;

public enum EDevice {
	/* ATtiny Series */
	DEV_ATTINY4		("ATtiny4"		,    256, 0x040,    32,    0, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_LPM|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_AVR8L),
	DEV_ATTINY5		("ATtiny5"		,    256, 0x040,    32,    0, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_LPM|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_AVR8L),
	DEV_ATTINY9		("ATtiny9"		,    512, 0x040,    32,    0, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_LPM|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_AVR8L),
	DEV_ATTINY10	("ATtiny10"		,    512, 0x040,    32,    0, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_LPM|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_AVR8L),
	DEV_ATTINY11	("ATtiny11"		,    512, 0x000,     0,    0, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_TINY1X|EMnemonic.DF_NO_XREG|EMnemonic.DF_NO_YREG|EMnemonic.DF_NO_LPM_X|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY12	("ATtiny12"		,    512, 0x000,     0,   64, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_TINY1X|EMnemonic.DF_NO_XREG|EMnemonic.DF_NO_YREG|EMnemonic.DF_NO_LPM_X|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY13	("ATtiny13"		,    512, 0x060,    64,   64, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY13A	("ATtiny13A"   ,    512, 0x060,    64,   64, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY15	("ATtiny15"    ,    512, 0x000,     0,   64, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_XREG|EMnemonic.DF_NO_YREG|EMnemonic.DF_NO_LPM_X|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_TINY1X),
	DEV_ATTINY20	("ATtiny20"    ,   1024, 0x040,   128,    0, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_LPM|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_BREAK|EMnemonic.DF_AVR8L),
	DEV_ATTINY22	("ATtiny22"    ,   1024, 0x060,   128,  128, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_LPM_X|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY24	("ATtiny24"    ,   1024, 0x060,   128,  128, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY24A	("ATtiny24A"   ,   1024, 0x060,   128,  128, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY25	("ATtiny25"    ,   1024, 0x060,   128,  128, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY26	("ATtiny26"    ,   1024, 0x060,   128,  128, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY28	("ATtiny28"    ,   1024, 0x000,     0,    0, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_TINY1X|EMnemonic.DF_NO_XREG|EMnemonic.DF_NO_YREG|EMnemonic.DF_NO_LPM_X|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY44	("ATtiny44"    ,   2048, 0x060,   256,  256, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY44A	("ATtiny44A"   ,   2048, 0x060,   256,  256, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY45	("ATtiny45"    ,   2048, 0x060,   256,  256, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY48	("ATtiny48"    ,   2048, 0x100,   256,   64, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY84	("ATtiny84"    ,   4096, 0x060,   512,  512, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY85	("ATtiny85"    ,   4096, 0x060,   512,  512, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY88	("ATtiny88"    ,   4096, 0x100,   512,   64, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY261A	("ATtiny261A"  ,   1024, 0x060,   128,  128, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY461A	("ATtiny461A"  ,   2048, 0x060,   256,  256, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY861A	("ATtiny861A"  ,   4096, 0x060,   512,  512, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY2313	("ATtiny2313"  ,   1024, 0x060,   128,  128, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY2313A("ATtiny2313A" ,   1024, 0x060,   128,  128, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_ATTINY4313	("ATtiny4313"  ,   2048, 0x060,   256,  256, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	/* AT90 series */
	DEV_AT90S1200	("AT90S1200"   ,    512, 0x000,     0,   64, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_TINY1X|EMnemonic.DF_NO_XREG|EMnemonic.DF_NO_YREG|EMnemonic.DF_NO_LPM|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_AT90S2313	("AT90S2313"   ,   1024, 0x060,   128,  128, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_LPM_X|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_AT90S2323	("AT90S2323"   ,   1024, 0x060,   128,  128, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_LPM_X|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_AT90S2333	("AT90S2333"   ,   1024, 0x060,   128,  128, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_LPM_X|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_AT90S2343	("AT90S2343"   ,   1024, 0x060,   128,  128, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_LPM_X|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_AT90S4414	("AT90S4414"   ,   2048, 0x060,   256,  256, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_LPM_X|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_AT90S4433	("AT90S4433"   ,   2048, 0x060,   128,  256, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_LPM_X|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_AT90S4434	("AT90S4434"   ,   2048, 0x060,   256,  256, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_LPM_X|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_AT90S8515	("AT90S8515"   ,   4096, 0x060,   512,  512, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_LPM_X|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_AT90C8534	("AT90C8534"   ,   4096, 0x060,   256,  512, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_LPM_X|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	DEV_AT90S8535	("AT90S8535"   ,   4096, 0x060,   512,  512, EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_LPM_X|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	/* AT90USB series */
	/* AT90USB168 */
	/* AT90USB1287 */
	/* ATmega series */
	DEV_ATMEGA8		("ATmega8"     ,   4096, 0x060,  1024,  512, EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA8A	("ATmega8A"    ,   4096, 0x060,  1024,  512, EMnemonic.DF_NO_JMP|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA161	("ATmega161"   ,   8192, 0x060,  1024,  512, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA162	("ATmega162"   ,   8192, 0x100,  1024,  512, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA163	("ATmega163"   ,   8192, 0x060,  1024,  512, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA16	("ATmega16"    ,   8192, 0x060,  1024,  512, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA323	("ATmega323"   ,  16384, 0x060,  2048, 1024, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA32	("ATmega32"    ,  16384, 0x060,  2048, 1024, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA603	("ATmega603"   ,  32768, 0x060,  4096, 2048, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_LPM_X|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_BREAK),
	DEV_ATMEGA103	("ATmega103"   ,  65536, 0x060,  4096, 4096, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_MUL|EMnemonic.DF_NO_MOVW|EMnemonic.DF_NO_LPM_X|EMnemonic.DF_NO_ELPM_X|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_BREAK),
	DEV_ATMEGA104	("ATmega104"   ,  65536, 0x060,  4096, 4096, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ESPM), /* Old name for mega128 */
	DEV_ATMEGA128	("ATmega128"   ,  65536, 0x100,  4096, 4096, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA128A	("ATmega128A"  ,  65536, 0x100,  4096, 4096, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA48	("ATmega48"    ,   2048, 0x100,   512,  256, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA48A	("ATmega48A"   ,   2048, 0x100,   512,  256, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA48P	("ATmega48P"   ,   2048, 0x100,   512,  256, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA48PA	("ATmega48PA"  ,   2048, 0x100,   512,  256, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA88	("ATmega88"    ,   4096, 0x100,  1024,  512, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA88A	("ATmega88A"   ,   4096, 0x100,  1024,  512, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA88P	("ATmega88P"   ,   4096, 0x100,  1024,  512, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA88PA	("ATmega88PA"  ,   4096, 0x100,  1024,  512, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA168	("ATmega168"   ,   8192, 0x100,  1024,  512, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA168A	("ATmega168A"  ,   8192, 0x100,  1024,  512, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA168P	("ATmega168P"  ,   8192, 0x100,  1024,  512, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA168PA("ATmega168PA" ,   8192, 0x100,  1024,  512, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA328	("ATmega328"   ,  16384, 0x100,  2048, 1024, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA328P	("ATmega328P"  ,  16384, 0x100,  2048, 1024, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA328PB("ATmega328PB" ,  16384, 0x100,  2048, 1024, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA32U4	("ATmega32U4"  ,  16384, 0x100,  2560, 1024, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA8515	("ATmega8515"  ,   8192, 0x060,   512,  512, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA1280	("ATmega1280"  ,  65536, 0x200,  8192, 4096, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA164P	("ATmega164P"  ,   8192, 0x100,  1024,  512, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA164A	("ATmega164PA" ,   8192, 0x100,  1024,  512, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA324P	("ATmega324P"  ,  16384, 0x100,  2048, 1024, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA324PA("ATmega324PA" ,  16384, 0x100,  2048, 1024, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA644	("ATmega644"   ,  32768, 0x100,  4096, 2048, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
 	DEV_ATMEGA644P	("ATmega644P"  ,  32768, 0x100,  4096, 2096, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA644PA("ATmega644PA" ,  32768, 0x100,  4096, 2096, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA1284P("ATmega1284P" ,  65536, 0x100, 16384, 4096, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA1284PA("ATmega1284PA",	65536, 0x100, 16384, 4096, EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP|EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA2560	("ATmega2560"	,	131072, 0x200,	8192, 4096, EMnemonic.DF_NO_ESPM),
	DEV_ATMEGA4809	("ATmega4809"	,	24000, 0x2800,	6000,  256, EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP),
	/* Other */
	DEV_AT94K("AT94K"       ,   8192, 0x060, 16384,    0, EMnemonic.DF_NO_ELPM|EMnemonic.DF_NO_SPM|EMnemonic.DF_NO_ESPM|EMnemonic.DF_NO_BREAK|EMnemonic.DF_NO_EICALL|EMnemonic.DF_NO_EIJMP);

	private	String	name;
	private	int		flash_size;	//in WORDS
	private	int		ram_start;
	private	int		ram_size;
	private	int		eeprom_size;
	private	int		flags;
	
	private EDevice(String l_name, int l_flash_size, int l_ram_start, int l_ram_size, int l_eeprom_size, int l_flags) {
		name = l_name;
		flash_size = l_flash_size;
		ram_start = l_ram_start;
		ram_size = l_ram_size;
		eeprom_size = l_eeprom_size;
		flags = l_flags;
	}
	
	public String get_name() {
		return name;
	}
	
	public int get_flash_size() {
		return flash_size;
	}
	
	public int get_ram_start() {
		return ram_start;
	}
	
	public int get_ram_size() {
		return ram_size;
	}
	
	public int get_eeprom_size() {
		return eeprom_size;
	}
	
	public int get_flags() {
		return flags;
	}
}
