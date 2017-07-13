/**Copyright(C) 2017  [Cong ty CP phan mem Luvina]
 *VNCharacterUtils.java, Feb 19, 2017 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.utils;

import java.text.Normalizer;

/**
 * Phương thức thay đổi ký tự tiếng việt có dấu thành không dấu
 * @author DELL
 *
 */
public class VNCharacterUtils {
    /**
     * Bỏ dấu 1 chuỗi ký tự
     * 
     * @param s
     * @return chuỗi đã bỏ dấu
     */
    public static String removeAccent(String s) {
    	s = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    	return replaceSpecialChar(s);
    }
     
    /**
     * Xóa các khoảng trắng thừa ở giữa và các ký tự không trong bảng chữ cái
     * @param s name
     * @return name
     */
    public static String replaceSpecialChar(String s) {
        return s.replaceAll("[^a-zA-Z\\s]", "").replaceAll("\\s+", " ");
    }
}