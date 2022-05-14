/*--------------------------------------------------------------------------------------------------------------------------------------------------------------
Файл распространяется под лицензией GPL-3.0-or-later, https://www.gnu.org/licenses/gpl-3.0.txt
----------------------------------------------------------------------------------------------------------------------------------------------------------------
14.05.2022	konstantin@5277.ru			Начало
--------------------------------------------------------------------------------------------------------------------------------------------------------------*/
package main;

public class Utils {
   public static String printHexBinary(byte[] l_bytes) {
      if(null == l_bytes || 0 == l_bytes.length) {
         return "";
      }

      StringBuilder result = new StringBuilder();
      for(int pos = 0; pos < l_bytes.length; pos++) {
         String num = Integer.toHexString(l_bytes[pos] & 0xff).toLowerCase();
         if(num.length() < 0x02) {
            result.append("0");
         }
         result.append(num);
      }
      return result.toString();
   }
}
