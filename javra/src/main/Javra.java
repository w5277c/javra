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

import JAObjects.JAList;
import JAObjects.JAListMac;
import JAObjects.JAMacro;
import JAObjects.JANoList;
import JAObjects.JAObject;
import common.Expr;
import enums.EDevice;
import enums.EMsgType;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.Locale;
import output.IntelHexBuilder;

public class Javra {
	public	static	final	String	VERSION	= "0.1";
	
	public static void main(String[] args) throws Exception {
		if(0x00 == args.length) {
			show_help();
			System.exit(0);
		}
		for(String arg : args) {
			if(arg.equalsIgnoreCase("-h") || arg.equalsIgnoreCase("--help")) {
				show_help();
				System.exit(0);
			}
			else if(arg.equalsIgnoreCase("--version")) {
				System.out.println("JAVRA version: " + VERSION);
				System.exit(0);
			}
			else if(arg.equalsIgnoreCase("--devices")) {
				for(EDevice device : EDevice.values()) {
					//TODO
				}
				System.exit(0);
			}
		}
		
		//TODO args parsing
		
		long timestamp = System.currentTimeMillis();

		ProgInfo pi = new ProgInfo();
		
		String asm_filename = null;
		String hex_filename = null;
		String list_filename = null;
		String map_filename = null;
		
		Line line = new Line();
		int args_pos = 0x00;
		while(args.length > args_pos) {
			String arg = args[args_pos++];
			if(arg.equals("-o") && args.length > args_pos && args.length > args_pos) {
				hex_filename = arg;
			}
			else if((arg.equals("-l") || arg.equals("--listfile")) && args.length > args_pos) {
				list_filename = args[args_pos++];
			}
			else if((arg.equals("-m")  || arg.equals("--mapfile")) && args.length > args_pos) {
				map_filename = args[args_pos++];
			}
			else if((arg.equals("-D")  || arg.equals("--define")) && args.length > args_pos) {
				String[] parts = args[args_pos++].split("=", 2);
				String name = parts[0x00].trim().toLowerCase();
				if(!pi.is_undefined(line, name, false)) {
					System.exit(1);
				}
				Long value = 0l;
				if(0x02 == parts.length) {
					value = Expr.parse(pi, line, parts[0x01].trim().toLowerCase());
					if(null == value) {
						pi.print(EMsgType.MSG_ERROR, line, JAObject.MSG_UNKNOWN_LEXEME, " '" + line.get_failpart() + "'");
						System.exit(1);
					}
				}
				pi.add_constant(line, name, value, false);
			}
			else if((arg.equals("-I")  || arg.equals("--includepath")) && args.length > args_pos) {
				String tmp = args[args_pos++];
				if(!tmp.endsWith("/")) {
					tmp += "/";
				}
				pi.get_lib_paths().add(tmp);
			}
			else if(arg.equals("--listmac")) {
				pi.set_listmac();
			}
			else if(arg.equals("--max_errors") && args.length > args_pos) {
				try {
					pi.set_max_errors(Integer.parseInt(args[args_pos++]));
				}
				catch(Exception ex) {
					System.out.println("Error: --max_errors expected number");
				}
			}
			else if(!arg.contains("-")) {
				asm_filename = arg;
			}
			else {
				System.out.println("Error: invalid params " + args[args_pos-0x01]);
				System.exit(1);
			}
		}
		
		if(null == asm_filename) {
			pi.print("Error: You need to specify a file to assemble");
			System.exit(1);
		}

		pi.print("Assembly " + pi.get_root_path() + asm_filename + "\n...");
		
		int pos = asm_filename.lastIndexOf(".");
		if(null == hex_filename) {
			hex_filename = (-1 == pos ? asm_filename : asm_filename.substring(0, pos));
		}
		if(null == list_filename) {
			list_filename = (-1 == pos ? asm_filename : asm_filename.substring(0, pos)) + ".lst";
		}
		if(null == map_filename) {
			map_filename = (-1 == pos ? asm_filename : asm_filename.substring(0, pos)) + ".map";
		}
		
		new Parser(pi, asm_filename, new File(asm_filename));
		if(pi.is_terminating()) {
			pi.print("Maximum error count reached. Exiting...");
		}
		else {
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
				if(pi.is_terminating()) {
					pi.print("Maximum error count reached. Exiting...");
					break;
				}
				if(obj.is_expr_fail()) {
					pi.print(EMsgType.MSG_ERROR, obj.get_line(), JAObject.MSG_UNKNOWN_LEXEME, " '" + obj.get_line().get_failpart() + "'");
				}
			}

			FileOutputStream list_fos = null;
			try {
				boolean list_on = true;
				boolean listmac = pi.get_listmac();
				list_fos = new FileOutputStream(new File(list_filename));
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
		}
		
		if(0 != pi.get_error_cntr()) {
			System.out.println("\nBuild FAIL, warnings:" + pi.get_warning_cntr() + ", errors:" + pi.get_error_cntr() + "/" + pi.get_max_errors());
			System.exit(1);
		}
		else {
			System.out.println();
			if(!pi.get_cseg().is_empty()) {
				IntelHexBuilder hex_builder = new IntelHexBuilder(pi, hex_filename + "_cseg.hex");
				pi.get_cseg().build(pi, hex_builder);
				hex_builder.close();
				pi.get_cseg().print_stat(pi);
			}
			if(!pi.get_dseg().is_empty()) {
				IntelHexBuilder hex_builder = new IntelHexBuilder(pi, hex_filename + "_dseg.hex");
				pi.get_dseg().build(pi, hex_builder);
				hex_builder.close();				
				pi.get_dseg().print_stat(pi);
			}
			if(!pi.get_eseg().is_empty()) {
				IntelHexBuilder hex_builder = new IntelHexBuilder(pi, hex_filename + "_eseg.hex");
				pi.get_eseg().build(pi, hex_builder);
				hex_builder.close();
				pi.get_eseg().print_stat(pi);
			}

			float time = (System.currentTimeMillis() - timestamp) / 1000f;
			System.out.println("\n(parsed: " + Parser.get_line_qnt() + " lines, total time: " + String.format(Locale.US, "%.2f", time) + " s)");
			System.out.println("Build SUCCESS, warnings:" + pi.get_warning_cntr());
			System.exit(0);
		}
	}

