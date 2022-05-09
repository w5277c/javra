/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import java.util.LinkedList;

public class Macro {
	private	String				name;
	private	Line					line;
	private	LinkedList<Line>	body	= new LinkedList<>();
	
	public Macro(Line l_line, String l_name) {
		name = l_name;
	}
	
	public Line get_line() {
		return line;
	}
	
	public LinkedList<Line> get_body() {
		return body;
	}
}
