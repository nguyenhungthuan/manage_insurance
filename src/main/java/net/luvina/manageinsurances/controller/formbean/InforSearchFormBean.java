package net.luvina.manageinsurances.controller.formbean;

/**
 * Class entity InforSearchFormBean làm modelAttribute cho MH02
 * @author DELL
 *
 */
public class InforSearchFormBean {
	private String companyInternalID = "1";
	private String fullName = "";
	private String insuranceNumber = "";
	private String placeOfRegister = "";
	private String sortType = "ASC";
	private String currentPage = "1";	
	
	public InforSearchFormBean(){}
	/**
	 * @param companyInternalID
	 */
	public InforSearchFormBean(String companyInternalID) {
		this.companyInternalID = companyInternalID;
	}
	/**
	 * @param sortType
	 * @param currentPage
	 */
	public InforSearchFormBean(String sortType, String currentPage) {
		this.sortType = sortType;
		this.currentPage = currentPage;
	}

	/**
	 * @return the companyID
	 */
	public String getCompanyInternalID() {
		return companyInternalID;
	}
	/**
	 * @param companyID the companyID to set
	 */
	public void setCompanyInternalID(String companyID) {
		this.companyInternalID = companyID;
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
	/**
	 * @return the currentPage
	 */
	public String getCurrentPage() {
		return currentPage;
	}
	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	
	
	
	
}
