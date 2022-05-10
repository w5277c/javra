/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
07.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import JAObjects.JAObject;
import enums.EDevice;
import enums.EMsgType;
import enums.ESegmentType;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class ProgInfo {
	private	LinkedList<String>			lib_paths	= new LinkedList<>();
	private	SegmentInfo						cseg			= new SegmentInfo(ESegmentType.CODE);
	private	SegmentInfo						dseg			= new SegmentInfo(ESegmentType.DATA);
	private	SegmentInfo						eseg			= new SegmentInfo(ESegmentType.EEPROM);
	private	SegmentInfo						cur_seg		= cseg;
	private	int								max_errors	= 1000;
	private	int								error_cntr	= 0;
	private	int								warning_cntr	= 0;
	private	long								timestamp	= System.currentTimeMillis();
	private	HashMap<String,Constant>	constants	= new HashMap<>();
	private	HashMap<String,Macro>		macros		= new HashMap<>();
	private	Macro								cur_macros	= null;
	private	String							root_path;
	private	EDevice							device		= null;
	private	IncludeInfo						ii				= null;
	private	String[]							registers	= new String[32];	
	private	Line								cur_line		= null;

//	private	boolean	segment_overlap;   /* set by .NOOVERLAP, .OVERLAP     */
//	private	EPass	pass;

	public ProgInfo() throws IOException {
		root_path = new java.io.File(".").getCanonicalPath();
		if(!root_path.endsWith(File.separator)) {
			root_path += File.separator;
		}
		
		for(int id = 0x00; id < 0x20; id++) {
			registers[id] = ("r" + id);
		}
		
		constants.put("pc", new PCConstant(this));
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

	public Constant get_constant(String l_name) {
		return constants.get(l_name);
	}
	
	public boolean add_constant(Constant l_constant) {
		Constant constant = constants.get(l_constant.get_name());
		if(null == constant || constant.is_redef() || (constant.get_num(cur_line) == l_constant.get_num(cur_line) && !l_constant.is_redef())) {
			constants.put(l_constant.get_name(), l_constant);
			return true;
		}
		else {
			print(EMsgType.MSG_ERROR, JAObject.MSG_ALREADY_DEFINED, "at '" + constant.get_line().get_location() + "'");
			return false;
		}
	}
	
	public String get_root_path() {
		return root_path;
	}
	
	public LinkedList<String> get_lib_paths() {
		return lib_paths;
	}

	public EDevice get_device() {
		return device;
	}
	public void set_device(EDevice l_device) {
		if(null == device) {
			device = l_device;

			if(!add_constant(new Constant(cur_line, "prog_flash", device.get_flash_size()))) {
				print(EMsgType.MSG_ERROR, "PROG_FLASH already defined");
			}
			if(!add_constant(new Constant(cur_line, "ram_start", device.get_ram_start()))) {
				print(EMsgType.MSG_ERROR, "RAM_START already defined");
			}
			if(!add_constant(new Constant(cur_line, "ram_end", device.get_ram_start() + device.get_ram_size() - 0x01))) {
				print(EMsgType.MSG_ERROR, "RAM_END already defined");
			}
			if(!add_constant(new Constant(cur_line, "eeprom_size", device.get_eeprom_size()))) {
				print(EMsgType.MSG_ERROR, "EEPROM_SIZE already defined");
			}
		}
		else {
			print(EMsgType.MSG_ERROR, "More than one .DEVICE definition");
		}
	}

	public void print(String... l_messages) {
		for(String msg : l_messages) {
			System.out.print(msg);
		}
		System.out.println();
	}

	public void print(EMsgType l_msg_type, String... l_messages) {
		System.out.print(l_msg_type);
		if(EMsgType.MSG_ERROR == l_msg_type) {
			error_cntr++;
			System.out.print("[" + error_cntr + "]");
			
		}
		else if(EMsgType.MSG_WARNING == l_msg_type) {
			warning_cntr++;
			System.out.print("[" + warning_cntr + "]");
		}
		if(null != cur_line && EMsgType.MSG_MESSAGE != l_msg_type) {
			System.out.print("line " + cur_line.get_number());
		}
		System.out.print(": ");
		for(String msg : l_messages) {
			System.out.print(msg);
		}
		if(null != cur_line && EMsgType.MSG_MESSAGE != l_msg_type) {
			System.out.print(" " + cur_line.get_text().trim());
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
	
	public boolean create_macro(String l_name) {
		if(null == cur_macros) {
			cur_macros = new Macro(cur_line, l_name);
			macros.put(l_name, cur_macros);
			return true;
		}
		return false;
	}
	
	public HashMap<String, Macro> get_macros() {
		return macros;
	}

	public Macro get_cur_macros() {
		return cur_macros;
	}
	
	public boolean close_macro() {
		if(null != cur_macros) {
			cur_macros = null;
			return true;
		}
		return false;
	}
	
	public int get_error_cntr() {
		return error_cntr;
	}
	public int get_warning_cntr() {
		return warning_cntr;
	}
	public int get_max_errors() {
		return max_errors;
	}

	public void set_line(Line l_line) {
		cur_line = l_line;
	}
	public Line get_cur_line() {
		return cur_line;
	}
}
