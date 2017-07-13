/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *LoginController.java, Dec 1, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import net.luvina.manageinsurances.controller.formbean.AccountFormBean;
import net.luvina.manageinsurances.utils.Common;
import net.luvina.manageinsurances.utils.Constant;
import net.luvina.manageinsurances.validates.LoginValidator;

/**
 * Class chứa các phương thức xử lý liên quan tới MH01
 * @author DELL
 *
 */
@Controller
public class LoginController {
	@Autowired
	private LoginValidator loginValidator;

	/**
	 * Phương thức sử dụng khi vào màn hình login với method = get
	 * @param model model
	 * @return MH01
	 */
	@RequestMapping(value={"/login", "/"},method=RequestMethod.GET)
	public String login(ModelMap modelMap) {
		modelMap.addAttribute("account", new AccountFormBean()); 
		return Constant.MH01;
	}
	
	/**
	 * Phương thức được gọi khi ấn đăng nhập tại MH01
	 * @param modelMap model map
	 * @param accountFormBean account chứa thông tin người dùng vừa nhập
	 * @param result mảng chữa lỗi
	 * @return nếu có lỗi return MH01, nếu không có lỗi return MH02
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)	
	public String login(ModelMap modelMap,@Valid @ModelAttribute("account") AccountFormBean accountFormBean, BindingResult result, HttpSession session) {
		try {
			// validate account
			loginValidator.validate(accountFormBean, result);
			// nếu có lỗi
			if (result.hasErrors()) {
				modelMap.addAttribute("account", accountFormBean);
				// trả về MH01 với thông tin vừa nhập vào
				return Constant.MH01;
			}
			// nếu không có lỗi
			// set lại password mã hóa để dùng khi insert
			accountFormBean.setPassword(Common.encryptMD5(accountFormBean.getPassword()));
			// đưa account lên session để dùng cho filter và đăng ký account
			session.setAttribute("accountSess", accountFormBean);
			return Constant.REDIR_MH02;
		} catch (Exception ex) {
			ex.printStackTrace();
			return Constant.RE_SYSTEM_ERROR;
		}
	}		
}