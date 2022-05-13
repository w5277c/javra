/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
07.03.2022	konstantin@5277.ru			Начало
----------------------------------------------------------------------------------------------------------------------------------------------------------------
TODO: Проверять количество параметров в каждой директиве
TODO:
1. #pragma warning range byte option
2. #pragma overlap option
3. #pragma error instruction
4. #pragma warning instruction

1. #pragma AVRPART ADMIN PART_NAME string
2. #pragma AVRPART CORE CORE_VERSION version-string
3. #pragma AVRPART CORE INSTRUCTIONS_NOT_SUPPORTED
mnemonic[ operand[,operand] ][:...]
4. #pragma AVRPART CORE NEW_INSTRUCTIONS mnemonic[ operand[,operand]][:...]
5. #pragma AVRPART MEMORY PROG_FLASH size
6. #pragma AVRPART MEMORY EEPROM size
7. #pragma AVRPART MEMORY INT_SRAM SIZE size
8. #pragma AVRPART MEMORY INT_SRAM START_ADDR address
9. #pragma partinclude num
TODO: поддержка #
TODO: Pre-defined Macros


TODO: Навести порядок с заполнением памяти в DataBlock с учетом PASS

-f /home/kostas/repos/w5277c/javra/javra/test.asm

//-l /home/kostas/repos/w5277c/core5277/ -f main.asm
/media/kostas/repos/w5277c/5277.ru/firmware/solid_relay_x4_v1.0
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import java.io.File;
import java.util.LinkedList;

public class Javra {
	public	static	final	String	VERSION	= "0.0.1";
	
	public static void main(String[] args) throws Exception {
		ProgInfo pi = new ProgInfo();
		
		String filename = "";
		int args_pos = 0x00;
		while(args.length > args_pos) {
			String arg = args[args_pos++];
			if(arg.equals("-l") && args.length > args_pos) {
				pi.get_lib_paths().add(args[args_pos++]);
			}
			else {
				System.out.println("invalid params " + args[args_pos-0x01]);
			}
			if(arg.equals("-f") && args.length > args_pos) {
				filename = args[args_pos++];
			}
			else {
				System.out.println("invalid params " + args[args_pos-0x01]);
			}
		}
		
		Parser parser = new Parser(pi, new File(filename));

		System.out.println("---ADDITIONAL PASS---");
		boolean progress = true;
		while(progress) {
			progress = false;
			LinkedList<Line> unparesed = pi.pull_unparsed();
			for(Line line : unparesed) {
				if(line.get_text().equalsIgnoreCase("JMP main")) {
					int t =1;
				}
				Parser.line_parse(pi, line);
			}
			if(pi.unparsed_qnt() < unparesed.size()) {
				progress = true;
			}
		}
		
		if(0 != pi.get_error_cntr()) {
			System.out.println("\nBuild fail, wrns:" + pi.get_warning_cntr() + ", errs:" + pi.get_error_cntr() + "/" + pi.get_max_errors());
		}
		else {
			System.out.println("\nBuild sucess, wrns:" + pi.get_warning_cntr());
		}
		pi.get_list().close();
	}
	
}
