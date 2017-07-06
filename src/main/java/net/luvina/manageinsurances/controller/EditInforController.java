/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *EditInforController.java, Dec 16, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import net.luvina.manageinsurances.logic.impl.dto.CompanyDto;
import net.luvina.manageinsurances.controller.formbean.AccountFormBean;
import net.luvina.manageinsurances.controller.formbean.InforSearchFormBean;
import net.luvina.manageinsurances.controller.formbean.UserInsuranceFormBean;
import net.luvina.manageinsurances.logic.CompanyLogic;
import net.luvina.manageinsurances.logic.UserLogic;
import net.luvina.manageinsurances.utils.Common;
import net.luvina.manageinsurances.utils.Constant;
import net.luvina.manageinsurances.validates.RegisterFormValidator;

/**
 * Class chứa các method xử lý tác vụ MH04
 * @author DELL
 *
 */
@Controller
public class EditInforController {
	
	@Autowired
	private CompanyLogic companyLogic;
	@Autowired
	private UserLogic userLogic;
	@Autowired
	private RegisterFormValidator registerValidator;
	
	/**
	 * Phương thức được gọi khi nhấn nút Đăng ký tại MH04
	 * @param modelMap ModelMap
	 * @param userInsuranceFormBean UserInsuranceFormBean
	 * @param result BingdingResult
	 * @param session HttpSession
	 * @param request HttpServletRequest
	 * @return MH04 nếu có lỗi, nếu xử lý thành công thì return MH02
	 */
	@RequestMapping(value=Constant.UPDATE_URL, method=RequestMethod.POST)
	public String validateInput(ModelMap modelMap,@Valid @ModelAttribute("userInsurance") UserInsuranceFormBean userInsuranceFormBean, BindingResult result, HttpSession session, HttpServletRequest request) {
		try{
			modelMap.addAttribute("ssKey", request.getParameter("ssKey"));
			// validate thông tin nhập vào
			registerValidator.validate(userInsuranceFormBean, result);
			// nếu có lỗi, đưa thông tin lỗi lên MH04, hiển thị thông báo lỗi
			if(result.hasErrors()) {
				modelMap.addAttribute("userInsurance", userInsuranceFormBean);
				return Constant.MH04;
			}
			// không có lỗi, lấy account đăng nhập từ session
			AccountFormBean accountFormBean = (AccountFormBean) session.getAttribute("accountSess");
			boolean rs = userLogic.insertOrUpdateUser(Common.copyPropertyUIFormBeanToUIDto(userInsuranceFormBean), Common.copyProAcc(accountFormBean));
			if(rs) {
				return "redirect:ListUser/back.do";
			}
			return Constant.RE_UPDATEFAILED;
		} catch(Exception ex) {
			return Constant.RE_SYSTEM_ERROR;
		}		
	}
	
	/**
	 * Phương thức được gọi để xử lý ajax
	 * @return list company
	 */
	@RequestMapping(value="loadCBB.do", method=RequestMethod.GET)
	public @ResponseBody List<CompanyDto> loadCBB() {
		return companyLogic.getAllCom();
	}
	
	/**
	 * Phương thức lấy thông tin công ty khi chọn select box
	 * @param request HttpServletRequest
	 * @return CompanyBean
	 */
	@RequestMapping(value="loadComInfor.do", method=RequestMethod.GET)
	public @ResponseBody CompanyDto loadComInfor(HttpServletRequest request) {
		int companyID = 1;
		CompanyDto company = new CompanyDto();
		try{
			// lấy companyID truyền từ ajax
			companyID = Integer.parseInt(request.getParameter("companyID"));
			// kiểm tra xem có tồn tại ID vừa lấy không, nếu không có thì set là 1
			companyID = companyLogic.checkExistedCom(companyID) ? companyID : 1;
		} catch(NumberFormatException ex) {
			ex.printStackTrace();
		}
		company = companyLogic.getCompanyByID(companyID);
		return company;
	}
	
	/**
	 * Phương thức được gọi khi người dùng ấn Đăng ký
	 * @param modelMap ModelMap
	 * @param request HttpServletRequest
	 * @return MH04
	 */
	@RequestMapping(value=Constant.REGISTER_URL, method={RequestMethod.POST, RequestMethod.GET})
	public String register(ModelMap modelMap, HttpServletRequest request) {
		try {
			int companyID;
			String ssKey = request.getParameter("ssKey");
			// lấy dữ liệu từ session
			InforSearchFormBean inforSearchFormBean = (InforSearchFormBean) request.getSession().getAttribute(ssKey);
			// khi người dùng gõ url trả về 1 đối tượng inforsearchformbean mới
			inforSearchFormBean = inforSearchFormBean == null ? new InforSearchFormBean() : inforSearchFormBean;
			companyID = Integer.parseInt(inforSearchFormBean.getCompanyInternalID());
			// chỉ lấy companyID để set lên form
			UserInsuranceFormBean userInsuranceFormBean = new UserInsuranceFormBean();
			userInsuranceFormBean.setCompanyInternalID(companyID);
			// đưa lên request
			modelMap.addAttribute("ssKey", ssKey);
			modelMap.addAttribute("userInsurance", userInsuranceFormBean);
			return Constant.MH04;
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return Constant.RE_NOTFOUNDCP;
		} catch (Exception e) {
			e.printStackTrace();
			return Constant.RE_SYSTEM_ERROR;
		}
		
	}
}
