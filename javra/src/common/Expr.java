/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
09.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package common;

import JAObjects.JAMacroBlock;
import JAObjects.JAObject;
import enums.EFunction;
import enums.EMsgType;
import enums.EOperator;
import java.util.HashSet;
import java.util.LinkedList;
import main.Constant;
import main.IncludeInfo;
import main.Label;
import main.Line;
import main.ProgInfo;

public class Expr {
	public static Long parse(ProgInfo l_pi, Line l_line, String l_expr) {
		Long result = 0l;
		int count = 0x00;
		char unary = 0x00;
		boolean first_flag = true;
		LinkedList<Long> elemets = new LinkedList<>();
		LinkedList<EOperator> operators = new LinkedList<>();
		
		String _expr = new String(l_expr.trim());
		while(!_expr.isEmpty()) {
			
			char c = _expr.charAt(0x00);
			if(first_flag && ('!' == c || '~' == c || '-' == c)) {
				unary = c;
				_expr = _expr.substring(0x01);
				first_flag = false;
			}
			else if ((count%2) == 0x01) {
				EOperator operator = null;
				for(EOperator _operator : EOperator.values()) {
					if(!_operator.get_text().isEmpty() && _expr.startsWith(_operator.get_text())) {
						operator = _operator;
						break;
					}
				}
				if(null == operator) {
					l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_ILLEGAL_OPERATOR, _expr.substring(0x00, 0x01));
					break;
				}
				_expr = _expr.substring(operator.get_text().length()).trim();
				operators.add(operator);
				first_flag = true;
				count++;
			}
			else {
				if('0' == c && _expr.length() > 0x01 && 'x' == _expr.charAt(0x01)) {
					int pos = 0x02;
					while(is_hex_digit(_expr.charAt(pos))) {pos++; if(pos == _expr.length()) break;}
					if(0x02 != pos) {
						result = Long.parseLong(_expr.substring(0x02, pos).replaceAll("_", ""), 0x10);
						_expr = _expr.substring(pos).trim();
					}
					else {
						l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_INVALID_SYNTAX);
						break;
					}
				}
				else if('0' == c && _expr.length() > 0x01 && 'b' == _expr.charAt(0x01)) {
					int pos = 0x02;
					while(is_bin_digit(_expr.charAt(pos))) {pos++; if(pos == _expr.length()) break;}
					if(0x02 != pos) {
						result = Long.parseLong(_expr.substring(0x02, pos).replaceAll("_", ""), 0x02);
						_expr = _expr.substring(pos).trim();
					}
					else {
						l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_INVALID_SYNTAX);
						break;
					}
				}
				else if('0' <= c && '9' >= c) {
					int pos = 0x00;
					while(is_dec_digit(_expr.charAt(pos))) {pos++; if(pos == _expr.length()) break;}
					if(0x00 != pos) {
						result = Long.parseLong(_expr.substring(0x00, pos).replaceAll("_", ""));
						_expr = _expr.substring(pos).trim();
					}
					else {
						l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_INVALID_SYNTAX);
						break;
					}
				}
				else if('$' == c) {
					int pos = 0x00;
					while(is_hex_digit(_expr.charAt(pos))) {pos++; if(pos == _expr.length()) break;}
					if(0x00 != pos) {
						result = Long.parseLong(_expr.substring(0x00, pos).replaceAll("_", ""), 0x10);
						_expr = _expr.substring(pos).trim();
					}
					else {
						l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_INVALID_SYNTAX);
						break;
					}
				}
				else if(0x02 <=_expr.length() && '0' == c &&  '0' <= _expr.charAt(0x01) && '7' >= _expr.charAt(0x01)) {
					int pos = 0x00;
					while(is_oct_digit(_expr.charAt(pos))) {pos++; if(pos == _expr.length()) break;}
					if(0x00 != pos) {
						result = Long.parseLong(_expr.substring(0x00, pos).replaceAll("_", ""), 0x08);
						_expr = _expr.substring(pos).trim();
					}
					else {
						l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_INVALID_SYNTAX);
						break;
					}
					
				}
				else if('\'' == c) {
					if(0x02 < _expr.length() && '\\' != _expr.charAt(0x01) && '\'' != _expr.charAt(0x01) && '\'' == _expr.charAt(0x02)) {
						result = (long)_expr.charAt(0x01);
						_expr = _expr.substring(0x03).trim();
					}
					else if(0x03 < _expr.length() && '\\' == _expr.charAt(0x01) && '\'' == _expr.charAt(0x02) && '\'' == _expr.charAt(0x03)) {
						result = (long)'\'';
						_expr = _expr.substring(0x04).trim();
					}
					else {
						l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_INVALID_SYNTAX);
						break;
					}
				}
				else if('(' == c) {
					int length = par_length(_expr, 0x01);
					if(-1 == length) {
						l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_MISSING, ")");
						break;
					}
					result = parse(l_pi, l_line, _expr.substring(0x01, 0x01 + length));
					if(null == result) {
						return null;
					}
					_expr = _expr.substring(length+0x02).trim();
				}
				else if(is_alpha(c) || '_' == c) {
					int pos = 0x00;
					while(is_label(_expr.charAt(pos))) {pos++; if(pos == _expr.length()) break;}
					String name = _expr.substring(0x00, pos).trim();
					_expr = _expr.substring(pos).trim();
					EFunction func = null;
					for(EFunction _func : EFunction.values()) {
						if(_func.get_text().equals(name)) {
							func = _func;
							break;
						}
					}
					if(null != func) {
						if('(' == _expr.charAt(0x00)) {
							int length = par_length(_expr, 0x01);
							if(-1 == length) {
								l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_MISSING, ")");
								break;
							}
							Long tmp = parse(l_pi, l_line, _expr.substring(0x01, 0x01+length));
							if(null != tmp) {
								result = do_function(l_pi, l_line, func, tmp);
								_expr = _expr.substring(0x02+length).trim();
								if(null == result) {
									l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_INVALID_SYNTAX);
									break;
								}
							}
							else {
								return null;
							}
						}
						else {
							l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_MISSING, "(");
							break;
						}
					}
					else {
						Constant constant = l_pi.get_constant(name);
						if(null != constant) {
							JAMacroBlock macroblock = l_pi.get_macroblock();
							if(l_pi.get_analyze() && (null != macroblock || null != l_pi.get_ii())) {
								for(IncludeInfo _ii : (null == macroblock ? l_pi.get_ii().get_iis() : macroblock.get_macro().get_ii().get_iis())) {
									if(!_ii.is_used() && _ii.get_resources().contains(constant.get_name())) {
										_ii.set_used();
										break;
									}
								}
							}
							result = constant.get_value();
						}
						else {
							Label label = l_pi.get_label(name);
							if(null != label) {
								JAMacroBlock macroblock = l_pi.get_macroblock();
								if(l_pi.get_analyze() && (null != macroblock || null != l_pi.get_ii())) {
									for(IncludeInfo _ii : (null == macroblock ? l_pi.get_ii().get_iis() : macroblock.get_macro().get_ii().get_iis())) {
										if(!_ii.is_used() && _ii.get_resources().contains(label.get_name())) {
											_ii.set_used();
											break;
										}
									}
								}
								result = (long)label.get_addr();
							}
							else {
								//UNKNOWN CONSTANT HERE!
								l_line.set_failpart(name);
								return null;
							}
						}
					}
				}
				else {
					l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_INVALID_SYNTAX);
					break;
				}
				if(null != result) {
					switch(unary) {
						case '-':
							result = -result;
							break;
						case '!':
							result = (0x00 == result ? 0x01l : 0x00l);
							break;
						case '~':
							result = ~result;
							break;
					}
					elemets.add(result);
				}
				count++;
				first_flag = false;
			}
		}
		
		if(0x01 == count) {
			return elemets.poll();
		}
		if(0x03 <= count && 0x01 == (count%2)) {
			for(int priority = EOperator.OP_MUL.get_priority(); priority >= EOperator.OP_LOGICAL_OR.get_priority() && !operators.isEmpty(); priority--) {
				int index = 0x00;
				while(operators.size() > index) {
					EOperator op = operators.get(index);
					if(op.get_priority() == priority) {
						try {
							Long _result = calc(l_pi, l_line, elemets.get(index), op, elemets.get(index+0x01));
							operators.remove(index);
							elemets.set(index, _result);
							elemets.remove(index+0x01);
						}
						catch(Exception ex) {
							ex.printStackTrace();
						}
					}
					else {
						index++;
					}
				}
			}
			return elemets.get(0x00);
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_INVALID_SYNTAX);
		}
		return 0l;
	}

	private static long calc(ProgInfo l_pi, Line l_line, long l_left, EOperator l_op, Long l_right) {
		switch(l_op) {
			case OP_MUL:
				return l_left*l_right;
			case OP_DIV:
				if(0 == l_right) {
					l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_DIVISION_BY_ZERO);
					return 0;
				}
				return l_left/l_right;
			case OP_MOD:
				if(0 == l_right) {
					l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_DIVISION_BY_ZERO, "(modulus operator)");
					return 0;
				}
				return l_left%l_right;
			case OP_ADD:
				return l_left+l_right;
			case OP_SUB:
				return l_left-l_right;
			case OP_SH_LEFT:
				return l_left<<l_right;
			case OP_SH_RIGHT:
				return l_left>>l_right;	//TODO проверить на знак
			case OP_LESS_THAN:
				return (l_left<l_right ? 1l : 0l);
			case OP_LESS_OR_EQUAL:
				return (l_left<=l_right ? 1l : 0l);
			case OP_GREATER_THAN:
				return (l_left>l_right ? 1l : 0l);
			case OP_GREATER_OR_EQUAL:
				return (l_left>=l_right ? 1l : 0l);
			case OP_EQUAL:
			case OP_EQUALX2:
				return (l_left==l_right ? 1l : 0l);
			case OP_NOT_EQUAL:
				return (l_left!=l_right ? 1l : 0l);
			case OP_BITWISE_AND:
				return (l_left & l_right);
			case OP_BITWISE_XOR:
				return (l_left ^ l_right);
			case OP_BITWISE_OR:
				return (l_left | l_right);
			case OP_LOGICAL_AND:
				return (0x00 != l_left && 0x00 != l_right) ? 1l : 0l;
			case OP_LOGICAL_OR:
				return (0x00 != l_left || 0x00 != l_right) ? 1l : 0l;
			default:
				l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_UNSUPPORTED);
				return 0;
		}
	}

	private static Long do_function(ProgInfo l_pi, Line l_line, EFunction l_func, Long l_value) {
		switch(l_func) {
			case FUNC_LOW:
			case FUNC_BYTE1:
				return l_value & 0xff;
			case FUNC_HIGH:
			case FUNC_BYTE2:
				return (l_value >> 0x08) & 0xff;
			case FUNC_BYTE3:
				return (l_value >> 0x10) & 0xff;
			case FUNC_BYTE4:
				return (l_value >> 0x18) & 0xff;
			case FUNC_LWRD:
				return l_value & 0xffff;
			case FUNC_HWRD:
				return (l_value >> 0x10) & 0xffff;
			case FUNC_PAGE:
				return (l_value >> 0x10) & 0xff;
			case FUNC_EXP2:
				return (0x01l << l_value);
			case FUNC_LOG2:
				long i = 0;
				while (0x01 <= l_value) {
					l_value = l_value >> 0x01;
					i++;
				}
				return i;
			default:
				l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_UNSUPPORTED);
		}
		return 0l;
	}

	private static int par_length(String l_str, int l_pos) {
		int b_count = 0x01;

		for (int pos = l_pos; pos < l_str.length(); pos++) {
			char c = l_str.charAt(pos);
			if (')' == c) {
				b_count--;
				if (0x00 == b_count) {
					return (pos - l_pos);
				}
			}
			else if ('(' == c) {
				b_count++;
			}
		}
		return -1;
	}

	
	static boolean is_bin_digit(char l_c) {
		return '0' == l_c || '1' == l_c || '_' == l_c;
	}
	static boolean is_oct_digit(char l_c) {
		return ('0' <= l_c && '7' >= l_c) || '_' == l_c;
	}
	static boolean is_dec_digit(char l_c) {
		return ('0' <= l_c && '9' >= l_c) || '_' == l_c;
	}
	static boolean is_hex_digit(char l_c) {
		return ('0' <= l_c && '9' >= l_c) || ('a' <= l_c && 'f' >= l_c) || '_' == l_c;
	}
	static boolean is_alpha(char l_c) {
		return 'a' <= l_c && 'z' >= l_c;
	}
	static boolean is_label(char l_c) {
		return is_alpha(l_c) || is_dec_digit(l_c);
	}
}
