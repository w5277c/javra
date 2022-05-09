/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
07.03.2022	konstantin@5277.ru			Начало
----------------------------------------------------------------------------------------------------------------------------------------------------------------
TODO: Вынести текущую Line в ProgInfo.
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import java.io.File;

public class Javra {
	public	static	final	String	VERSION	= "0.0.1";
	
	public static void main(String[] args) throws Exception {
		ProgInfo pi = new ProgInfo();
		
		int args_pos = 0x00;
		while(args.length > args_pos) {
			String arg = args[args_pos++];
			if(arg.equals("-l") && args.length > args_pos) {
				pi.get_lib_paths().add(args[args_pos++]);
			}
			else {
				System.out.println("invalid params " + args[args_pos-0x01]);
			}
		}
		
		Parser parser = new Parser(pi, new File("main.asm"));
		if(0 != pi.get_error_cntr()) {
			System.out.println("\nBuild fail, wrns:" + pi.get_warning_cntr() + ", errs:" + pi.get_error_cntr() + "/" + pi.get_max_errors());
		}
		else {
			System.out.println("\nBuild sucess, wrns:" + pi.get_warning_cntr());
		}
	}
	
}
