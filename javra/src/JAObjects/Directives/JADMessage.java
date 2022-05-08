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
public class JADMessage extends JADirective {
	public JADMessage(ProgInfo l_pi, Line l_line) throws Exception {
		l_pi.print(EMsgType.MSG_MESSAGE, null, l_line.get_value());
	}
}
