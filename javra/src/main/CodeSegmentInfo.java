/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
14.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

import enums.ESegmentType;

public class CodeSegmentInfo extends SegmentInfo {
	
	public CodeSegmentInfo() {
		super(ESegmentType.CODE);
	}

	@Override
	public CodeBlock set_block(int l_addr) {
		CodeBlock block = (CodeBlock)blocks.get(l_addr);
		if(null == block) {
			cur_block = new CodeBlock(l_addr); 
			blocks.put(l_addr, cur_block);
			return null;
		}
		else {
			cur_block = block;
			return block;
		}
	}
	
	@Override
	public CodeBlock get_cur_block() {
		return (CodeBlock)super.get_cur_block();
	}
}
