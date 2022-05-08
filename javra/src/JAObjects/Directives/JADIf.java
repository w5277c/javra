/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAObjects.Directives;

import JAObjects.Expr;
import JAObjects.JAObject;
import enums.EMsgType;
import java.io.File;
import java.io.FileNotFoundException;
import main.Constant;
import main.Line;
import main.ORGInfo;
import main.Parser;
import main.ProgInfo;

/**
 *
 * @author kostas
 */
public class JADIf extends JADirective {
	public JADIf(ProgInfo l_pi, Line l_line) throws Exception {
		String tmp = l_line.get_value().trim().toLowerCase();
		if(!tmp.isEmpty()) {
			Long _tmp = Expr.parse(l_pi, l_line, tmp);
			if(null == _tmp) {
				l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_INVALID_SYNTAX);
			}
			else {
				l_pi.block_start(l_line, 0x00 == _tmp);
			}
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_INVALID_SYNTAX);
		}
	}
}
