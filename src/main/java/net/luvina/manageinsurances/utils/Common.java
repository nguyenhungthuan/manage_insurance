/**
 * Copyright(C) 2016  Cty CP PM Luvina
 * Common.java, Nov 24, 2016
 */
package net.luvina.manageinsurances.utils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;

import net.luvina.manageinsurances.entities.CompanyBean;
import net.luvina.manageinsurances.entities.InsuranceBean;
import net.luvina.manageinsurances.entities.UserBean;
import net.luvina.manageinsurances.entities.UserInsuranceBean;
import net.luvina.manageinsurances.logic.impl.dto.AccountDto;
import net.luvina.manageinsurances.logic.impl.dto.CompanyDto;
import net.luvina.manageinsurances.logic.impl.dto.InforSearchDto;
import net.luvina.manageinsurances.logic.impl.dto.UserInsuranceDto;
import net.luvina.manageinsurances.controller.formbean.AccountFormBean;
import net.luvina.manageinsurances.controller.formbean.InforSearchFormBean;
import net.luvina.manageinsurances.controller.formbean.UserInsuranceFormBean;
/**
 * Class chứa các method dùng chung
 * @author DELL
 *
 */
public class Common {
	
	/**
	 * Phương thức kiểm tra đăng nhập
	 * @param session
	 * @return trả về màn hình login nếu chưa đăng nhập
	 */
	public static String checkLogin(HttpSession session) {
        return (session.getAttribute("accountSess") == null) ? Constant.MH01 : "";
    }

	/**
	 * Phương thức mã hóa mật khẩu
	 * @param mật khẩu
	 * @return mật khẩu đã mã hóa
	 */
	public static String encryptMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

	/**
	 * Phương thức chuyển đổi giới tính từ số sang chuỗi
	 * @param sexCode giá trị lấy từ DB
	 * @return Nam nếu sexCode là  1, Nữ nếu sexCode là  2
	 */
	public static String sexByString(int sexCode) {
		return sexCode == 1 ? "Nam" : "Nữ";
	}

	/**
	 * Phương thức xử lý các ký tự đặc biệt trong sql
	 * @param inputString chuỗi vào
	 * @return chuỗi đã thay đổi
	 */
	public static String processWildcard(String inputString) {
		String result = inputString.replace("\\", "\\\\");
		result = result.replace("%", "\\%");
		result = result.replace("_", "\\_");
		return result;
	}

	/**
	 * Phương thức xử lý các ký tự đặc biệt trong HTMl
	 * @param content chuẩn truyền vào
	 * @return chuỗi đã xử lý
	 */
	public static String escapeHTML(String content) {
	if(content != null) {
	   StringBuffer sb = new StringBuffer();
	   for (int i = 0; i < content.length(); i++) {
		   char c = content.charAt(i);
	       switch (c) {
	       case '<':
	          sb.append("&lt;");
	          break;
	       case '>':
	          sb.append("&gt;");
	          break;
	       case '&':
	          sb.append("&amp;");
	          break;
	       case '"':
	          sb.append("&quot;");
	          break;
	       default:
	          sb.append(c);
	   }
	   }
	    content = sb.toString();
	}
      return content;
    }

	/**
     * Tạo chuỗi paging
     * @param totalUsers tổng số user
     * @param limit số bản ghi trên 1 trang
     * @param currentPage trang hiện tại
     * @return
     */
    public static List<Integer> getListPaging (int totalRecords, int limit, int currentPage) {
    	List<Integer> lstPaging = new ArrayList<Integer>();
    	int totalPages = getTotalPage(totalRecords, limit);
    	float range = Integer.parseInt(ConfigProperties.getMessage("range"));
    	int space = (int) Math.ceil(range/2);
    	int startPage = currentPage - (space - 1);
    	if(startPage <= 0) {
    		startPage = 1;
    	} else if(totalPages - currentPage < space) {
    		startPage = totalPages - ((int)range - 1) > 0 ? totalPages - ((int)range - 1) : 1;
    	}
    	for(int i = startPage; i < startPage + range; i++) {
    		if(i <= totalPages) {
    			lstPaging.add(i);
    		} else {
    			break;
    		}
    	}
    	return lstPaging;
    }
    
