/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

public class Constant {
	protected	Line		line;
	protected	String	name;
	protected	long		value;
	protected	boolean	redef;
	
	public Constant(Line l_line, String l_name, long l_value, boolean l_redef) {
		line = l_line;
		name = l_name;
		value = l_value;
		redef = l_redef;
	}
	
	public Constant(Line l_line, String l_name, long l_value) {
		line = l_line;
		name = l_name;
		value = l_value;
		redef = false;
	}

	public Line get_line() {
		return line;
	}
	
	public String get_name() {
		return name;
	}
	
	public long get_value() {
		return value;
	}
	
	public boolean is_redef() {
		return redef;
	}
}