	private static void show_help() {
		System.out.println("JAVRA: Java AVR macro assembler Version " + VERSION + ", licensed by GPL-3.0-or-later.");
		System.out.println();
		System.out.println("JAVRA is an open source assembler for Atmel AVR microcontroller family which is based on the AVRA project.");
		System.out.println("This project is distributed AS IS.\nThe author does not give any guarantees and is not responsible for the work " +
									"of the program. \nHowever, author adheres to the golden rule of morality.");
		System.out.println();
		System.out.println("If necessary, you can find the project on https://github.com/w5277c/javra or write to konstantin@5277.ru");
		System.out.println();
		System.out.println("usage:");
		System.out.println("avra\t[-o <filename>] output hex file name");
		System.out.println("\t[--listfile/-l <filename>] output list file name");
		System.out.println("\t[--mapfile/-m <filename>] output map file name");
		System.out.println("\t[--define/-D <symbol>[=<value>]] define symbol ('javra' already defined)");
		System.out.println("\t[--includepath/-I <dir>] additional include path");
		System.out.println("\t[--listmac] list macro expansion in listfile");
		System.out.println("\t[--max_errors <number>] maximum number of errors before exit (default: " + ProgInfo.DEF_MAX_ERROS + ")");
		System.out.println("\t[--devices] list out supported devices");
		System.out.println("\t[--version] version information");
		System.out.println("\t[--help/-h] this help text");
		System.out.println("\t<file to assemble>");
	}
}