    /**
     * Thay đổi giá trị currentPage nếu input không hợp lệ
     * @param currentPage trang hiện tại
     * @param totalPages tổng số trang
     * @return trang hợp lệ
     */
    public static int exchangeCurrentPage(int currentPage, int totalPages) {
    	if(currentPage > totalPages) {
    		return totalPages;
    	} else if(currentPage <= 0) {
    		return 1;
    	}
    	return currentPage;
    }

    /**
     * Lấy số lượng bản ghi trên 1 trang lưu trong file properties
     * @return số bản ghi trên 1 trang
     */
    public static int getLimit() {
    	int recordPerPage = 1;
    	try{
    		recordPerPage = Integer.parseInt(ConfigProperties.getMessage("limit"));
    	} catch (NumberFormatException ex) {
    		System.out.println("Limit is not a number");
    	}
    	return recordPerPage;
    }

    /**
     * Tổng số trang
     * @param totalUsers tổng số User
     * @param limit số bản ghi trên 1 trang
     * @return tổng số trang
     */
    public static int getTotalPage(int totalUsers, int limit) {
    	return (int) Math.ceil((double) totalUsers / limit);
    }

    /**
     * Lấy vị trí bắt đầu lấy dữ liệu trong DB
     * @param currentPage Trang hiện tại
     * @param limit số bản ghi trên 1 trang
     * @return vị trí bắt đầu
     */
    public static int getOffset(int currentPage,int limit) {
    	return limit * (currentPage - 1);
    }
    
    /**
     * Phương thức chuyển định dạng ngày tháng
     * @param date chuỗi ngày tháng vào
     * @return chuỗi theo format
     */
    public static String formatDate(String date) {
    	if(date != null) {
	    	String[] arrDate = date.split("-");
	    	return arrDate[2]+"/"+arrDate[1]+"/"+arrDate[0];
    	} 
    	return date;
    }
    
    /**
     * Lấy ra icon theo thứ tự sắp xếp
     * @param orderBy trạng thái sắp xếp hiện tại
     * @return icon
     */
    public static String getIcon(String orderBy) {
    	return orderBy.equals(Constant.ASC) ? "▲▽" : "△▼";
    }
    
