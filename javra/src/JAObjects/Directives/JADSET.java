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
public class JADSET extends JADirective {
	private	String	name;
	private	Constant	constant;
	private	long		num;
	
	public JADSET(ProgInfo l_pi, Line l_line) {
		String parts[] = l_line.get_value().split("=");
		String tmp = parts[0x00].trim().toLowerCase();
		if(0x02 == parts.length && !tmp.isEmpty() && tmp.replaceAll(REG_CONST_NAME, "").isEmpty()) {
			name = tmp;
			tmp = parts[0x01].trim().toLowerCase();
			Long _num = Expr.parse(l_pi, l_line, tmp);
			if(null == _num) {
				constant = l_pi.get_constants().get(tmp);
				if(null == constant) {
					l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_UNKNOWN_CONSTANT);
				}
			}
			else {
				num = _num;
			}
			l_pi.get_constants().put(name, new Constant(l_line, name, (null == constant ? num : constant.get_num())));
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_INVALID_SYNTAX);
		}
	}
}
