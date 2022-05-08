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
public class ORGInfo {
	private	long		start;
	private	long		length				= 0x0000;
	private	boolean	segment_overlap	= false;
	
	public ORGInfo(long l_start) {
		start = l_start;
	}

	public ORGInfo(long l_strat, long l_length, boolean l_segment_overlap) {
		start = l_strat;
		length = l_length;
		segment_overlap = l_segment_overlap;
	}
}
