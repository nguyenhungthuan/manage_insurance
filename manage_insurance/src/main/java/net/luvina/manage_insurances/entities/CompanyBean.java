/**
 * Copyright(C) 2016  Cty CP PM Luvina
 * CompanyBean.java, Nov 27, 2016
 */
package net.luvina.manageinsurances.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Class entity CompanyBean đại diện cho tbl_company trong DB
 * @author DELL
 *
 */
@Entity
@Table(name = "tbl_company")
public class CompanyBean {
	private int companyInternalId;
	private String companyName;
	private String address;
	private String email;
	private String tel;
	private List<UserBean> user = new ArrayList<UserBean>();
	
	/**
	 * Phương thức khởi tạo không tham số
	 */
	public CompanyBean(){};

	public CompanyBean(int companyInternalID) {
		this.companyInternalId = companyInternalID;
	}

	/**
	 * @param companyName
	 * @param address
	 * @param email
	 * @param tel
	 */
	public CompanyBean(String companyName, String address, String email, String tel) {
		this.companyName = companyName;
		this.address = address;
		this.email = email;
		this.tel = tel;
	}



	/**
	 * Phương thức khởi tạo có tham số
	 * @param companyInternalID
	 * @param companyName
	 */
	public CompanyBean(int companyInternalID, String companyName) {
		this.companyInternalId = companyInternalID;
		this.companyName = companyName;
	}
	
	

	/**
	 * Phương thức khởi tạo có tham số
	 * @param companyInternalId id
	 * @param companyName name
	 * @param address address
	 * @param email email
	 * @param tel telephone
	 */
	public CompanyBean(int companyInternalId, String companyName, String address, String email, String tel) {
		this.companyInternalId = companyInternalId;
		this.companyName = companyName;
		this.address = address;
		this.email = email;
		this.tel = tel;
	}


	/**
	 * @return the id
	 */
	@Id
	@Column(name = "company_internal_id", length = 10)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getCompanyInternalId() {
		return companyInternalId;
	}
	/**
	 * @param id the id to set
	 */
	public void setCompanyInternalId(int companyInternalID) {
		this.companyInternalId = companyInternalID;
	}
	/**
	 * @return the name
	 */
	@Column(name = "company_name", nullable = false, length = 50)
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param name the name to set
	 */
	public void setCompanyName(String name) {
		this.companyName = name;
	}
	/**
	 * @return the address
	 */
	@Column(name = "address", nullable = false, length = 100)
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the email
	 */
	@Column(name = "email", nullable = true, length = 50)
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
	 * @return the tel
	 */
	@Column(name = "telephone", nullable = true, length = 15)
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}


	/**
	 * @return the user
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public List<UserBean> getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(List<UserBean> user) {
		this.user = user;
	}

	

}
