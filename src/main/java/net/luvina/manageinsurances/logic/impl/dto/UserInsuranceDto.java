/**Copyright(C) 2017  [Cong ty CP phan mem Luvina]
 *UserInsuranceDto.java, Feb 20, 2017 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.logic.impl.dto;

import lombok.EqualsAndHashCode;

/**
 * @author DELL
 *
 */
@EqualsAndHashCode
public class UserInsuranceDto {
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
	
	public UserInsuranceDto(){}
	public UserInsuranceDto(int userInternalID, String fullName) {
		this.userInternalID = userInternalID;
		this.fullName = fullName;
	}
	
	
	public UserInsuranceDto(int userInternalID,int companyInternalID, String fullName, String sex, String birthday, String insuranceNumber,
			String insuranceStartDate, String insuranceEndDate, String placeOfRegister) {
		this.userInternalID = userInternalID;
		this.insuranceNumber = insuranceNumber;
		this.fullName = fullName;
		this.sex = sex;
		this.birthday = birthday;
		this.placeOfRegister = placeOfRegister;
		this.insuranceStartDate = insuranceStartDate;
		this.insuranceEndDate = insuranceEndDate;
		this.companyInternalID = companyInternalID;
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
}