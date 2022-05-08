/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAObjects.Directives;

import JAObjects.Expr;
import JAObjects.JAObject;
import enums.EMsgType;
import main.Constant;
import main.Line;
import main.ORGInfo;
import main.ProgInfo;

/**
 *
 * @author kostas
 */
public class JADDef extends JADirective {
	private	String	name;
	private	Constant	constant;
	private	long		num;
	
	public JADDef(ProgInfo l_pi, Line l_line) {
		String parts[] = l_line.get_value().split("=");
		String tmp = parts[0x00].trim().toLowerCase();
		if(0x02 == parts.length && !tmp.isEmpty() && tmp.replaceAll(REG_CONST_NAME, "").isEmpty()) {
			name = tmp;
			Constant _constant = l_pi.get_constants().get(name);
			if(null != _constant) {
				l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_ALREADY_DEFINED, " at '" + _constant.get_line().get_location() + "'");
			}
			else {
				Integer _register = get_register(l_pi, name);
				if(null != _register) {
					l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_ALREADY_DEFINED, " as 'r" + _register + "'");
				}
				else {
					tmp = parts[0x01].trim().toLowerCase();
					Integer register = get_register(l_pi, tmp);
					if(null == register) {
						l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_WRONG_REGISTER);
					}
					else {
						l_pi.get_registers()[register] = name;
					}
				}
			}
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_INVALID_SYNTAX);
		}
	}
}
