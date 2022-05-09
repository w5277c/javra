/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

public class Constant {
	private	Line		line;
	private	String	name;
	private	long		num;
	private	boolean	redef;
	
	public Constant(Line l_line, String l_name, long l_num, boolean l_redef) {
		line = l_line;
		name = l_name;
		num = l_num;
		redef = l_redef;
	}
	
	public Constant(Line l_line, String l_name, long l_num) {
		line = l_line;
		name = l_name;
		num = l_num;
		redef = false;
	}

	public Line get_line() {
		return line;
	}
	
	public String get_name() {
		return name;
	}
	
	public long get_num() {
		return num;
	}
	
	public boolean is_redef() {
		return redef;
	}
}
