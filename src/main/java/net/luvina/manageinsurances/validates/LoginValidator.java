/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *LoginValidator.java, Dec 22, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.validates;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import net.luvina.manageinsurances.controller.formbean.AccountFormBean;
import net.luvina.manageinsurances.entities.UserBean;
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
			Boolean rs = userLogic.checkExistedAcc(accountFormBean.getUserName(), Common.encryptMD5(accountFormBean.getPassword()));
			if(rs == false) {
				error.rejectValue("userName", "Account.validate.failed");
			}
		}
	}
	
}
