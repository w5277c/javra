/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

/**
 *
 * @author kostas
 */
public enum EOperator {
	OP_ERROR(""),
	OP_SH_LEFT("<<"),
	OP_SH_RIGHT(">>"),
	OP_LESS_OR_EQUAL("<="),
	OP_GREATER_OR_EQUAL(">="),
	OP_EQUALX2("=="),
	OP_NOT_EQUAL("!="),
	OP_BITWISE_AND("&&"),
	OP_BITWISE_OR("||"),
	OP_MUL("*"),
	OP_DIV("/"),
	OP_MOD("%"),
	OP_ADD("+"),
	OP_SUB("-"),
	OP_LESS_THAN("<"),
	OP_GREATER_THAN("<"),
	OP_EQUAL("="),
	OP_BITWISE_XOR("^"),
	OP_LOGICAL_AND("&"),
	OP_LOGICAL_OR("|");
	
	private String text;
	
	private EOperator(String l_text) {
		text = l_text;
	}
	
	public String get_text() {
		return text;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
