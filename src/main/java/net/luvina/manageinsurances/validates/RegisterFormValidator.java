/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *RegisterFormValidator.java, Dec 24, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.validates;

import java.time.LocalDate;
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
	public void validate(UserInsuranceFormBean userInsurance, Errors errors) {
		// validate tên
		if(!errors.hasFieldErrors("fullName")) {
			if(userInsurance.getFullName().length() > 50) {
				errors.rejectValue("fullName","Length.userInsurance.fullName");
			}
		}
		
		// validate ngày sinh
		if(!userInsurance.getBirthday().isEmpty()) {
			String birthday = userInsurance.getBirthday();
			if (!Common.checkExistDate(birthday)) {
				errors.rejectValue("birthday", "Invalid.userInsurance.birthday");
			} else if (Common.compareDate(Common.convertFormatDate(LocalDate.now().toString()), birthday)) {
				errors.rejectValue("birthday", "Later.userInsurance.birthday");
			}
		}
		
		// validate ngày bắt đầu thẻ BH
		if(!errors.hasFieldErrors("insuranceStartDate")) {
			String insuranceStartDate = userInsurance.getInsuranceStartDate();
			if (!Common.checkExistDate(insuranceStartDate)) {
				errors.rejectValue("insuranceStartDate", "Invalid.userInsurance.insuranceStartDate");		
			}
		}
			
		// validate ngày kết thúc thẻ BH
		if(!errors.hasFieldErrors("insuranceEndDate")) {
			String insuranceEndDate = userInsurance.getInsuranceEndDate();
			if (!Common.checkExistDate(insuranceEndDate)) {
				errors.rejectValue("insuranceEndDate", "Invalid.userInsurance.insuranceEndDate");	
			}
		}		
		
		// Validate thông tin công ty nhập
		if(!companyLogic.checkExistCompany(userInsurance.getCompanyInternalID())) {
			// Validate tên công ty
			if(userInsurance.getCompanyName().equals("")){
				errors.rejectValue("companyName", "NotEmpty.userInsurance.companyName");
			}
			// validate địa chỉ công ty
			if (userInsurance.getCompanyAddress().equals("")) {
				errors.rejectValue("companyAddress", "NotEmpty.userInsurance.companyAddress");
			}
			// validate email
			if(!Pattern.matches(ConfigProperties.getMessage("emailPattern"), userInsurance.getEmail())) {
				errors.rejectValue("email", "Email.userInsurance.email");
			} else if(companyLogic.checkExistEmail(userInsurance.getEmail())) {
				errors.rejectValue("email", "Existed.userInsurance.email");
			}
			// validate số điện thoại
			if(!Pattern.matches(ConfigProperties.getMessage("telPattern"), userInsurance.getTelephone())) {
				errors.rejectValue("telephone", "Pattern.userInsurance.telephone");
			} else if(companyLogic.checkExistTel(userInsurance.getTelephone())) {
				errors.rejectValue("telephone", "Existed.userInsurance.telephone");
			}
		}
		// Validate tồn tại mã số thẻ bảo hiểm
		if (!errors.hasFieldErrors("insuranceNumber")) {
			if(userLogic.checkExistedInsuNum(userInsurance.getInsuranceNumber(), userInsurance.getUserInternalID())) {
				errors.rejectValue("insuranceNumber", "UserInsurance.validate.ExistedNumber");
			}
		}
		
		// Validate so sánh ngày bắt đầu thẻ bảo hiểm và ngày kết thúc
		if(!errors.hasFieldErrors("insuranceStartDate") && !errors.hasFieldErrors("insuranceEndDate")) {
			Boolean rsCompare = Common.compareDate(userInsurance.getInsuranceStartDate(), userInsurance.getInsuranceEndDate());
			if(!rsCompare) {
				errors.rejectValue("insuranceStartDate", "UserInsurance.validate.InvalidDate");
			}
		}		
	}
}