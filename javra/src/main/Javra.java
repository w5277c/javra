/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
07.05.2022	konstantin@5277.ru			Начало
----------------------------------------------------------------------------------------------------------------------------------------------------------------
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


TODO: CSEG,DSEG,ESEG
TODO: args parsing
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import JAObjects.JAData;
import JAObjects.JAList;
import JAObjects.JAListMac;
import JAObjects.JAMacro;
import JAObjects.JAMnenomic;
import JAObjects.JANoList;
import JAObjects.JAObject;
import enums.EMsgType;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.Locale;
import output.IntelHexBuilder;

public class Javra {
	public	static	final	String	VERSION	= "0.0.1";
	
	public static void main(String[] args) throws Exception {
		long timestamp = System.currentTimeMillis();
		
		System.out.println("JAVRA Java AVR macro assembler Version " + VERSION);
		System.out.println("Licensed by GPL-3.0-or-later");
		System.out.println();
		System.out.println("WARNING! The project is not finished yet, it is under development.");
		System.out.println("!!!It is not recommended to use!!!");
		System.out.println();
		
		ProgInfo pi = new ProgInfo();
		
		String filename = "";
		int args_pos = 0x00;
		while(args.length > args_pos) {
			String arg = args[args_pos++];
			if(arg.equals("-l") && args.length > args_pos) {
				pi.get_lib_paths().add(args[args_pos++]);
			}
			else if(arg.equals("-f") && args.length > args_pos) {
				filename = args[args_pos++];
			}
			else {
				System.out.println("invalid params " + args[args_pos-0x01]);
			}
		}
		
		new Parser(pi, filename, new File(filename));

		
		boolean progress = true;
		while(progress) {
			progress = false;
			for(JAObject obj : new LinkedList<>(pi.get_objects())) {
				if(obj.is_expr_fail()) {
					
					obj.parse();
					
					if(!obj.is_expr_fail()) {
						progress = true;
					}
				}
			}
		}
		
		for(JAObject obj : new LinkedList<>(pi.get_objects())) {
			if(pi.get_error_cntr() >= pi.get_max_errors()) {
				break;
			}
			if(obj.is_expr_fail()) {
				pi.print(EMsgType.MSG_ERROR, obj.get_line(), JAObject.MSG_UNKNOWN_LEXEME, " '" + obj.get_line().get_failpart() + "'");
			}
		}

		FileOutputStream list_fos = null;
		try {
			boolean list_on = true;
			boolean listmac = false;
			list_fos = new FileOutputStream(new File("noname.lst"));
			for(JAObject obj : pi.get_objects()) {
				if(obj instanceof JAList) {
					list_on = true;
				}
				else if(obj instanceof JANoList) {
					list_on = false;
				}
				else if(obj instanceof JAListMac) {
					listmac = true;
				}
				
				if((list_on && !(obj instanceof JAMacro)) || (listmac && (obj instanceof JAMacro))) {
					obj.write_list(list_fos);
				}
			}
			list_fos.flush();
			list_fos.close();
		}
		catch(Exception ex) {
			ex.printStackTrace();
			if(null != list_fos) {
				try {
					list_fos.close();
				}
				catch(Exception ex2) {
				}
			}
		}

		if(0 != pi.get_error_cntr()) {
			System.out.println("\nBuild FAIL, warnings:" + pi.get_warning_cntr() + ", errors:" + pi.get_error_cntr() + "/" + pi.get_max_errors());
		}
		else {
			System.out.println();
			if(!pi.get_cseg().is_empty()) {
				IntelHexBuilder hex_builder = new IntelHexBuilder(pi, "nonamae_cseg.hex");
				pi.get_cseg().build(pi, hex_builder);
				hex_builder.close();
				pi.get_cseg().print_stat(pi);
			}
			if(!pi.get_dseg().is_empty()) {
				IntelHexBuilder hex_builder = new IntelHexBuilder(pi, "nonamae_dseg.hex");
				pi.get_dseg().build(pi, hex_builder);
				hex_builder.close();
				pi.get_dseg().print_stat(pi);
			}
			if(!pi.get_eseg().is_empty()) {
				IntelHexBuilder hex_builder = new IntelHexBuilder(pi, "nonamae_eseg.hex");
				pi.get_eseg().build(pi, hex_builder);
				hex_builder.close();
				pi.get_eseg().print_stat(pi);
			}

			System.out.println("\nBuild SUCCESS, warnings:" + pi.get_warning_cntr());
			float time = (System.currentTimeMillis() - timestamp) / 1000f;
			System.out.println("(parsed: " + Parser.get_line_qnt() + " lines, total time: " + String.format(Locale.US, "%.2f", time) + " s)");
		}
	}
}
