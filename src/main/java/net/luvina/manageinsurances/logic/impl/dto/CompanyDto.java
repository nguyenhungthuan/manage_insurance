/**Copyright(C) 2017  [Cong ty CP phan mem Luvina]
 *CompanyDto.java, Feb 20, 2017 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.logic.impl.dto;

import java.util.Objects;

/**
 * @author DELL
 *
 */
public class CompanyDto {
	private int companyInternalId;
	private String companyName;
	private String address;
	private String email;
	private String tel;
	
	public CompanyDto(){}
	public CompanyDto(int companyInternalID, String companyName) {
		this.companyInternalId = companyInternalID;
		this.companyName = companyName;
	}
	/**
	 * @return the companyInternalId
	 */
	public int getCompanyInternalId() {
		return companyInternalId;
	}
	/**
	 * @param companyInternalId the companyInternalId to set
	 */
	public void setCompanyInternalId(int companyInternalId) {
		this.companyInternalId = companyInternalId;
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
	 * @return the address
	 */
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
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyDto companyDto = (CompanyDto) o;
        return companyInternalId == companyDto.companyInternalId &&
                Objects.equals(companyName, companyDto.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyInternalId, companyName);
    }
}
