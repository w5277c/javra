/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
07.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import JAObjects.JAMacro;
import JAObjects.JAObject;
import enums.EDevice;
import enums.EMsgType;
import enums.ESegmentType;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class ProgInfo {
	private	LinkedList<String>			lib_paths		= new LinkedList<>();
	private	CodeSegmentInfo				cseg				= new CodeSegmentInfo();
	private	SegmentInfo						dseg				= new SegmentInfo(ESegmentType.DATA);
	private	SegmentInfo						eseg				= new SegmentInfo(ESegmentType.EEPROM);
	private	SegmentInfo						cur_seg			= cseg;
	private	int								max_errors		= 1000;
	private	int								error_cntr		= 0;
	private	int								warning_cntr	= 0;
	private	long								timestamp		= System.currentTimeMillis();
	private	HashMap<String,Constant>	constants		= new HashMap<>();
	private	HashMap<String,Label>		labels			= new HashMap<>();
	private	HashMap<String,JAMacro>		macros			= new HashMap<>();
	private	JAMacro							cur_macro		= null;
	private	JAMacro							expand_macro	= null;
	private	String							root_path;
	private	EDevice							device			= null;
	private	IncludeInfo						ii					= null;
	private	HashMap<String,Integer>		registers		= new HashMap<>();
	private	LinkedList<JAObject>			objects			= new LinkedList<>();
	
//	private	boolean	segment_overlap;   /* set by .NOOVERLAP, .OVERLAP     */

	public ProgInfo() throws IOException {
		root_path = new java.io.File(".").getCanonicalPath();
		if(!root_path.endsWith(File.separator)) {
			root_path += File.separator;
		}
		
		constants.put("pc", new PCConstant(this));
	}
	
	
/*public boolean get_segment_overlap() {
		return segment_overlap;
	}*/
	
	public SegmentInfo get_cur_segment() {
		return cur_seg;
	}
	public CodeSegmentInfo get_cseg() {
		return cseg;
	}
	public SegmentInfo get_dseg() {
		return dseg;
	}
	public SegmentInfo get_eseg() {
		return eseg;
	}

	public Constant get_constant(String l_name) {
		Constant result = null;
		if(null != expand_macro) {
			result = expand_macro.get_constant(l_name);
		}
		if(null == result) {
			result = constants.get(l_name);
		}
		return result;
	}
	public boolean add_constant(Line l_line, String l_name, long l_value, boolean l_redef) {
		if(is_undefined(l_line, l_name, l_value, l_redef)) {
			Constant constatnt = new Constant(l_line, l_name, l_value, l_redef);
			if(null == cur_macro) {
				constants.put(l_name, constatnt);
			}
			else {
				cur_macro.add_constant(constatnt);
			}
			return true;
		}
		return false;
	}
	
	public Label get_label(String l_name) {
		Label result = null;
		if(null != expand_macro) {
			result = expand_macro.get_label(l_name);
		}
		if(null == result) {
			result = labels.get(l_name);
		}
		return result;
	}
	public boolean add_label(Line l_line, String l_name) {
		if(is_undefined(l_line, l_name, false)) {
			int addr = get_cur_segment().get_cur_block().get_addr();
			if(null == cur_macro) {
				labels.put(l_name, new Label(l_line, l_name, addr));
			}
			else {
				cur_macro.add_label(new Label(l_line, l_name, addr));
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
	public void set_device(Line l_line, EDevice l_device) {
		if(null == device) {
			device = l_device;

			if(!add_constant(l_line, "prog_flash", device.get_flash_size(), false)) {
				print(EMsgType.MSG_ERROR, l_line, "PROG_FLASH already defined");
			}
			if(!add_constant(l_line, "ram_start", device.get_ram_start(), false)) {
				print(EMsgType.MSG_ERROR, l_line, "RAM_START already defined");
			}
			if(!add_constant(l_line, "ram_end", device.get_ram_start() + device.get_ram_size() - 0x01, false)) {
				print(EMsgType.MSG_ERROR, l_line, "RAM_END already defined");
			}
			if(!add_constant(l_line, "eeprom_size", device.get_eeprom_size(), false)) {
				print(EMsgType.MSG_ERROR, l_line, "EEPROM_SIZE already defined");
			}
		}
		else {
			print(EMsgType.MSG_ERROR, l_line, "More than one .DEVICE definition");
		}
	}

	public void print(String... l_messages) {
		for(String msg : l_messages) {
			System.out.print(msg);
		}
		System.out.println();
	}

	public void print(EMsgType l_msg_type, Line l_line, String... l_messages) {
		System.out.print(l_msg_type);
		if(EMsgType.MSG_ERROR == l_msg_type) {
			error_cntr++;
			System.out.print("[" + error_cntr + "]");
			
		}
		else if(EMsgType.MSG_WARNING == l_msg_type) {
			warning_cntr++;
			System.out.print("[" + warning_cntr + "]");
		}
		if(null != l_line && EMsgType.MSG_DMESSAGE != l_msg_type && EMsgType.MSG_DWARNING != l_msg_type && EMsgType.MSG_DERROR != l_msg_type) {
			System.out.print(" " + l_line.get_filename() + "(" + l_line.get_line_number() + ")");
		}
		System.out.print(": ");
		for(String msg : l_messages) {
			System.out.print(msg);
		}
		if(null != l_line && EMsgType.MSG_DMESSAGE != l_msg_type && EMsgType.MSG_DWARNING != l_msg_type && EMsgType.MSG_DERROR != l_msg_type) {
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
	
	public boolean create_macro(JAMacro l_macro, String l_name) {
		if(null == cur_macro) {
			cur_macro = l_macro; 
			macros.put(l_name, cur_macro);
			return true;
		}
		return false;
	}
	
//	public void open_macro(String l_name) {
//		cur_macro = macros.get(l_name);
//	}

//	public HashMap<String, JAMacro> get_macros() {
//		return macros;
//	}
	
	public JAMacro get_macro(String l_name) {
		return macros.get(l_name);
	}

	public JAMacro get_cur_macros() {
		return cur_macro;
	}
	
	public boolean close_macro() {
		if(null != cur_macro) {
			cur_macro = null;
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
	public void put_register(Line l_line, String l_name, int l_register_id) {
		if(registers.values().contains(l_register_id)) {
			print(EMsgType.MSG_WARNING, l_line, l_name + "(r" + l_register_id + ") already assigned");
		}
		registers.put(l_name, l_register_id);
	}
	public void remove_register(Line l_line, String l_name) {
		if(null == registers.remove(l_name)) {
			print(EMsgType.MSG_WARNING, l_line, l_name + " register not defined");
		}
	}
	
	public boolean is_undefined(Line l_line, String l_name,boolean l_redef) {
		return is_undefined(l_line, l_name, null, l_redef);
	}
	public boolean is_undefined(Line l_line, String l_name, Long l_value, boolean l_redef) {
		Constant constant = get_constant(l_name);
		if(null != constant && (!l_redef || !constant.is_redef()) && (null == l_value || l_value != constant.get_value())) {
			print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_ALREADY_DEFINED, " at '" + constant.get_line().get_location() + "'");
			return false;
		}
		Label label = get_label(l_name);
		if(null != label) {
			print(EMsgType.MSG_ERROR, l_line, "Label '", l_name, "' ", JAObject.MSG_ALREADY_DEFINED, " at " + label.get_line().get_location());
			return false;
		}
		Integer register_id = get_register(l_name);
		if(null != register_id) {
			print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_ALREADY_DEFINED, " as 'r" + Integer.toString(register_id) + "'");
			return false;
		}
		JAMacro macro = get_macro(l_name);
		if(null != macro) {
			print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_ALREADY_DEFINED, " at '" + macro.get_line().get_location() + "'");
			return false;
		}
		
		//TODO добавить остальне проверки
		
		return true;
	}
	
//	public Macro get_expand_macro() {
//		return expand_macro;
//	}
//	public void set_expand_macro(JAMacro l_macro) {
//		expand_macro = l_macro;
//	}

	public void add_object(JAObject l_obj) {
		objects.add(l_obj);
	}
	public LinkedList<JAObject> get_objects() {
		return objects;
	}
}
