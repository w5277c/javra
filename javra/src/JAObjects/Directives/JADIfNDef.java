/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAObjects.Directives;

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
public class JADIfNDef extends JADirective {
	private	String	value;
	
	public JADIfNDef(ProgInfo l_pi, Line l_line) throws Exception {
		String tmp = l_line.get_value().trim().toLowerCase();
		if(!tmp.isEmpty()) {
			l_pi.block_start(l_line, null != l_pi.get_constants().get(tmp));
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_INVALID_SYNTAX);
		}
	}
}
