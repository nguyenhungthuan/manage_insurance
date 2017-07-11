/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *ErrorController.java, Dec 22, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.luvina.manageinsurances.utils.Constant;

/**
 * Class chứa các method xử lý liên quan tới MH thông báo lỗi
 * @author DELL
 *
 */
@Controller
@PropertySource(value="classpath:message_error.properties", encoding="UTF-8")
public class ErrorController {
	@Autowired
	private Environment env;
	/**
	 * Phương thức xử lý khi vào MH thông báo lỗi
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value="error.do", method=RequestMethod.GET)
	public String setMess(ModelMap modelMap,HttpServletRequest request) {
		String error = request.getParameter("error");
		String errorMsg = "";
		if(Constant.NOTFOUND.equals(error)) {
			errorMsg = env.getProperty("NOTFOUND");
		} else if(Constant.UpdateFailed.equals(error)) {
			errorMsg = env.getProperty("UpdateFailed");
		} else if(Constant.SYSTEM_ERROR.equals(error)) {
			errorMsg = env.getProperty("SystemError");
		} else if(Constant.NotFoundCP.equals(error)) {
			errorMsg = env.getProperty("NotFoundCP");
		}
		modelMap.addAttribute("errorMsg", errorMsg);
		return "Error";
	}
}