/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *RegisterFormValidator.java, Dec 24, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.validates;

import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import net.luvina.manageinsurances.controller.formbean.UserInsuranceFormBean;
import net.luvina.manageinsurances.logic.CompanyLogic;
import net.luvina.manageinsurances.logic.UserLogic;
import net.luvina.manageinsurances.utils.Common;
import net.luvina.manageinsurances.utils.ConfigProperties;

/**
 * Class validate thông tin nhập vào từ MH04
 * @author DELL
 *
 */
@Component
public class RegisterFormValidator {
	
	@Autowired
	private UserLogic userLogic;
	@Autowired
	private CompanyLogic companyLogic;

	/**
	 * Phương thức validate thông tin người dùng nhập vào form đăng ký
	 * @param userInsuranceFormBean UserInsuranceFormBean
	 * @param errors Errors
	 */
	public void validate(UserInsuranceFormBean userInsuranceFormBean, Errors errors) {
		// validate ngày sinh
		if(!errors.hasFieldErrors("birthday")) {
			String birthday = userInsuranceFormBean.getBirthday();
			if(Common.checkExistDate(birthday) == 0) {
				errors.rejectValue("birthday", "NotExist.userInsurance.birthday");
			} else if (Common.checkExistDate(birthday) == 2) {
				errors.rejectValue("birthday", "Invalid.userInsurance.birthday");
			}
		}
		
		// validate ngày bắt đầu thẻ BH
		if(!errors.hasFieldErrors("insuranceStartDate")) {
			String insuranceStartDate = userInsuranceFormBean.getInsuranceStartDate();
			if(Common.checkExistDate(insuranceStartDate) == 0) {
				errors.rejectValue("insuranceStartDate", "NotExist.userInsurance.insuranceStartDate");
			} else if (Common.checkExistDate(insuranceStartDate) == 2) {
				errors.rejectValue("insuranceStartDate", "Invalid.userInsurance.insuranceStartDate");		
			}
		}
			
		// validate ngày kết thúc thẻ BH
		if(!errors.hasFieldErrors("insuranceEndDate")) {
			String insuranceEndDate = userInsuranceFormBean.getInsuranceEndDate();
			if(Common.checkExistDate(insuranceEndDate) == 0) {
				errors.rejectValue("insuranceEndDate", "NotExist.userInsurance.insuranceEndDate");
			} else if (Common.checkExistDate(insuranceEndDate) == 2) {
				errors.rejectValue("insuranceEndDate", "Invalid.userInsurance.insuranceEndDate");	
			}
		}		
		
		// Validate thông tin công ty nhập
		if(!companyLogic.checkExistedCom(userInsuranceFormBean.getCompanyInternalID())) {
			// Validate tên công ty
			if(userInsuranceFormBean.getCompanyName().equals("")){
				errors.rejectValue("companyName", "NotEmpty.userInsurance.companyName");
			}
			// validate địa chỉ công ty
			if (userInsuranceFormBean.getCompanyAddress().equals("")) {
				errors.rejectValue("companyAddress", "NotEmpty.userInsurance.companyAddress");
			}
			// validate email
			if(!Pattern.matches(ConfigProperties.getMessage("emailPattern"), userInsuranceFormBean.getEmail())) {
				errors.rejectValue("email", "Email.userInsurance.email");
			} else if(companyLogic.checkExistedEmail(userInsuranceFormBean.getEmail())) {
				errors.rejectValue("email", "Existed.userInsurance.email");
			}
			// validate số điện thoại
			if(!Pattern.matches(ConfigProperties.getMessage("telPattern"), userInsuranceFormBean.getTelephone())) {
				errors.rejectValue("telephone", "Pattern.userInsurance.telephone");
			} else if(companyLogic.checkExistedTel(userInsuranceFormBean.getTelephone())) {
				errors.rejectValue("telephone", "Existed.userInsurance.telephone");
			}
		}
		// Validate tồn tại mã số thẻ bảo hiểm
		if (!errors.hasFieldErrors("insuranceNumber")) {
			if(userLogic.checkExistedInsuNum(userInsuranceFormBean.getInsuranceNumber(), userInsuranceFormBean.getUserInternalID())) {
				errors.rejectValue("insuranceNumber", "UserInsurance.validate.ExistedNumber");
			}
		}
		
		// Validate so sánh ngày bắt đầu thẻ bảo hiểm và ngày kết thúc
		if(!errors.hasFieldErrors("insuranceStartDate") && !errors.hasFieldErrors("insuranceEndDate")) {
			Boolean rsCompare = Common.compareDate(userInsuranceFormBean.getInsuranceStartDate(), userInsuranceFormBean.getInsuranceEndDate());
			if(!rsCompare) {
				errors.rejectValue("insuranceStartDate", "UserInsurance.validate.InvalidDate");
			}
		}		
	}
}
