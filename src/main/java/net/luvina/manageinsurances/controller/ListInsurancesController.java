/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *ListInsurancesController.java, Dec 3, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import net.luvina.manageinsurances.logic.impl.dto.CompanyDto;
import net.luvina.manageinsurances.logic.impl.dto.InforSearchDto;
import net.luvina.manageinsurances.logic.impl.dto.UserInsuranceDto;
import net.luvina.manageinsurances.controller.formbean.InforSearchFormBean;
import net.luvina.manageinsurances.logic.CompanyLogic;
import net.luvina.manageinsurances.logic.UserLogic;
import net.luvina.manageinsurances.utils.Common;
import net.luvina.manageinsurances.utils.Constant;

/**
 * Class chứa các method liên quan tới xử lý MH02
 * @author DELL
 *
 */
// thêm annotation để nhận biết đây là controller
@Controller
@PropertySource(value="classpath:message_error.properties", encoding="UTF-8")
public class ListInsurancesController {
	// 1 đối tượng tblcompanylogic sẽ được tạo ra
	@Autowired
	private CompanyLogic companyLogic;
	// 1 đối tượng tbluserlogic sẽ được tạo ra
	@Autowired
	private UserLogic userLogic;
	@Autowired
	private Environment env;
	/**
	 * Phương thức sử dụng cho MH02
	 * @param modelMap model map
	 * @param userInsurance UserInsuranceFormBean
	 * @param request HttpServletRequest
	 * @param session HttpSession
	 * @return MH02
	 */
	@RequestMapping(value={"ListUser.do","/ListUser/{action}.do"}, method=RequestMethod.GET)
	public String manageInsu(ModelMap modelMap,InforSearchFormBean inforSearchFormBean, @ModelAttribute("lstCompany") List<CompanyDto> lstCompany, HttpSession session, @PathVariable Optional<String> action, HttpServletRequest request){
		try {
			// nếu có lỗi trong quá trình lấy công ty, đưa đến màn hình lỗi
			if(lstCompany.size() == 0) throw new Exception();
			int limit, offset, totalRecords;
			int currentPage = 1;
			String sortBy = Constant.SORTBY;
			// nếu là các trường hợp search, back, sort, change
			if (action.isPresent()) {
				String actionValue = action.get();
				// nếu là search, set lại số trang và thứ tự sắp xếp
				if (actionValue.equals(Constant.SEARCH)) {
					inforSearchFormBean.setCurrentPage("1");
					inforSearchFormBean.setSortType(Constant.ASC);
					// nếu là sort, set lại sortType
				} else if (actionValue.equals(Constant.SORT)) {
					inforSearchFormBean.setSortType(Common.changeSortType(inforSearchFormBean.getSortType()));
					// nếu là back, lấy object từ session
				} else if (actionValue.equals(Constant.BACK)) {
					InforSearchFormBean search = ((InforSearchFormBean) session.getAttribute(request.getParameter("ssKey")));
					inforSearchFormBean = (search == null ? new InforSearchFormBean() : search);
					// trường hợp thay đổi công ty, set lại company ID
				} else if (actionValue.equals(Constant.CHANGE)) {
					inforSearchFormBean = new InforSearchFormBean(inforSearchFormBean.getCompanyInternalID());
				}
			}
			// convert page
			currentPage = Integer.parseInt(inforSearchFormBean.getCurrentPage());
			// số bản ghi trên 1 trang
			limit = Common.getLimit();
			// vị trí bản ghi phục vụ phân trang
			offset = Common.getOffset(currentPage, limit);
			// tổng số bản ghi phù hợp
			InforSearchDto searchDto = Common.copProISFBToISDto(inforSearchFormBean);
			// tổng số bản ghi
			totalRecords = userLogic.getTotalRecords(searchDto);
			// tổng số trang
			int totalPage = Common.getTotalPage(totalRecords, limit);
			// list paging
			List<Integer> listPaging = Common.getListPaging(totalRecords, limit, currentPage);
			// list user infor
			List<UserInsuranceDto> listInfor = userLogic.getListInfor(searchDto, sortBy, limit, offset);
			String ssKey = request.getParameter("ssKey");
			// tạo mới session hoặc lấy từ request
			String key = ssKey == null ? Common.getKey() : ssKey;
			// đưa đối tượng lên session
			session.setAttribute(key, inforSearchFormBean);
			// đưa lên request
			modelMap.addAttribute("ssKey", key);
			modelMap.addAttribute("inforSearchFormBean", inforSearchFormBean);
			modelMap.addAttribute("listInfor",listInfor);
			modelMap.addAttribute("listPaging", listPaging);
			modelMap.addAttribute("iconSort", Common.getIcon(inforSearchFormBean.getSortType()));
			modelMap.addAttribute("totalPages", totalPage);
		} catch (NumberFormatException e) {
			modelMap.addAttribute("error", env.getProperty("InvalidInput"));
		} catch (Exception ex) {
			ex.printStackTrace();
			return Constant.RE_SYSTEM_ERROR;
		}
		return Constant.MH02;
	}
	
	/**
	 * Phương thức lấy tất cả công ty có trong DB để đưa sang jsp
	 * @return list<CompanyBean>
	 */
	@ModelAttribute("lstCompany")
	public List<CompanyDto> getListCompany() {
		try {
			return companyLogic.getAllCom();
		} catch(Exception ex) {
			return new ArrayList<CompanyDto>();
		}
	}
}
