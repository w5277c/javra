/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAObjects;

import enums.EFunction;
import enums.EMsgType;
import enums.EOperator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import main.Constant;
import main.Line;
import main.Parser;
import main.ProgInfo;

/**
 *
 * @author kostas
 */
public class Expr {

	private static Long do_function(ProgInfo l_pi, Line l_line, Long tmp) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public static Long parse(ProgInfo l_pi, Line l_line, String l_expr) {
		Long result = null;
		int count = 0x00;
		String unary = "";
		boolean first_flag = true;
		Queue<Long> elemets = new LinkedBlockingQueue<>();
		Queue<EOperator> operators = new LinkedBlockingQueue<>();
		
		String _expr = new String(l_expr.trim());
		while(!_expr.isEmpty()) {
			if(first_flag && (_expr.startsWith("!") || _expr.startsWith("~") || _expr.startsWith("-"))) {
				unary = _expr.substring(0x00, 0x01);
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
				count++;
			}
			else {
				if(_expr.startsWith("0x")) {
					int pos = 0x02;
					//while(pos < _expr.length() && is_hex_digit(_expr.charAt(pos++)));
					while(is_hex_digit(_expr.charAt(pos))) {pos++; if(pos == _expr.length()) break;}
					if(0x02 != pos) {
						result = Long.parseLong(_expr.substring(0x02, pos), 0x10);
						_expr = _expr.substring(pos).trim();
					}
					else {
						l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_INVALID_SYNTAX);
						break;
					}
				}
				else if(_expr.startsWith("0b")) {
					int pos = 0x02;
					//while(pos < _expr.length() && is_bin_digit(_expr.charAt(pos++)));
					while(is_bin_digit(_expr.charAt(pos))) {pos++; if(pos == _expr.length()) break;}
					if(0x02 != pos) {
						result = Long.parseLong(_expr.substring(0x02, pos), 0x02);
						_expr = _expr.substring(pos).trim();
					}
					else {
						l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_INVALID_SYNTAX);
						break;
					}
				}
				else if(is_dec_digit(_expr.charAt(0x00))) {
					int pos = 0x00;
					while(is_dec_digit(_expr.charAt(pos))) {pos++; if(pos == _expr.length()) break;}
					if(0x00 != pos) {
						result = Long.parseLong(_expr.substring(0x00, pos));
						_expr = _expr.substring(pos).trim();
					}
					else {
						l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_INVALID_SYNTAX);
						break;
					}
				}
				else if(_expr.startsWith("$")) {
					int pos = 0x00;
					while(is_hex_digit(_expr.charAt(pos))) {pos++; if(pos == _expr.length()) break;}
					if(0x00 != pos) {
						result = Long.parseLong(_expr.substring(0x00, pos), 0x10);
						_expr = _expr.substring(pos).trim();
					}
					else {
						l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_INVALID_SYNTAX);
						break;
					}
				}
				else if(_expr.startsWith("(")) {
					int length = par_length(_expr, 0x01);
					if(-1 == length) {
						l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_MISSING, ")");
						break;
					}
					result = parse(l_pi, l_line, _expr.substring(0x01, 0x01 + length));
					if(null == result) {
						l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_INVALID_SYNTAX);
						break;
					}
					_expr = _expr.substring(length+0x02).trim();
				}
				else if(is_alpha(_expr.charAt(0x00)) || '_' == _expr.charAt(0x00)) {
					int pos = 0x00;
					while(is_label(_expr.charAt(pos))) {pos++; if(pos == _expr.length()) break;}
					String name = _expr.substring(0x00, pos).trim();
					_expr = _expr.substring(pos).trim();
					EFunction func = null;
					for(EFunction _func : EFunction.values()) {
						if(_func.equals(name)) {
							func = _func;
							break;
						}
					}
					if(null != func) {
						if(_expr.startsWith("(")) {
							int length = par_length(_expr, 0x01);
							if(-1 == length) {
								l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_MISSING, ")");
								break;
							}
							Long tmp = parse(l_pi, l_line, _expr.substring(0x01, length));
							if(null != tmp) {
								result = do_function(l_pi, l_line, tmp);
								if(null == result) {
									l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_INVALID_SYNTAX);
									break;
								}
								elemets.add(result);
							}
							else {
								l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_INVALID_SYNTAX);
								break;
							}
						}
						else {
							l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_MISSING, "(");
							break;
						}
					}
					else {
						Constant constant = l_pi.get_constants().get(name);
						if(null != constant) {
							result = constant.get_num();
						}
						else {
							l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_UNSUPPORTED);
						}
					}
				}
				else {
					l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_INVALID_SYNTAX);
					break;
				}
				if(null != result) {
					elemets.add(result);
					count++;
				}
			}
		}
		
		if(0x01 == count) {
			return elemets.poll();
		}
		if(0x03 <= count && 0x01 == (count%2)) {
			long _result = elemets.poll();
			while(!elemets.isEmpty()) {
				_result = calc(l_pi, l_line, _result, operators.poll(), elemets.poll());
			}
			return _result;
		}
		else {
			l_pi.print(EMsgType.MSG_ERROR, l_line, JAObject.MSG_INVALID_SYNTAX);
		}
		return null;
	}

	private static long calc(ProgInfo l_pi, Line l_line, long l_left, EOperator l_op, Long l_right) {
		switch(l_op) {
			case OP_MUL:
				return l_left * l_right;
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
		return '0' == l_c || '1' == l_c;
	}
	static boolean is_dec_digit(char l_c) {
		return '0' <= l_c && '9' >= l_c;
	}
	static boolean is_hex_digit(char l_c) {
		return ('0' <= l_c && '9' >= l_c) || ('a' <= l_c && 'f' >= l_c);
	}
	static boolean is_alpha(char l_c) {
		return 'a' <= l_c && 'z' >= l_c;
	}
	static boolean is_label(char l_c) {
		return is_alpha(l_c) | is_dec_digit(l_c) | '_' == l_c;
	}
}
