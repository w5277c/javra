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
public enum EMsgType {
	MSG_ERROR("ERR "),
	MSG_WARNING("WRN "),
	MSG_MESSAGE("MSG "),
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
