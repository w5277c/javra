/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
07.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import JAObjects.JAObject;
import common.Macro;
import enums.EDevice;
import enums.EMsgType;
import enums.EPass;
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
	private	HashMap<String,Integer>		registers	= new HashMap<>();
	private	Line								cur_line		= null;
	private	EPass								pass			= EPass.PASS_1;
	private	boolean							list_on		= true;
	private	boolean							listmac_on		= false;
	
//	private	boolean	segment_overlap;   /* set by .NOOVERLAP, .OVERLAP     */
//	private	EPass	pass;

	public ProgInfo() throws IOException {
		root_path = new java.io.File(".").getCanonicalPath();
		if(!root_path.endsWith(File.separator)) {
			root_path += File.separator;
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
		if(null != cur_macros) {
			Constant result = cur_macros.get_labels().get(l_name);
			if(null != result) return result;
		}
		return constants.get(l_name);
	}
	public boolean add_constant(String l_name, long l_value, boolean l_redef) {
		if(is_undefined(l_name, l_value, l_redef)) {
			constants.put(l_name, new Constant(cur_line, l_name, l_value, l_redef));
			return true;
		}
		return false;
	}
	public boolean add_label(String l_name) {
		if(is_undefined(l_name, false)) {
			long addr = get_cur_segment().get_datablock().get_addr();
			if(null == cur_macros) {
				constants.put(l_name, new Constant(cur_line, l_name, addr));
			}
			else {
				cur_macros.get_labels().put(l_name, new Constant(cur_line, l_name, addr));
			}
			return true;
		}
		return false;
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

			if(!add_constant("prog_flash", device.get_flash_size(), false)) {
				print(EMsgType.MSG_ERROR, "PROG_FLASH already defined");
			}
			if(!add_constant("ram_start", device.get_ram_start(), false)) {
				print(EMsgType.MSG_ERROR, "RAM_START already defined");
			}
			if(!add_constant("ram_end", device.get_ram_start() + device.get_ram_size() - 0x01, false)) {
				print(EMsgType.MSG_ERROR, "RAM_END already defined");
			}
			if(!add_constant("eeprom_size", device.get_eeprom_size(), false)) {
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
		if(null != cur_line && EMsgType.MSG_DMESSAGE != l_msg_type && EMsgType.MSG_DWARNING != l_msg_type && EMsgType.MSG_DERROR != l_msg_type) {
			System.out.print("line " + cur_line.get_number());
		}
		System.out.print(": ");
		for(String msg : l_messages) {
			System.out.print(msg);
		}
		if(null != cur_line && EMsgType.MSG_DMESSAGE != l_msg_type && EMsgType.MSG_DWARNING != l_msg_type && EMsgType.MSG_DERROR != l_msg_type) {
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
	
	public EPass get_pass() {
		return pass;
	}
	
	public Integer get_register(String l_name) {
		if(l_name.startsWith("r") && l_name.length() > 0x01 && l_name.length() <= 0x03 && l_name.substring(0x01).replaceAll("\\d", "").isEmpty()) {
			return Integer.parseInt(l_name.substring(0x01));
		}
		if(l_name.equals("xl") || l_name.equals("x")) {
			return 26;
		}
		if(l_name.equals("xh")) {
			return 27;
		}
		if(l_name.equals("yl") || l_name.equals("y")) {
			return 28;
		}
		if(l_name.equals("yh")) {
			return 29;
		}
		if(l_name.equals("zl") || l_name.equals("z")) {
			return 30;
		}
		if(l_name.equals("zh")) {
			return 31;
		}

		return registers.get(l_name);
	}
	public void put_register(String l_name, int l_register_id) {
		if(registers.values().contains(l_register_id)) {
			print(EMsgType.MSG_WARNING, l_name + "(r" + l_register_id + ") already assigned");
		}
		registers.put(l_name, l_register_id);
	}
	public void remove_register(String l_name) {
		if(null == registers.remove(l_name)) {
			print(EMsgType.MSG_WARNING, l_name + " register not defined");
		}
	}
	
	public boolean is_undefined(String l_name,boolean l_redef) {
		return is_undefined(l_name, null, l_redef);
	}
	public boolean is_undefined(String l_name, Long l_value, boolean l_redef) {
		Constant constant = get_constant(l_name);
		if(null != constant && (!l_redef || !constant.is_redef()) && (null == l_value || l_value != constant.get_value())) {
			print(EMsgType.MSG_ERROR, JAObject.MSG_ALREADY_DEFINED, " at '" + constant.get_line().get_location() + "'");
			return false;
		}
		Integer register_id = get_register(l_name);
		if(null != register_id) {
			print(EMsgType.MSG_ERROR, JAObject.MSG_ALREADY_DEFINED, " as 'r" + Integer.toString(register_id) + "'");
			return false;
		}
		Macro macros = get_macros().get(l_name);
		if(null != macros) {
			print(EMsgType.MSG_ERROR, JAObject.MSG_ALREADY_DEFINED, " at '" + macros.get_line().get_location() + "'");
			return false;
		}
		
		//TODO добавить остальне проверки
		
		return true;
	}
	
	public boolean is_list_on() {
		return list_on;
	}
	public void set_list_on(boolean l_is_on) {
		list_on = l_is_on;
	}
	public boolean is_listmac_on() {
		return listmac_on;
	}
	public void set_listmac_on(boolean l_is_on) {
		listmac_on = l_is_on;
	}
}
