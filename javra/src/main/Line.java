/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
07.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

public class Line {
	private	String	filename;
	private	int		line_number;
	private	String	text;
	private	boolean	unparsed	= false;
	
	public Line(String l_filename, int l_number, String l_text) {
		filename = l_filename;
		line_number = l_number;
		text = l_text.replaceAll("\\s+", " ").trim();
	}
	
	public void append(String l_text) {
		text = text + l_text;
	}
	
	public int get_number() {
		return line_number;
	}
	
	public String get_filename() {
		return filename;
	}
	
	public String get_text() {
		return text;
	}
	
	public String get_location() {
		return filename + ":" + line_number;
	}
	
	public boolean is_unparsed() {
		return unparsed;
	}
	public void set_unparsed() {
		unparsed = true;
	}
}