    /**
     * So sánh 2 chuỗi ngày tháng
     * @param date1 ngày tháng 1 
     * @param date2 ngày tháng 2
     * @return true nếu ngày thứ 1 sớm hơn ngày thứ 2 và ngược lại
     */
    public static Boolean compareDate(String date1, String date2) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/DD");
		boolean resultCompare;
		try {
			Date dt1 = sdf.parse(date1);
			Date dt2 = sdf.parse(date2);
			resultCompare = dt1.before(dt2);
			if(resultCompare) {
				return true;
			}
		} catch (ParseException e) {
			System.out.println("error: " + e.getMessage());
		}
		return false;
    }
    
    /**
     * Convert lại ngày tháng để đưa vào DB
     * @param date ngày
     * @return ngày đã convert
     */
    public static String convertDateHQL(String date) {
    	if(date.length() > 0) {
    		String[] rs = date.split("/");
    		return rs[2]+"-"+rs[1]+"-"+rs[0];
    	}
    	return null;
    }
    
    /**
     * Phương thức kiểm tra tồn tại ngày
     * @param date chuỗi ngày nhập vào
     * @return return 1 nếu có tồn tại, 0 nếu không tồn tại và  return 2 nếu sai format
     */
	public static int checkExistDate(String date) {
		if(date.length() != 0) { 
			try {
				String[] partOfDate = date.split("/");
				int year = Integer.parseInt(partOfDate[2]);
				int month = Integer.parseInt(partOfDate[1]);
				int day = Integer.parseInt(partOfDate[0]);
		
				switch (month) {
				case 1:
				case 3:
				case 5:
				case 7:
				case 8:
				case 10:
				case 12: {
					if (day <= 31)
						return 1;
				}
				case 4:
				case 6:
				case 9:
				case 11: {
					if (day <= 30)
						return 1;
				}
				case 2: {
					if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
						if (day <= 29) {
							return 1;
						}
					} else {
						if (day <= 28) {
							return 1;
						}
					}
				}
				}
			} catch(ArrayIndexOutOfBoundsException ex) {
				System.out.println("Lỗi ngày nhập vào không đúng định dạng");
				return 2;
			}
			return 0;
			}
		return 1;
	}
	
	/**
	 * Phương thức chuyển hóa tên người dùng theo yêu cầu
	 * @param name tên người dùng
	 * @return chuỗi đã chuyển hóa 
	 */
	public static String convertStringName(String name) {
		String convertedName = VNCharacterUtils.removeAccent(name);
        String temp1[] = convertedName.split(" ");
        convertedName = ""; 
        for (int i = 0; i < temp1.length; i++) {
        	convertedName += String.valueOf(temp1[i].charAt(0)).toUpperCase() + temp1[i].substring(1);
            if (i < temp1.length - 1) {
            	convertedName += " ";
            }
        }
        return convertedName;
	}
	
	/**
	 * Phương thức lấy ra key bất kỳ để đưa lên session
	 * @return String key
	 */
	public static String getKey() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder key = new StringBuilder();
		Random random = new Random();
		while(key.length() < 30) {
			int index = (int) (random.nextFloat() * chars.length());
			key.append(chars.charAt(index));
		}
		return key.toString();
	}
	
	/**
	 * Phương thức thay đổi trang thái sắp xếp
	 * @param crSortType sortType hiện tại
	 * @return sortType mới
	 */
	public static String changeSortType(String crSortType) {
		return crSortType.equals(Constant.ASC) ? Constant.DESC : Constant.ASC;
	}
	
	/**
	 * Phương thức kiểm tra thứ tự sắp xếp
	 * @param sortType kiểu sắp xếp
	 * @return kiểu sắp xếp
	 */
	public static String checkSortType(String sortType) {
		return sortType.equals(Constant.DESC) ? Constant.DESC : Constant.ASC;
	}
	
	/**
	 * Phương thức escape xss
	 * @param userInsuranceFormBean UserInsuranceFormBean
	 * @return UserInsuranceFormBean
	 */
	public static UserInsuranceFormBean escapeXSSUser(UserInsuranceFormBean userInsuranceFormBean) {
		userInsuranceFormBean.setFullName(escapeHTML(userInsuranceFormBean.getFullName()));
		userInsuranceFormBean.setSex(escapeHTML(userInsuranceFormBean.getSex()));
		userInsuranceFormBean.setCompanyName(escapeHTML(userInsuranceFormBean.getCompanyName()));
		userInsuranceFormBean.setPlaceOfRegister(escapeHTML(userInsuranceFormBean.getPlaceOfRegister()));
		return userInsuranceFormBean;
	}
	
	/**
	 * Escape xss cho 1 list UserInsuranceFormBean
	 * @param listUser List<UserInsuranceFormBean>
	 * @return List<UserInsuranceFormBean>
	 */
	public static List<UserInsuranceFormBean> escapeListUser(List<UserInsuranceFormBean> listUser) {
		List<UserInsuranceFormBean> list = new ArrayList<>();
		for(UserInsuranceFormBean user : listUser) {
			list.add(escapeXSSUser(user));
		}
		return list;
	}
	
	/**
	 * Phương thức xử lý các ký tự đặc biệt cho 1 đối tượng
	 * @param inforSearchDto InforSearchDto
	 * @return inforSearchDto
	 */
	public static InforSearchDto processWildcardOb(InforSearchDto inforSearchDto) {
		inforSearchDto.setFullName(processWildcard(inforSearchDto.getFullName()));
		inforSearchDto.setInsuranceNumber(processWildcard(inforSearchDto.getInsuranceNumber()));
		inforSearchDto.setPlaceOfRegister(processWildcard(inforSearchDto.getPlaceOfRegister()));
		return inforSearchDto;
	}
	
	/**
	 * Copy thuộc tính
	 * @param userInsuranceBean UserInsuranceBean
	 * @return UserInsuranceDto
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public static UserInsuranceDto copyPropertyUIBeanToUIDto (UserInsuranceBean userInsuranceBean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		UserInsuranceDto userInsuranceDto = new UserInsuranceDto();
		PropertyUtils.copyProperties(userInsuranceDto, userInsuranceBean);
		return userInsuranceDto;
	}
	
	/**
	 * Copy thuộc tính
	 * @param list List<UserInsuranceBean>
	 * @return List<UserInsuranceDto>
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public static List<UserInsuranceDto> copyProListDtoToBean(List<UserInsuranceBean> list) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<UserInsuranceDto> lstuserInsuranceDto = new ArrayList<>();
		for(UserInsuranceBean bean : list) {
			lstuserInsuranceDto.add(copyPropertyUIBeanToUIDto(bean));
		}
		return lstuserInsuranceDto;
	}
	
	/**
	 * Copy thuộc tính
	 * @param userInsuranceFormBean UserInsuranceFormBean
	 * @return UserInsuranceDto
	 */
	public static UserInsuranceDto copyPropertyUIFormBeanToUIDto(UserInsuranceFormBean userInsuranceFormBean) {
		UserInsuranceDto userInsuranceDto = new UserInsuranceDto();
		try {
			PropertyUtils.copyProperties(userInsuranceDto, userInsuranceFormBean);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return userInsuranceDto;
	}
	/**
	 * Copy thuộc tính
	 * @param userInsuranceDto UserInsuranceDto
	 * @return UserInsuranceFormBean
	 */
	public static UserInsuranceFormBean copyPropertyUIDtoToUIFB(UserInsuranceDto userInsuranceDto) {
		UserInsuranceFormBean userInsuranceFormBean = new UserInsuranceFormBean();
		try {
			PropertyUtils.copyProperties(userInsuranceFormBean, userInsuranceDto);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return userInsuranceFormBean;
	}
	
	/**
	 * Copy thuộc tính
	 * @param accountFormBean AccountFormBean
	 * @return AccountDto
	 */
	public static AccountDto copyProAcc(AccountFormBean accountFormBean) {
		AccountDto accountDto = new AccountDto();
		try {
			PropertyUtils.copyProperties(accountDto, accountFormBean);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return accountDto;
	}
	/**
	 * Copy thuộc tính 1 list
	 * @param lstcompanyBean List<CompanyBean>
	 * @return List<CompanyDto>
	 */
	public static List<CompanyDto> castListCom(List<CompanyBean> lstcompanyBean) {
		List<CompanyDto> list = new ArrayList<>();
		for(CompanyBean bean : lstcompanyBean) {
			list.add(copyProCom(bean));
		}
		return list;
	}
	
	/**
	 * Copy thuộc tính
	 * @param companyBean CompanyBean
	 * @return CompanyDto
	 */
	public static CompanyDto copyProCom(CompanyBean companyBean) {
		CompanyDto companyDto = new CompanyDto();
		try{
			PropertyUtils.copyProperties(companyDto, companyBean);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return companyDto;
	}
	
	/**
	 * Copy thuộc tính
	 * @param inforSearchFormBean InforSearchFormBean
	 * @return InforSearchDto
	 */
	public static InforSearchDto copProISFBToISDto(InforSearchFormBean inforSearchFormBean) {
		InforSearchDto inforSearchDto = new InforSearchDto();
		try{
			PropertyUtils.copyProperties(inforSearchDto, inforSearchFormBean);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return inforSearchDto;
	}
	
	public static UserInsuranceDto combineUBAndUIBToUID(UserBean userBean){
		UserInsuranceDto userInsuranceDto = new UserInsuranceDto();
		try {
			PropertyUtils.copyProperties(userInsuranceDto, userBean);
			PropertyUtils.copyProperties(userInsuranceDto, userBean.getInsurance());
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		userInsuranceDto.setBirthday(Common.formatDate(userInsuranceDto.getBirthday()));
		userInsuranceDto.setInsuranceEndDate(Common.formatDate(userInsuranceDto.getInsuranceEndDate()));
		userInsuranceDto.setInsuranceStartDate(Common.formatDate(userInsuranceDto.getInsuranceStartDate()));
		userInsuranceDto.setCompanyInternalID(userBean.getCompany().getCompanyInternalId());
		return userInsuranceDto;
	}
}