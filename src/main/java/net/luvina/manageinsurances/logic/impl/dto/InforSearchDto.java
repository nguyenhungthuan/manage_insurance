/**Copyright(C) 2017  [Cong ty CP phan mem Luvina]
 *InforSearchDto.java, Feb 20, 2017 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.logic.impl.dto;

/**
 * @author DELL
 *
 */
public class InforSearchDto {
	private String companyInternalID;
	private String fullName;
	private String insuranceNumber;
	private String placeOfRegister;
	private String sortType;
	
	public InforSearchDto(){}
	
	
	public InforSearchDto(String companyInternalID, String fullName, String insuranceNumber, String placeOfRegister,
			String sortType) {
		super();
		this.companyInternalID = companyInternalID;
		this.fullName = fullName;
		this.insuranceNumber = insuranceNumber;
		this.placeOfRegister = placeOfRegister;
		this.sortType = sortType;
	}


	/**
	 * @return the companyInternalID
	 */
	public String getCompanyInternalID() {
		return companyInternalID;
	}
	/**
	 * @param companyInternalID the companyInternalID to set
	 */
	public void setCompanyInternalID(String companyInternalID) {
		this.companyInternalID = companyInternalID;
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
	 * @return the sortType
	 */
	public String getSortType() {
		return sortType;
	}
	/**
	 * @param sortType the sortType to set
	 */
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
}
