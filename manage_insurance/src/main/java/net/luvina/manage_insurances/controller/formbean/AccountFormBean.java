/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *AccountFormBean.java, Dec 22, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.controller.formbean;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Class entity AccountFormBean
 * @author DELL
 *
 */
public class AccountFormBean {
	@NotEmpty
	private String userName;
	@NotEmpty
	private String password;
	
	public AccountFormBean(){}
	
	/**
	 * Constructor có tham số
	 * @param userName user nam
	 * @param password password
	 */
	public AccountFormBean(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
