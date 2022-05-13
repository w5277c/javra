/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.File;
import java.io.FileOutputStream;

/**
 *
 * @author kostas
 */
public class List {
	private	String							filename;
	private	FileOutputStream				fos			= null;
	private	boolean							list_on		= true;
	private	boolean							listmac_on	= false;

	public List(String l_filename) {
		filename = l_filename;
	}
	
	public void push_opcode(int l_addr, Integer l_opcode1, Integer l_opcode2, String l_text) {
		if(list_on) {
			if(null == fos) {
				try {
					fos = new FileOutputStream(new File(filename));
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			if(null == fos) {
				return;
			}
			try {
				if(null == l_opcode1 && null == l_opcode2) {
					fos.write(("\n").getBytes("ASCII"));
				}
				fos.write(("0x" + String.format("%04X", l_addr)).getBytes("ASCII"));
				if(null != l_opcode1) {
					 fos.write((" " + String.format("%04X", l_opcode1)).getBytes("ASCII"));
				}
				if(null != l_opcode2) {
					fos.write((String.format("%04X", l_opcode2)).getBytes("ASCII"));
				}
				if(null == l_opcode1 && null == l_opcode2) {
					fos.write((" " + l_text.toUpperCase() + ":\n").getBytes("ASCII"));
				}
				else {
					fos.write((" " + l_text + "\n").getBytes("ASCII"));
				}
				fos.flush();
			}
			catch(Exception ex) {
			}
		}
	}
	
	public void push_label(int l_addr, String l_text) {
		if(list_on) {
			if(null == fos) {
				try {
					fos = new FileOutputStream(new File(filename));
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			if(null == fos) {
				return;
			}
			try {
				fos.write(("\n").getBytes("ASCII"));
				fos.write(("0x" + String.format("%04X", l_addr)).getBytes("ASCII"));
				fos.write((" " + l_text.toUpperCase() + ":\n").getBytes("ASCII"));
				fos.flush();
			}
			catch(Exception ex) {
			}
		}
	}

	public void push_include(String l_text) {
		if(list_on) {
			if(null == fos) {
				try {
					fos = new FileOutputStream(new File(filename));
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			if(null == fos) {
				return;
			}
			try {
				fos.write((l_text + "\n").getBytes("ASCII"));
				fos.flush();
			}
			catch(Exception ex) {
			}
		}
	}

	public void close() {
		if(null != fos) {
			try {
				fos.close();
			}
			catch(Exception ex) {
			}
		}
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
