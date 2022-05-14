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
import enums.ESegmentType;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
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
	private	HashMap<String,Macro>		macros			= new HashMap<>();
	private	Macro								cur_macros		= null;
	private	Macro								expand_macro	= null;
	private	String							root_path;
	private	EDevice							device			= null;
	private	IncludeInfo						ii					= null;
	private	HashMap<String,Integer>		registers		= new HashMap<>();
	private	Line								cur_line			= null;
	private	HashMap<String, Line>		unparsed			= new HashMap<>();
	private	List								list				= new List("unnamed.lst");
	
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
	public boolean add_constant(String l_name, long l_value, boolean l_redef) {
		if(is_undefined(l_name, l_value, l_redef)) {
			Constant constatnt = new Constant(cur_line, l_name, l_value, l_redef);
			if(null == cur_macros) {
				constants.put(l_name, constatnt);
			}
			else {
				cur_macros.add_constant(constatnt);
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
	public boolean add_label(String l_name) {
		if(is_undefined(l_name, false)) {
			int addr = get_cur_segment().get_cur_block().get_addr();
			if(null == cur_macros) {
				labels.put(l_name, new Label(cur_line, l_name, addr));
			}
			else {
				cur_macros.add_label(new Label(cur_line, l_name, addr));
			}
			list.push_label(addr, l_name);
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
	public void open_macro(String l_name) {
		cur_macros = macros.get(l_name);
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
		Label label = get_label(l_name);
		if(null != label) {
			print(EMsgType.MSG_ERROR, "Label '", l_name, "' ", JAObject.MSG_ALREADY_DEFINED, " at " + label.get_line().get_location());
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
	
	public List get_list() {
		return list;
	}

	public Macro get_expand_macro() {
		return expand_macro;
	}
	public void set_expand_macro(Macro l_macro) {
		expand_macro = l_macro;
	}

	public void put_unparsed() {
		if(null == unparsed.get(cur_line.get_location())) {
			cur_line.set_addr(get_cseg().get_cur_block().get_addr());
			unparsed.put(cur_line.get_location(), cur_line);
		}
	}
	
	public int  unparsed_qnt() { 
		return unparsed.size();
	}
	public LinkedList<Line> pull_unparsed() {
		LinkedList<Line> result = new LinkedList<Line>(unparsed.values());
		unparsed.clear();
		Collections.sort(result, new Comparator<Line>() {
			@Override
			public int compare(Line o1, Line o2) {
				return o1.get_addr().compareTo(o2.get_addr());
			}
		});
		return result;
	}
}
