/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
07.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import enums.EMsgType;
import enums.EPass;
import java.io.File;
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
	private	String							root_path;
	private	String							device		= null;
	private	int								block_cntr	= 0;
	private	boolean							blockskip	= false;
	private	String[]							registers	= new String[32];	
	

//struct args *args;
	//struct device *device;
	//struct macro_call *macro_call;
	//struct macro_line *macro_line;
	//FILE *list_file;
	//int list_on;
	//int map_on;
	//char *list_line;
	//char *root_path;
	//FILE *obj_file;
	//struct include_file *last_include_file;
	//struct include_file *first_include_file;
	//struct def *first_def;
	//struct def *last_def;
	//struct label *first_label;
	//struct label *last_label;
	//struct label *first_constant;
	//struct label *last_constant;
	//struct label *first_variable;
	//struct label *last_variable;
	//struct location *first_ifdef_blacklist;
	//struct location *last_ifdef_blacklist;
	//struct location *first_ifndef_blacklist;
	//struct location *last_ifndef_blacklist;
	//struct macro *first_macro;
	//struct macro *last_macro;
	//struct macro_call *first_macro_call;
	//struct macro_call *last_macro_call;
	//struct orglist *first_orglist;	/* List of used memory segments. Needed for overlap-check */
	//struct orglist *last_orglist;
	//int effective_overlap; /* as specified by #pragma overlap */
	private	boolean	segment_overlap;   /* set by .NOOVERLAP, .OVERLAP     */
	//int conditional_depth;
	/* coff additions */
	//FILE *coff_file;
	/* Warning additions */
	//int NoRegDef;
	private	EPass	pass;

	public ProgInfo() throws IOException {
		root_path = new java.io.File(".").getCanonicalPath();
		for(int id = 0x00; id < 0x20; id++) {
			registers[id] = ("r" + id);
		}
	}
	
	
	public EPass get_pass() {
		return pass;
	}
	
	public boolean get_segment_overlap() {
		return segment_overlap;
	}
	
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

	public boolean is_blockskip() {
		return blockskip;
	}
	
	public void block_start(Line l_line, boolean l_skip) {
		blockskip = l_skip;
		block_cntr++;
	}
	public void block_end(Line l_line) {
		block_cntr--;
	}
	public void block_skip_invet() {
		blockskip = !blockskip;
	}

	public int get_blockcntr() {
		return  block_cntr;
	}
	
	public void set_blockcntr(int l_blockcntr) {
		block_cntr = l_blockcntr;
	}
	
	public String[] get_registers() {
		return registers;
	}
}
