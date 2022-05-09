/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

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
