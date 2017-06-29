/**
 * 
 */
package net.luvina.manageinsurances.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import net.luvina.manageinsurances.controller.formbean.AccountFormBean;

/**
 * @author nguyenhungthuan
 *
 */
@Controller
public class WelcomeController {
	
	@RequestMapping("/")
	public String welcome(Map<String, Object> model, ModelMap modelMap) {
		modelMap.addAttribute("account", new AccountFormBean());
		return "MH01";
	}
}
