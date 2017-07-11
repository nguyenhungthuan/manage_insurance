/**
 * Copyright(C) 2016  Cty CP PM Luvina
 * Common.java, Nov 24, 2016
 */
package net.luvina.manageinsurances.utils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.PropertyUtils;
import net.luvina.manageinsurances.entities.CompanyBean;
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
	 * Check log in
	 * @param session HttpSession
	 * @return view
	 */
	public static String checkLogin(HttpSession session) {
        return (session.getAttribute("accountSess") == null) ? Constant.MH01 : "";
    }

	/**
	 * Encrypt password
	 * @param password
	 * @return encrypted
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
	 * convert sex to string
	 * @param sexCode sex code
	 * @return "Nam" if sexCode = 1, "Nữ" if sexCode = 2
	 */
	public static String sexByString(int sexCode) {
		return sexCode == 1 ? "Nam" : "Nữ";
	}

	/**
	 * Process wildcard
	 * @param inputString input string
	 * @return processed string
	 */
	public static String processWildcard(String inputString) {
		String result = inputString.replace("\\", "\\\\");
		result = result.replace("%", "\\%");
		result = result.replace("_", "\\_");
		return result;
	}

	/**
	 * Escape HTML
	 * @param content input string
	 * @return escaped string
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
     * Get list paging
     * @param totalUsers total users
     * @param limit records per page
     * @param currentPage current page
     * @return list paging
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
     * Exchange current page
     * @param currentPage current page
     * @param totalPages total pages
     * @return output page
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
     * Get record per page
     * @return record per page
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
     * Get total page
     * @param totalUsers total User
     * @param limit record per page
     * @return total page
     */
    public static int getTotalPage(int totalUsers, int limit) {
    	return (int) Math.ceil((double) totalUsers / limit);
    }

    /**
     * Get offset
     * @param currentPage current page
     * @param limit record per page
     * @return offset
     */
    public static int getOffset(int currentPage,int limit) {
    	return limit * (currentPage - 1);
    }
    
    /**
     * Convert format date
     * @param date input date
     * @return converted string
     */
    public static String convertFormatDate(String date) {
    	if(date != null) {
	    	String[] arrDate = date.split("-");
	    	return arrDate[2]+"/"+arrDate[1]+"/"+arrDate[0];
    	} 
    	return date;
    }
    
    /**
     * Get icon order by
     * @param orderBy current order by
     * @return icon
     */
    public static String getIcon(String orderBy) {
    	return orderBy.equals(Constant.ASC) ? "▲▽" : "△▼";
    }
    
    /**
     * Compare string date
     * @param date1 date 1 
     * @param date2 date 2
     * @return true if date 1 early 2
     */
    public static Boolean compareDate(String date1, String date2) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/DD");
		try {
			Date dt1 = sdf.parse(date1);
			Date dt2 = sdf.parse(date2);
			return dt1.before(dt2);
		} catch (ParseException e) {
			System.out.println("error: " + e.getMessage());
			return false;
		}
    }
    
    /**
     * Convert date to insert to DB
     * @param date string date
     * @return converted string
     */
    public static String convertDateHQL(String date) {
    	if(date.length() > 0) {
    		String[] rs = date.split("/");
    		return rs[2]+"-"+rs[1]+"-"+rs[0];
    	}
    	return null;
    }
    
    /**
     * Check exist date
     * @param date string date
     * @return return 1 if exist, 0 if not exist và  return 2 invalid format
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
	 * Convert name
	 * @param name user name
	 * @return converted string
	 */
	public static String convertName(String name) {
		String arrayName[] = VNCharacterUtils.removeAccent(name).split(" ");
        StringBuilder convertedName = new StringBuilder();
        for (int i = 0; i < arrayName.length; i++) {
        	convertedName.append(String.valueOf(arrayName[i].charAt(0)).toUpperCase() + arrayName[i].substring(1));
            if (i < arrayName.length - 1) {
            	convertedName.append(" ");
            }
        }
        return convertedName.toString();
	}
	
	/**
	 * Get random key
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
	 * Change sort type
	 * @param crSortType current sortType
	 * @return sortType
	 */
	public static String changeSortType(String crSortType) {
		return crSortType.equals(Constant.ASC) ? Constant.DESC : Constant.ASC;
	}
	
	/**
	 * Check sort type
	 * @param sortType sort type
	 * @return sort type
	 */
	public static String checkSortType(String sortType) {
		return sortType.equals(Constant.DESC) ? Constant.DESC : Constant.ASC;
	}
	
	/**
	 * Escape xss
	 * @param userInsuranceFormBean UserInsuranceFormBean
	 * @return UserInsuranceFormBean
	 */
	public static UserInsuranceFormBean escapeXSSUser(UserInsuranceFormBean userInsurance) {
		userInsurance.setFullName(escapeHTML(userInsurance.getFullName()));
		userInsurance.setSex(escapeHTML(userInsurance.getSex()));
		userInsurance.setCompanyName(escapeHTML(userInsurance.getCompanyName()));
		userInsurance.setPlaceOfRegister(escapeHTML(userInsurance.getPlaceOfRegister()));
		return userInsurance;
	}
	
	/**
	 * Process wildcard object
	 * @param inforSearchDto InforSearchDto
	 * @return inforSearchDto
	 */
	public static InforSearchDto processWildcardOb(InforSearchDto inforSearch) {
		inforSearch.setFullName(processWildcard(inforSearch.getFullName()));
		inforSearch.setInsuranceNumber(processWildcard(inforSearch.getInsuranceNumber()));
		inforSearch.setPlaceOfRegister(processWildcard(inforSearch.getPlaceOfRegister()));
		return inforSearch;
	}
	
	/**
	 * Copy properties
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
	 * Copy properties
	 * @param list List<UserInsuranceBean>
	 * @return List<UserInsuranceDto>
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public static List<UserInsuranceDto> copyProListDtoToBean(List<UserInsuranceBean> list) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<UserInsuranceDto> userInsuranceDtos = new ArrayList<>();
		for(UserInsuranceBean bean : list) {
			userInsuranceDtos.add(copyPropertyUIBeanToUIDto(bean));
		}
		return userInsuranceDtos;
	}
	
	/**
	 * Copy properties
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
	 * Copy properties
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
	 * Copy properties
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
	 * Copy properties 1 list
	 * @param lstcompanyBean List<CompanyBean>
	 * @return List<CompanyDto>
	 */
	public static List<CompanyDto> castListCom(List<CompanyBean> companyBeans) {
		List<CompanyDto> companyDtos = new ArrayList<>();
		companyBeans.forEach(company -> companyDtos.add(copyProCom(company)));
		return companyDtos;
	}
	
	/**
	 * Copy properties
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
	 * Copy properties
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
		userInsuranceDto.setBirthday(Common.convertFormatDate(userInsuranceDto.getBirthday()));
		userInsuranceDto.setInsuranceEndDate(Common.convertFormatDate(userInsuranceDto.getInsuranceEndDate()));
		userInsuranceDto.setInsuranceStartDate(Common.convertFormatDate(userInsuranceDto.getInsuranceStartDate()));
		userInsuranceDto.setCompanyInternalID(userBean.getCompany().getCompanyInternalId());
		return userInsuranceDto;
	}
}