/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author kostas
 */
public class Constant {
	private	Line		line;
	private	String	name;
	private	long		num;
	
	public Constant(Line l_line, String l_name, long l_num) {
		line = l_line;
		name = l_name;
		num = l_num;
	}
	
	public Line get_line() {
		return line;
	}
	
	public String get_name() {
		return name;
	}
	
	public long get_num() {
		return num;
	}
}
