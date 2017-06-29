/**
 * Copyright(C) 2016  Cty CP PM Luvina
 * UserBean.java, Nov 27, 2016
 */
package net.luvina.manageinsurances.entities;

import javax.persistence.*;

/**
 * Class entity UserBean đại diện cho tbl_user trong DB
 * @author DELL
 *
 */
@Entity
@Table(name = "tbl_user", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
public class UserBean {
	private int userInternalID;
	private int insuranceInternalId;
	private String userName;
	private String password;
	private String fullName;
	private String sex;
	private String birthday;
	private CompanyBean company;
	private InsuranceBean insurance;
	
	public UserBean(){}
	
	/**
	 * @param userInternalID
	 * @param companyInternalID
	 * @param insuranceInternalID
	 * @param userName
	 * @param password
	 * @param fullName
	 * @param sex
	 * @param birthday
	 */
	public UserBean(int userInternalID, int insuranceInternalID, String userName, String password,
			String fullName, String sex, String birthday) {
		this.userInternalID = userInternalID;
		this.insuranceInternalId = insuranceInternalID;
		this.userName = userName;
		this.password = password;
		this.fullName = fullName;
		this.sex = sex;
		this.birthday = birthday;
	}
	
		
	/**
	 * @param insuranceInternalId
	 * @param fullName
	 * @param sex
	 * @param birthday
	 * @param company
	 * @param insurance
	 */
	public UserBean(int userInternalID, String fullName, String sex, String birthday, CompanyBean company,
			InsuranceBean insurance) {
		this.userInternalID = userInternalID;
		this.fullName = fullName;
		this.sex = sex;
		this.birthday = birthday;
		this.company = company;
		this.insurance = insurance;
	}

	/**
	 * @param userName
	 * @param password
	 */
	public UserBean(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	/**
	 * @return the userInternalID
	 */
	@Id
	@Column(name = "user_internal_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	 * @return the insuranceInternalID
	 */
	@Column(name = "insurance_internal_id", length = 10, nullable = false)
	public int getInsuranceInternalId() {
		return insuranceInternalId;
	}
	/**
	 * @param insuranceInternalID the insuranceInternalID to set
	 */
	public void setInsuranceInternalId(int insuranceInternalID) {
		this.insuranceInternalId = insuranceInternalID;
	}
	/**
	 * @return the userName
	 */
	@Column(name = "username", length = 15, nullable = false)
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
	@Column(name = "password", length = 32, nullable = false)
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the fullName
	 */
	@Column(name = "user_full_name", length = 50, nullable = false)
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
	@Column(name = "user_sex_division", length = 2, nullable = false)
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
	@Column(name = "birthdate", nullable = true)
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
	 * @return the company
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_internal_id", nullable = false)
	public CompanyBean getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(CompanyBean company) {
		this.company = company;
	}

	/**
	 * @return the insurance
	 */
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
	public InsuranceBean getInsurance() {
		return insurance;
	}

	/**
	 * @param insurance the insurance to set
	 */
	public void setInsurance(InsuranceBean insurance) {
		this.insurance = insurance;
	}
	

}
