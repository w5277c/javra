/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
13.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

public class Label {
	private	Line		line;
	private	String	name;
	private	int		addr;
	
	public Label(Line l_line, String l_name, int l_addr) {
		line = l_line;
		name = l_name;
		addr = l_addr;
	}
	
	public Line get_line() {
		return line;
	}
	
	public String get_name() {
		return name;
	}
	
	public int get_addr() {
		return addr;
	}
}
