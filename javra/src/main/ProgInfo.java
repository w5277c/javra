/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
07.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import enums.EMsgType;
import enums.EPass;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class ProgInfo {
	private	LinkedList<String>			lib_paths	= new LinkedList<>();
	private	SegmentInfo						cseg			= new SegmentInfo();
	private	SegmentInfo						dseg			= new SegmentInfo();
	private	SegmentInfo						eseg			= new SegmentInfo();
	private	SegmentInfo						cur_seg		= cseg;
	private	int								max_errors	= 10;
	private	int								error_cntr	= 0;
	private	int								warning_ctr	= 0;
	private	long								timestamp	= System.currentTimeMillis();
	private	HashMap<String,Constant>	constants	= new HashMap<>();
	private	HashMap<String,Macros>		macros		= new HashMap<>();
	private	Macros							cur_macros	= null;
	private	String							root_path;
	private	String							device		= null;
	private	IncludeInfo						ii				= null;
	private	String[]							registers	= new String[32];	
	

//	private	boolean	segment_overlap;   /* set by .NOOVERLAP, .OVERLAP     */
//	private	EPass	pass;

	public ProgInfo() throws IOException {
		root_path = new java.io.File(".").getCanonicalPath();
		for(int id = 0x00; id < 0x20; id++) {
			registers[id] = ("r" + id);
		}
	}
	
	
/*	public EPass get_pass() {
		return pass;
	}
	
	public boolean get_segment_overlap() {
		return segment_overlap;
	}*/
	
	public SegmentInfo get_cur_segment() {
		return cur_seg;
	}
	public SegmentInfo get_cseg() {
		return cseg;
	}
	public SegmentInfo get_dseg() {
		return dseg;
	}
	public SegmentInfo get_eseg() {
		return eseg;
	}

	public HashMap<String, Constant> get_constants() {
		return constants;
	}
	
	public String get_root_path() {
		return root_path;
	}
	
	public LinkedList<String> get_lib_paths() {
		return lib_paths;
	}

	public String get_device() {
		return device;
	}
	public void set_device(String l_device) {
		device = l_device;
	}

	public void print(EMsgType l_msg_type, Line l_line, String... l_messages) {
		System.out.print(l_msg_type);
		if(EMsgType.MSG_ERROR == l_msg_type) {
			error_cntr++;
			System.out.print("[" + error_cntr + "]");
			
		}
		else if(EMsgType.MSG_WARNING == l_msg_type) {
			System.out.print("[" + warning_ctr + "]");
		}
		if(null != l_line) {
			System.out.print("line:" + l_line.get_number() + " ");
		}
		for(String msg : l_messages) {
			System.out.print(msg);
		}
		if(null != l_line) {
			System.out.print(" " + l_line.get_text().trim());
		}
		System.out.println();
	}
	
	public boolean is_terminating() {
		return (max_errors == error_cntr);
	}

	public IncludeInfo exch_ii(IncludeInfo l_ii) {
		IncludeInfo old_ii = ii;
		ii = l_ii;
		return old_ii;
	}

	public IncludeInfo get_ii() {
		return ii;
	}
	
	public String[] get_registers() {
		return registers;
	}
	
	public boolean create_macros(String l_name) {
		if(null == cur_macros) {
			cur_macros = new Macros(l_name);
			macros.put(l_name, cur_macros);
			return true;
		}
		return false;
	}
	
	public HashMap<String, Macros> get_macros() {
		return macros;
	}

	public Macros get_cur_macros() {
		return cur_macros;
	}
	
	public boolean close_macros() {
		if(null != cur_macros) {
			cur_macros = null;
			return true;
		}
		return false;
	}
}
