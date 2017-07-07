/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *InsuranceBean.java, Dec 6, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

/**
 * Class entity InsuranceBean đại diện cho tbl_insurance trong DB
 * @author DELL
 *
 */
@Entity
@Table(name = "tbl_insurance", uniqueConstraints = { @UniqueConstraint(columnNames = { "insurance_number" }) })
public class InsuranceBean {
	private int insuranceInternalId;
	private String insuranceNumber;
	private String insuranceStartDate;
	private String insuranceEndDate;
	private String placeOfRegister;
	private UserBean user;
	
	
	public InsuranceBean(){}
	
	/**
	 * @param insuranceInternalId
	 * @param insuranceNumber
	 * @param insuranceStartDate
	 * @param insuranceEndDate
	 * @param placeOfRegister
	 */
	public InsuranceBean(int insuranceInternalId, String insuranceNumber, String insuranceStartDate,
			String insuranceEndDate, String placeOfRegister) {
		this.insuranceInternalId = insuranceInternalId;
		this.insuranceNumber = insuranceNumber;
		this.insuranceStartDate = insuranceStartDate;
		this.insuranceEndDate = insuranceEndDate;
		this.placeOfRegister = placeOfRegister;
	}

	/**
	 * @return the insuranceInternalId
	 */
	@Id
	@Column(name = "insurance_internal_id", length = 10)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getInsuranceInternalId() {
		return insuranceInternalId;
	}
	/**
	 * @param insuranceInternalId the insuranceInternalId to set
	 */
	public void setInsuranceInternalId(int insuranceInternalId) {
		this.insuranceInternalId = insuranceInternalId;
	}
	/**
	 * @return the insuranceNumber
	 */
	@Column(name = "insurance_number", length = 10, nullable = false)
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
	 * @return the placeOfRegister
	 */
	@Column(name = "place_of_register", nullable = false, length = 50)
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
	@Column(name = "insurance_start_date", nullable = false)
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
	@Column(name = "insurance_end_date", nullable = false)
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
	 * @return the user
	 */
	@OneToOne
	@PrimaryKeyJoinColumn
	public UserBean getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(UserBean user) {
		this.user = user;
	}
	
	
}
