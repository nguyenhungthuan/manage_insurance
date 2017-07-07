/**Copyright(C) 2017  [Cong ty CP phan mem Luvina]
 *UserInsuranceBean.java, Feb 20, 2017 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.entities;

import net.luvina.manageinsurances.utils.Common;

/**
 * @author DELL
 *
 */
public class UserInsuranceBean {

	private int userInternalID;
	
	private String insuranceNumber;
	
	private String fullName;
	
	private String sex;
	
	private String birthday;
	
	private String companyName;	

	private String companyAddress;
	
	private String email;
	
	private String telephone;
	
	private String placeOfRegister;
	
	private String insuranceStartDate;

	private String insuranceEndDate;
	
	private int companyInternalID;
	private int insuranceInternalID;
	
	public UserInsuranceBean(){};

	public UserInsuranceBean(int userInternalID,String fullName){
		this.userInternalID = userInternalID;
		this.fullName = fullName;
	};

	/**
	 * Phương thức khởi tạo có tham số
	 * @param userInternalID user id 
	 * @param companyInternalID id nội bộ công ty
	 * @param companyName tên công ty
	 * @param fullName tên đầy đủ
	 * @param sex giới tính
	 * @param birthday ngày sinh
	 * @param insuranceNumber mã số tbh
	 * @param insuranceStartDate ngày bắt đầu
	 * @param insuranceEndDate ngày hết hạn
	 * @param placeOfRegister địa chỉ đăng ký KCB
	 */
	public UserInsuranceBean(int userInternalID, int companyInternalId, String companyName, String fullName, String sex,
			String birthday, String insuranceNumber, String insuranceStartDate, String insuranceEndDate,
			String placeOfRegister) {
		this.userInternalID = userInternalID;
		this.companyInternalID = companyInternalId;
		this.companyName = companyName;
		this.fullName = fullName;
		this.sex = Common.sexByString(Integer.parseInt(sex));
		this.birthday = Common.formatDate(birthday);
		this.insuranceNumber = insuranceNumber;
		this.insuranceStartDate = Common.formatDate(insuranceStartDate);
		this.insuranceEndDate = Common.formatDate(insuranceEndDate);
		this.placeOfRegister = placeOfRegister;
	}
	
	/**
	 * @param insuranceNumber
	 * @param fullName
	 * @param sex
	 * @param birthday
	 * @param placeOfRegister
	 * @param insuranceStartDate
	 * @param insuranceEndDate
	 */
	public UserInsuranceBean(String fullName, String sex, String birthday, String insuranceNumber, 
			String insuranceStartDate, String insuranceEndDate, String placeOfRegister) {
		this.fullName = fullName;
		this.sex = Common.sexByString(Integer.parseInt(sex));
		this.birthday = Common.formatDate(birthday);
		this.insuranceNumber = insuranceNumber;
		this.placeOfRegister = placeOfRegister;
		this.insuranceStartDate = Common.formatDate(insuranceStartDate);
		this.insuranceEndDate = Common.formatDate(insuranceEndDate);
	}
	
	/**
	 * Phương thức khởi tạo có tham số
	 * @param userInternalID user id
	 * @param companyInternalID id nội bộ công ty
	 * @param sex giới tính
	 * @param birthday ngày sinh
	 * @param insuranceNumber mã số tbh
	 * @param insuranceStartDate ngày bắt đầu
	 * @param insuranceEndDate ngày hết hạn
	 * @param placeOfRegister địa chỉ đăng ký KCB
	 */
	public UserInsuranceBean(int userInternalID, int companyInternalId, int insuranceInternalID, String fullName, String sex,
			String birthday, String insuranceNumber, String insuranceStartDate, String insuranceEndDate,
			String placeOfRegister) {
		this.userInternalID = userInternalID;
		this.companyInternalID = companyInternalId;
		this.insuranceInternalID = insuranceInternalID;
		this.fullName = fullName;
		this.sex = sex;
		this.birthday = Common.formatDate(birthday);
		this.insuranceNumber = insuranceNumber;
		this.insuranceStartDate = Common.formatDate(insuranceStartDate);
		this.insuranceEndDate = Common.formatDate(insuranceEndDate);
		this.placeOfRegister = placeOfRegister;
	}	
	/**
	 * @return the userInternalID
	 */
	public int getUserInternalID() {
		return userInternalID;
	}
	/**
	 * @param userInternalID the userInternalID to set
	 */
	public void setUserInternalID(int userInternalID) {
		this.userInternalID = userInternalID;
	}
	/**
	 * @return the insuranceNumber
	 */
	public String getInsuranceNumber() {
		return insuranceNumber;
	}
	/**
	 * @param insuranceNumber the insuranceNumber to set
	 */
	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the companyAddress
	 */
	public String getCompanyAddress() {
		return companyAddress;
	}
	/**
	 * @param companyAddress the companyAddress to set
	 */
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return the placeOfRegister
	 */
	public String getPlaceOfRegister() {
		return placeOfRegister;
	}
	/**
	 * @param placeOfRegister the placeOfRegister to set
	 */
	public void setPlaceOfRegister(String placeOfRegister) {
		this.placeOfRegister = placeOfRegister;
	}
	/**
	 * @return the insuranceStartDate
	 */
	public String getInsuranceStartDate() {
		return insuranceStartDate;
	}
	/**
	 * @param insuranceStartDate the insuranceStartDate to set
	 */
	public void setInsuranceStartDate(String insuranceStartDate) {
		this.insuranceStartDate = insuranceStartDate;
	}
	/**
	 * @return the insuranceEndDate
	 */
	public String getInsuranceEndDate() {
		return insuranceEndDate;
	}
	/**
	 * @param insuranceEndDate the insuranceEndDate to set
	 */
	public void setInsuranceEndDate(String insuranceEndDate) {
		this.insuranceEndDate = insuranceEndDate;
	}
	/**
	 * @return the companyInternalID
	 */
	public int getCompanyInternalID() {
		return companyInternalID;
	}
	/**
	 * @param companyInternalID the companyInternalID to set
	 */
	public void setCompanyInternalID(int companyInternalID) {
		this.companyInternalID = companyInternalID;
	}
	/**
	 * @return the insuranceInternalID
	 */
	public int getInsuranceInternalID() {
		return insuranceInternalID;
	}
	/**
	 * @param insuranceInternalID the insuranceInternalID to set
	 */
	public void setInsuranceInternalID(int insuranceInternalID) {
		this.insuranceInternalID = insuranceInternalID;
	}	
	
}
