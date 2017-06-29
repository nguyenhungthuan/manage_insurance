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
import net.luvina.manageinsurances.entities.UserInsuranceBean;
import net.luvina.manageinsurances.logic.impl.dto.AccountDto;
import net.luvina.manageinsurances.logic.impl.dto.CompanyDto;
import net.luvina.manageinsurances.logic.impl.dto.InforSearchDto;
import net.luvina.manageinsurances.logic.impl.dto.UserInsuranceDto;
import net.luvina.manageinsurances.controller.formbean.AccountFormBean;
import net.luvina.manageinsurances.controller.formbean.InforSearchFormBean;
import net.luvina.manageinsurances.controller.formbean.UserInsuranceFormBean;
/**
 * Class chá»©a cÃ¡c method dÃ¹ng chung
 * @author DELL
 *
 */
public class Common {
	
	/**
	 * PhÆ°Æ¡ng thá»©c kiá»ƒm tra Ä‘Äƒng nháº­p
	 * @param session
	 * @return tráº£ vá»� mÃ n hÃ¬nh ADM001 náº¿u chÆ°a Ä‘Äƒng nháº­p
	 */
	public static String checkLogin(HttpSession session) {
        return (session.getAttribute("accountSess") == null) ? Constant.MH01 : "";
    }

	/**
	 * PhÆ°Æ¡ng thá»©c mÃ£ hÃ³a máº­t kháº©u
	 * @param input máº­t kháº©u
	 * @return chuá»—i Ä‘Ã£ mÃ£ hÃ³a
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
	 * PhÆ°Æ¡ng thá»©c tráº£ vá»� giá»›i tÃ­nh lÃ  1 chuá»—i
	 * @param sexCode mÃ£ giá»›i tÃ­nh láº¥y tá»« DB
	 * @return Nam náº¿u sexCode lÃ  1, Ná»¯ náº¿u sexCode lÃ  2
	 */
	public static String sexByString(int sexCode) {
		return sexCode == 1 ? "Nam" : "Ná»¯";
	}

	/**
	 * PhÆ°Æ¡ng thá»©c xá»­ lÃ½ cÃ¡c kÃ½ tá»± Ä‘áº·c biá»‡t trong sql
	 * @param inputString chuá»—i vÃ o
	 * @return chuá»—i tráº£ vá»�
	 */
	public static String processWildcard(String inputString) {
		String result = inputString.replace("\\", "\\\\");
		result = result.replace("%", "\\%");
		result = result.replace("_", "\\_");
		return result;
	}

	/**
	 * PhÆ°Æ¡ng thá»©c xá»­ lÃ½ chuá»—i cÃ³ cÃ¡c kÃ½ tá»± HTML
	 * @param content chuá»—i cáº§n xá»­ lÃ½
	 * @return chuá»—i Ä‘Ã£ xá»­ lÃ½
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
     * Táº¡o chuá»—i paging
     * @param totalUsers tá»•ng sÃ´ user
     * @param limit sá»‘ lÆ°á»£ng cáº§n hiá»ƒn thá»‹ trÃªn 1 trang
     * @param currentPage trang hiá»‡n táº¡i
     * @return
     */
    public static List<Integer> getListPaging (int totalRecords, int limit, int currentPage) {
    	List<Integer> lstPaging = new ArrayList<Integer>();
    	int totalPages = getTotalPage(totalRecords, limit);
    	int range = Integer.parseInt(ConfigProperties.getMessage("range"));
    	int startPage = 1;
    	int endPage = totalPages;
    	int space = (int) Math.floor(range/2);
    	if(currentPage > space && currentPage <= totalPages - space){
    		startPage = currentPage - range/2;
    		endPage = currentPage + range/2;
    	} else if(currentPage <= space) {
	    	endPage = startPage + range - 1;
    	} else if(totalPages > range) {
    		startPage = totalPages - (range - 1);
    	}
    	for(int i = startPage; i <= endPage; i++) {
    		if(i <= totalPages) {
    			lstPaging.add(i);
    		} else {
    			break;
    		}
    	}
    	return lstPaging;
    }

    /**
     * Láº¥y sá»‘ lÆ°á»£ng hiá»ƒn thá»‹ báº£n ghi trÃªn 1 trang
     * @return sá»‘ lÆ°á»£ng records cáº§n láº¥y
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
     * TÃ­nh tá»•ng sá»‘ trang
     * @param totalUsers tá»•ng sá»‘ User
     * @param limit sá»‘ lÆ°á»£ng cáº§n hiá»ƒn thá»‹ trÃªn 1 trang
     * @return tá»•ng sá»‘ trang
     */
    public static int getTotalPage(int totalUsers, int limit) {
    	return (int) Math.ceil((double) totalUsers / limit);
    }

    /**
     * Láº¥y vá»‹ trÃ­ data cáº§n láº¥y
     * @param currentPage Trang hiá»‡n táº¡i
     * @param limit sá»‘ lÆ°á»£ng cáº§n hiá»ƒn thá»‹ trÃªn 1 trang
     * @return vá»‹ trÃ­ cáº§n láº¥y
     */
    public static int getOffset(int currentPage,int limit) {
    	return limit * (currentPage - 1);
    }
    
    /**
     * PhÆ°Æ¡ng thá»©c chuyá»ƒn Ä‘á»‹nh dáº¡ng chuá»—i ngÃ y thÃ¡ng 
     * @param date ngÃ y thÃ¡ng 
     * @return chuá»—i theo format
     */
    public static String formatDate(String date) {
    	if(date != null) {
	    	String[] arrDate = date.split("-");
	    	return arrDate[2]+"/"+arrDate[1]+"/"+arrDate[0];
    	} 
    	return date;
    }
    
