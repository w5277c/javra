/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
20.09.2024	konstantin@5277.ru			Добавлен тип сообщения BUG
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package enums;

public enum EMsgType {
	MSG_ERROR("Error"),
	MSG_WARNING("Warning"),
	MSG_REPORT("Report"),
	MSG_DMESSAGE("Message"),
	MSG_DWARNING("Warning"),
	MSG_DERROR("Error"),
	MSG_DTODO("TODO"),
	MSG_DBUG("BUG"),
	MSG_INFO("");
	
	private String text;
	
	private EMsgType(String l_text) {
		text = l_text;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
