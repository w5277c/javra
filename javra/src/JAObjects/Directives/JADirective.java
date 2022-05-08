/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAObjects.Directives;

import JAObjects.JAObject;
import enums.EMsgType;
import java.io.FileNotFoundException;
import main.Line;
import main.ProgInfo;

/**
 *
 * @author kostas
 */
public class JADirective extends JAObject {

	public static JADirective parse(ProgInfo l_pi, Line l_line) throws Exception {
		if(l_pi.is_blockskip()) {
			switch(l_line.get_key().toLowerCase()) {
				case ".ifdef":
					return new JADIfDef(l_pi, l_line);
				case ".ifndef":
					return new JADIfNDef(l_pi, l_line);
				case ".if":
					return new JADIf(l_pi, l_line);
				case ".endif":
					return new JADEndIf(l_pi, l_line);
				case ".else":
					return new JADElse(l_pi, l_line);
				default:
			}
		}
		else {
			switch(l_line.get_key().toLowerCase()) {
				case ".equ":
					return new JADEQU(l_pi, l_line);
				case ".set":
					return new JADSET(l_pi, l_line);
				case ".org":
					return new JADORG(l_pi, l_line);
				case ".include":
					return new JADInclude(l_pi, l_line);
				case ".device":
					return new JADDevice(l_pi, l_line);
				case ".ifdef":
					return new JADIfDef(l_pi, l_line);
				case ".ifndef":
					return new JADIfNDef(l_pi, l_line);
				case ".endif":
					return new JADEndIf(l_pi, l_line);
				case ".else":
					return new JADElse(l_pi, l_line);
				case ".if":
					return new JADIf(l_pi, l_line);
				case ".message":
					return new JADMessage(l_pi, l_line);
				case ".def":
					return new JADDef(l_pi, l_line);

			}
			l_pi.print(EMsgType.MSG_ERROR, l_line, MSG_UNKNOWN_DIRECTIVE);
		}
		return null;
	}
}
