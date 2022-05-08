/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAObjects.Directives;

import JAObjects.Expr;
import JAObjects.JAObject;
import enums.EMsgType;
import main.Line;
import main.ORGInfo;
import main.ProgInfo;

/**
 *
 * @author kostas
 */
public class JADORG extends JADirective {
	public JADORG(ProgInfo l_pi, Line l_line) {
		Long addr = Expr.parse(l_pi, l_line, l_line.get_value());
		if(null == addr) {
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_INVALID_NUMBER);
		}
		else {
			l_pi.get_cur_segment().get_orglist().add(new ORGInfo(addr));
		}
	}
}
