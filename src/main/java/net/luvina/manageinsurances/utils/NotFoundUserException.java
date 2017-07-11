/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *NotFoundUserException.java, Aug 22, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.utils;

/**
 * Lớp định nghĩa exception không tồn tại user
 * @author LA-AM
 *
 */
public class NotFoundUserException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Phương thức khởi tạo Exception
	 * @param message thông báo
	 */
	public NotFoundUserException (String message) {
		super(message);
	}
}
