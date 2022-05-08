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
	private	String	key;
	private	String	value;
	
	
	public Line(String l_filename, int l_number, String l_text) {
		filename = l_filename;
		line_number = l_number;
		text = l_text;
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
	
	public void parse() {
		String[] parts = text.split("\\s", 0x02);
		key = parts[0x00];
		value = (parts.length > 0x01 ? parts[0x01] : "");
	}
	
	public String get_key() {
		return key;
	}
	public String get_value() {
		return value;
	}
	
	public String get_location() {
		return filename + ":" + line_number;
	}
}
