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
public enum EFunction {
	FUNC_LOW("low"),
	FUNC_BYTE1("byte1"),
	FUNC_HIGH("high"),
	FUNC_BYTE2("byte2"),
	FUNC_BYTE3("byte3"),
	FUNC_BYTE4("byte4"),
	FUNC_LWRD("lwrd"),
	FUNC_HWRD("hwrd"),
	FUNC_PAGE("page"),
	FUNC_EXP2("exp2"),
	FUNC_LOG2("log2"),
	FUNC_COUNT("count");
	
	private String text;
	
	private EFunction(String l_text) {
		text = l_text;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
