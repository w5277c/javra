/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAObjects;

import JAObjects.Directives.JADirective;
import main.Line;
import main.ProgInfo;

/**
 *
 * @author kostas
 */
public class JAObject {
	public	final	static	String	MSG_INVALID_NUMBER		= "invalid number";
	public	final	static	String	MSG_INVALID_SYNTAX		= "invalid syntax";
	public	final	static	String	MSG_ALREADY_DEFINED		= "already defined";
	public	final	static	String	MSG_UNKNOWN_DIRECTIVE	= "unknown directive";
	public	final	static	String	MSG_UNKNOWN_CONSTANT		= "unknown constant";
	public	final	static	String	MSG_ABSENT_FILE			= "absent_file";
	public	final	static	String	MSG_MISSING					= "missing ";
	public	final	static	String	MSG_UNSUPPORTED			= "unsupported ";
	public	final	static	String	MSG_ILLEGAL_OPERATOR		= "illegal operator: ";
	public	final	static	String	MSG_DIVISION_BY_ZERO		= "division by zero";
	public	final	static	String	MSG_WRONG_REGISTER		= "wrong register";
			  
	public	final	static	String	REG_CONST_NAME				= "[_|a-z][_\\d|a-z]*";
	
	protected JAObject() {
	}
	
	public static JAObject parse(ProgInfo l_pi, Line l_line) throws Exception {
		if(l_line.get_key().startsWith(".")) {
			return JADirective.parse(l_pi, l_line);
		}
		if(!l_pi.is_blockskip()) {
			//TODO
		}
		return null;
	}
	
	protected Long parse_addr(String l_value) {
		try {
			if(l_value.toLowerCase().startsWith("0x")) {
				return Long.parseLong(l_value.substring(0x02), 0x10);
			}
			else {
				return Long.parseLong(l_value);
			}
		}
		catch(Exception ex) {
		}
		return null;
	}
	
	protected Long parse_num(String l_value) {
		try {
			if(l_value.toLowerCase().startsWith("0x")) {
				return Long.parseLong(l_value.substring(0x02), 0x10);
			}
			else if(l_value.toLowerCase().startsWith("0b")) {
				return Long.parseLong(l_value.substring(0x02), 0x02);
			}
			else {
				return Long.parseLong(l_value);
			}
			//TODO ADD OCT
		}
		catch(Exception ex) {
		}
		return null;
	}
	
	protected String parse_string(String l_value) {
		try {
			String tmp = l_value.trim();
			if((tmp.startsWith("\"") && tmp.endsWith("\"")) || (tmp.startsWith("'") && tmp.endsWith("'"))) {
				return tmp.substring(0x01, tmp.length()-0x01);
			}
		}
		catch(Exception ex) {
		}
		return null;
	}
	
	protected Integer get_register(ProgInfo l_pi, String l_str) {
		if(l_str.startsWith("r") && l_str.length() > 0x01 && l_str.length() <= 0x03 && l_str.substring(0x01).replaceAll("\\d", "").isEmpty()) {
			return Integer.parseInt(l_str.substring(0x01));
		}
		else {
			for(int id=0x00; id < 0x20; id++) {
				
				if(l_pi.get_registers()[id].equals(l_str)) {
					return id;
				}
			}
		}
		return null;
	}
}
