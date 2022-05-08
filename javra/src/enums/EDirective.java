/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
07.03.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package enums;

import java.util.HashMap;
import java.util.Map;

public enum EDirective {
   BYTE(0),
	CSEG(1),
	CSEGSIZE(2),
	DB(3),
	DEF(4),
	DEVICE(5),
	DSEG(6),
	DW(7),
	ENDM(8),
	ENDMACRO(9),
	EQU(10),
	ESEG(11),
	EXIT(12),
	INCLUDE(13),
	INCLUDEPATH(14),
	LIST(15),
	LISTMAC(16),
	MACRO(17),
	NOLIST(18),
	ORG(19),
	SET(20),
	DEFINE(21),
	UNDEF(22),
	IFDEF(23),
	IFNDEF(24),
	IF(25),
	ELSE(26),
	ELSEIF(27),
	ELIF(28),
	ENDIF(29),
	MESAGE(30),
	WARNING(31),
	ERROR(32),
	PRAGMA(33);

   private static final Map<Integer, EDirective> ids = new HashMap<>();
   static {
      for (EDirective type : EDirective.values()) {
         ids.put(type.get_id(), type);
      }
   }

	private int id;
   
   private EDirective(int l_id) {
      id = l_id;
   }
   
   public int get_id() {
      return id;
   }
   
   public static EDirective fromInt(int l_id) {
      return ids.get(l_id);
   }
}
