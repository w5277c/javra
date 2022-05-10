/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package enums;

public enum EOperator {
	OP_SH_LEFT("<<", 8),
	OP_SH_RIGHT(">>", 8),
	OP_LESS_OR_EQUAL("<=", 7),
	OP_GREATER_OR_EQUAL(">=", 7),
	OP_EQUALX2("==", 6),
	OP_NOT_EQUAL("!=", 6),
	OP_BITWISE_AND("&&", 5),
	OP_BITWISE_OR("||", 3),
	OP_MUL("*", 10),
	OP_DIV("/", 10),
	OP_MOD("%", 10),
	OP_ADD("+", 9),
	OP_SUB("-", 9),
	OP_LESS_THAN("<", 7),
	OP_GREATER_THAN(">", 7),
	OP_EQUAL("=", 6),
	OP_BITWISE_XOR("^", 4),
	OP_LOGICAL_AND("&", 2),
	OP_LOGICAL_OR("|", 1);
	
	private	String	text;
	private	int		priority;
	
	private EOperator(String l_text, int l_priority) {
		text = l_text;
		priority = l_priority;
	}
	
	public int get_priority() {
		return priority;
	}
	
	public String get_text() {
		return text;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