    /**
     * PhÆ°Æ¡ng thá»©c láº¥y icon cho tá»«ng tÆ°á»�ng
     * @param orderBy trÃ¬nh tá»± sáº¯p xáº¿p cá»§a trÆ°á»�ng
     * @return icon
     */
    public static String getIcon(String orderBy) {
    	return orderBy.equals(Constant.ASC) ? "â–²â–½" : "â–³â–¼";
    }
    
    /**
     * PhÆ°Æ¡ng thá»©c so sÃ¡nh 2 ngÃ y 
     * @param date1 ngÃ y thá»© 1 
     * @param date2 ngÃ y thá»© 2
     * @return true náº¿u ngÃ y thá»© 1 sá»›m hÆ¡n ngÃ y thá»© 2 vÃ  ngÆ°á»£c láº¡i
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
     * Convert láº¡i ngÃ y Ä‘á»ƒ Ä‘Æ°a vÃ o DB
     * @param date ngÃ y
     * @return ngÃ y Ä‘Ã£ convert
     */
    public static String convertDateHQL(String date) {
    	if(date.length() > 0) {
    		String[] rs = date.split("/");
    		return rs[2]+"-"+rs[1]+"-"+rs[0];
    	}
    	return null;
    }
    
    /**
     * PhÆ°Æ¡ng thá»©c kiá»ƒm tra chuá»—i ngÃ y thÃ¡ng nháº­p vÃ o
     * @param date chuá»—i ngÃ y thÃ¡ng
     * @return return 1 náº¿u ngÃ y cÃ³ tá»“n táº¡i, 0 náº¿u ngÃ y khÃ´ng tá»“n táº¡i vÃ  return 2 náº¿u sai format
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
				System.out.println("Lá»—i nháº­p ngÃ y khÃ´ng Ä‘Ãºng Ä‘á»‹nh dáº¡ng");
				return 2;
			}
			return 0;
			}
		return 1;
	}
	
	/**
	 * PhÆ°Æ¡ng thá»©c mÃ£ hÃ³a tÃªn theo Ä‘á»‹nh dáº¡ng yÃªu cáº§u
	 * @param name tÃªn do ngÆ°á»�i dÃ¹ng nháº­p vÃ o
	 * @return tÃªn Ä‘Ã£ mÃ£ hÃ³a
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
	 * PhÆ°Æ¡ng thá»©c láº¥y key báº¥t ká»³ Ä‘á»ƒ Ä‘áº·t tÃªn cho Ä‘á»‘i tÆ°á»£ng truyá»�n lÃªn session
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
	 * PhÆ°Æ¡ng thá»©c thay Ä‘á»•i kiá»ƒu sáº¯p xáº¿p
	 * @param crSortType sortType hiá»‡n táº¡i
	 * @return sortType má»›i
	 */
	public static String changeSortType(String crSortType) {
		return crSortType.equals(Constant.ASC) ? Constant.DESC : Constant.ASC;
	}
	
	/**
	 * PhÆ°Æ¡ng thá»©c kiá»ƒm tra kiá»ƒu sáº¯p xáº¿p
	 * @param sortType kiá»ƒu sáº¯p xáº¿p
	 * @return kiá»ƒu sáº¯p xáº¿p
	 */
	public static String checkSortType(String sortType) {
		return sortType.equals(Constant.DESC) ? Constant.DESC : Constant.ASC;
	}
	
	/**
	 * PhÆ°Æ¡ng thá»©c escape xss cho 1 Ä‘á»‘i tÆ°á»£ng 
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
	 * PhÆ°Æ¡ng thá»©c escape xss cho 1 list UserInsuranceFormBean
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
	 * PhÆ°Æ¡ng thá»©c xá»­ lÃ½ cÃ¡c kÃ½ tá»± Ä‘áº·c biá»‡t cho Ä‘á»‘i tÆ°á»£ng chá»©a thÃ´ng tin tÃ¬m kiáº¿m
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
	 * Copy thuá»™c tÃ­nh sang 1 Ä‘á»‘i tÆ°á»£ng má»›i
	 * @param userInsuranceBean UserInsuranceBean
	 * @return UserInsuranceDto
	 */
	public static UserInsuranceDto copyPropertyUIBeanToUIDto (UserInsuranceBean userInsuranceBean) {
		UserInsuranceDto userInsuranceDto = new UserInsuranceDto();
		try {
			PropertyUtils.copyProperties(userInsuranceDto, userInsuranceBean);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return userInsuranceDto;
	}
	
	/**
	 * Copy thuá»™c tÃ­nh 1 list Ä‘á»‘i tÆ°á»£ng 
	 * @param list List<UserInsuranceBean>
	 * @return List<UserInsuranceDto>
	 */
	public static List<UserInsuranceDto> copyProListDtoToBean(List<UserInsuranceBean> list) {
		List<UserInsuranceDto> lstuserInsuranceDto = new ArrayList<>();
		for(UserInsuranceBean bean : list) {
			lstuserInsuranceDto.add(copyPropertyUIBeanToUIDto(bean));
		}
		return lstuserInsuranceDto;
	}
	
	/**
	 * Copy thuá»™c tÃ­nh má»™t Ä‘á»‘i tÆ°á»£ng 
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
	 * Copy thuá»™c tÃ­nh má»™t Ä‘á»‘i tÆ°á»£ng 
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
	 * copy thuá»™c tÃ­nh 1 Ä‘á»‘i tÆ°á»£ng
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
	 * Copy thuá»™c tÃ­nh 1 list
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
	 * Copy thuá»™c tÃ­nh 1 Ä‘á»‘i tÆ°á»£ng
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
	 * Copy thuá»™c tÃ­nh 1 Ä‘á»‘i tÆ°á»£ng
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
}
