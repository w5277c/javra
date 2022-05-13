/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import java.util.LinkedList;

public class IncludeInfo {
	private	String					name;
	private	boolean					block_success	= false;
	private	boolean					elseif_skip		= false;
	private	LinkedList<Boolean>	block_skip		= new LinkedList<>();
	private	int						block_cntr		= 0;
	
	public IncludeInfo(String l_name) {
		name = l_name;
	}
	
	public boolean is_blockskip() {
		boolean result = elseif_skip;
		for(Boolean skip : block_skip) {
			result |=skip;
		}
		return result;
	}
	
	public void block_start(Line l_line, boolean l_skip) {
		block_success |= !l_skip;
		
		block_skip.add(l_skip);
		block_cntr++;
		//System.out.println("BLCK_STRT:" + l_line.get_location() + ", cntr:" + block_skip.size() + ", skip:" + is_blockskip() + " " + l_line.get_text());
	}
	public void block_end(Line l_line) {
		elseif_skip = false;
		block_success = false;
		
		block_cntr--;
		if(!block_skip.isEmpty()) {
			block_skip.removeLast();
		}
		//System.out.println("BLCK_END:" + l_line.get_location() + ", cntr:" + block_skip.size() + ", skip:" + is_blockskip() + " " + l_line.get_text());
	}
	public void block_skip_invert(Line l_line) {
		if(!block_skip.isEmpty()) {
			block_skip.add(!block_skip.removeLast());
		}
		//System.out.println("BLCK_INVERT:" + l_line.get_location() + ", cntr:" + block_skip.size() + ", skip:" + is_blockskip() + " " + l_line.get_text());
	}
	public void block_elseif(boolean l_skip) {
		if(!block_success) {
			elseif_skip = l_skip;
			block_success |= !l_skip;
		}
	}
	public int get_blockcntr() {
		return  block_cntr;
	}
	
	public String get_name() {
		return name;
	}
}
