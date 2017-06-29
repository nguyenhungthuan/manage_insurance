/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *LoginValidator.java, Dec 22, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.validates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import net.luvina.manageinsurances.controller.formbean.AccountFormBean;
import net.luvina.manageinsurances.logic.UserLogic;
import net.luvina.manageinsurances.utils.Common;

/**
 * Class validate account
 * @author DELL
 *
 */
@Component
public class LoginValidator{
	@Autowired
	private UserLogic userLogic;

	public void validate(AccountFormBean accountFormBean, Errors error) {
		// nếu giá trị của username và password nhập vào không phải là rỗng thì mới tiếp tục validate
		if(!error.hasErrors()) {		
			Boolean rsCheck = userLogic.checkExistedAcc(accountFormBean.getUserName(), Common.encryptMD5(accountFormBean.getPassword()));
			if(!rsCheck) {
				error.rejectValue("userName", "Account.validate.failed");
			}
		}
	}
	
}
