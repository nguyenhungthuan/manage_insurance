/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *ViewDetailsController.java, Dec 11, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import net.luvina.manageinsurances.logic.impl.dto.UserInsuranceDto;
import net.luvina.manageinsurances.controller.formbean.InforSearchFormBean;
import net.luvina.manageinsurances.controller.formbean.UserInsuranceFormBean;
import net.luvina.manageinsurances.logic.UserLogic;
import net.luvina.manageinsurances.utils.Common;
import net.luvina.manageinsurances.utils.Constant;
import net.luvina.manageinsurances.utils.NotFoundUserException;

/**
 * Class chứa các method xử lý liên quan tới MH03
 * @author DELL
 *
 */
@Controller
@PropertySource(value="classpath:label_text.properties", encoding="UTF-8")
public class ViewDetailsController {
	@Autowired
	private UserLogic userLogic;
	@Autowired
	private Environment evn;
	
	/**
	 * Phương thức sử dụng khi từ MH02 sang MH03
	 * @param modelMap ModelMap
	 * @param session HttpSession
	 * @param ssKey key của đối tượng tìm kiếm được đưa lên session
	 * @param id id của user từ MH02
	 * @return MH03
	 */
	@RequestMapping(value=Constant.VIEW_URL+"/{id}/{ssKey}", method=RequestMethod.GET)
	public String getDetailsInfor (ModelMap modelMap, HttpSession session, @PathVariable("ssKey") String ssKey, @PathVariable("id") String id) {
		try{
			int userID = Integer.parseInt(id);
			// kiểm tra tồn tại user trong DB, nếu không tồn tại hiển thị màn hình lỗi
			if(!userLogic.checkExistUser(userID)) throw new NotFoundUserException(evn.getProperty("NotFoundUser"));
			// nếu có thì lấy ra user
			UserInsuranceFormBean userInsuranceFormBean = Common.copyPropertyUIDtoToUIFB(userLogic.getDetailsInfor(userID));
			// đưa object user insurance và session key sang MH03
			modelMap.addAttribute("userInsurance", userInsuranceFormBean);
			modelMap.addAttribute("ssKey", ssKey);
		}catch(NumberFormatException | NotFoundUserException ex) {
			// nếu id nhập vào không hợp lệ thì chuyển qua màn hình lỗi
			return Constant.RE_NOTFOUND;
		}
		return Constant.MH03;
	}
	
	/**
	 * Phương thức được gọi khi xóa user
	 * @param modelMap ModelMap
	 * @param request HttpServletRequest
	 * @param session HttpSession
	 * @return MH02
	 */
	@RequestMapping(value="/DeleteUser.do", method=RequestMethod.POST)
	public String deleteUser(ModelMap modelMap, HttpServletRequest request, HttpSession session) {
		try{
			String ssKey = request.getParameter("ssKey");
			// thực hiện xóa user
			boolean rs = userLogic.deleteUser(Integer.parseInt(request.getParameter("userInternalID")));
			// nếu xóa thành công
			if(rs) {
				// lấy đối tượng lưu trên session xuống
				InforSearchFormBean inforSearchFormBean = (InforSearchFormBean) session.getAttribute(ssKey);
				// reset các giá trị theo yêu cầu, chỉ giữ lại companyID
				inforSearchFormBean = new InforSearchFormBean(inforSearchFormBean.getCompanyInternalID());
				// đưa lại lên session
				session.setAttribute(ssKey, inforSearchFormBean);
				// chuyển qua MH02
				return "redirect:/ListUser/back.do?ssKey="+ssKey;
			} else {
				return Constant.RE_SYSTEM_ERROR;
			}
		}catch(NumberFormatException ex) {
			return Constant.RE_NOTFOUND;
		}		
	}
}