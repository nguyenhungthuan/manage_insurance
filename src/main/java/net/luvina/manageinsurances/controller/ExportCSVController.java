/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *ExportCSVController.java, Dec 28, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.ScrollableResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import net.luvina.manageinsurances.controller.formbean.InforSearchFormBean;
import net.luvina.manageinsurances.entities.UserInsuranceBean;
import net.luvina.manageinsurances.logic.CompanyLogic;
import net.luvina.manageinsurances.logic.UserLogic;
import net.luvina.manageinsurances.logic.impl.dto.CompanyDto;
import net.luvina.manageinsurances.utils.Common;
import net.luvina.manageinsurances.utils.Constant;

/**
 * @author DELL
 *
 */
@Controller
@PropertySource(value="classpath:label_text.properties", encoding="UTF-8")
public class ExportCSVController {
	@Autowired
	private UserLogic userLogic;
	@Autowired
	private CompanyLogic companyLogic;
	@Autowired
	private Environment env;
	
	@RequestMapping(value = Constant.DOWNLOADCSV_URL, method=RequestMethod.GET)
	public void downloadCSV(HttpServletResponse response, @ModelAttribute("inforSearchFormBean") InforSearchFormBean  inforSearch, HttpServletRequest request)throws IOException {
		String sortBy = Constant.SORTBY;
		String fileName = Constant.FILE_NAME;
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", fileName);
		// Cấu hình header cho file export
		response.setHeader(headerKey, headerValue);
		response.setContentType("text/csv; character:UTF-8");
		BufferedWriter writer = addBOM(response);
		ScrollableResults results =  userLogic.getListDataToExport(Common.copProISFBToISDto(inforSearch),sortBy);
		// Tạo đối tượng csvbeanwriter với writer vừa thêm BOM
		ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(writer,CsvPreference.STANDARD_PREFERENCE);
		int comID = Integer.parseInt(inforSearch.getCompanyInternalID());
		writeFile(csvBeanWriter, comID, results);
	}
	
	private BufferedWriter addBOM(HttpServletResponse response) {
		try {
			// Thêm BOM(Byte of mark) vào đầu file để có thể hiển thị tiếng Việt
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(0xEF);   
			outputStream.write(0xBB);
			outputStream.write(0xBF);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
			return writer;
		} catch (IOException e) {
			return null;
		}
	}
	
	private void writeFile(ICsvBeanWriter csvBeanWriter, int companyID, ScrollableResults results) {
		String title = env.getProperty("TITLE_02");
		// Set header cho dữ liệu trong bảng 
		String[] headerUser = {Constant.FULLNAME, Constant.SEX, Constant.BIRTHDAY, Constant.INSURANCE_NUMBER, Constant.IN_START_DATE, Constant.IN_END_DATE,Constant.REGISTER_PLACE};
		// Tiêu đề các cột khi export
		String[] headerShow = {env.getProperty("FULLNAME"), env.getProperty("SEX"), env.getProperty("BIRTHDATE"), env.getProperty("INSURANCE_CODE"), env.getProperty("START_DATE"), env.getProperty("END_DATE"), env.getProperty("REGISTER_PLACE")};			
		CompanyDto company = companyLogic.getCompanyByID(companyID);
		try {
			csvBeanWriter.writeComment(title+"\n");
			csvBeanWriter.writeComment(env.getProperty("COMPANY_NAME")+","+company.getCompanyName());
			csvBeanWriter.writeComment(env.getProperty("ADDRESS")+","+company.getAddress());
			csvBeanWriter.writeComment(env.getProperty("EMAIL")+","+company.getEmail());
			csvBeanWriter.writeComment(env.getProperty("TEL")+", \t"+company.getTel());
			csvBeanWriter.writeComment("\n");
			csvBeanWriter.writeHeader(headerShow);
			while(results.next()){
				csvBeanWriter.write(addCharToField((UserInsuranceBean) results.get(0)), headerUser);
			}
			csvBeanWriter.close();
			results.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private UserInsuranceBean addCharToField(UserInsuranceBean userInsurance) {
		userInsurance.setBirthday("\t" + userInsurance.getBirthday());
		userInsurance.setTelephone("\t" + userInsurance.getTelephone());
		userInsurance.setInsuranceNumber("\t" + userInsurance.getInsuranceNumber());
		userInsurance.setInsuranceStartDate("\t" + userInsurance.getInsuranceStartDate());
		userInsurance.setInsuranceEndDate("\t" + userInsurance.getInsuranceEndDate());
		return userInsurance;
	}
}