package net.luvina.manageinsurances.logic.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import net.luvina.manageinsurances.dao.CompanyDao;
import net.luvina.manageinsurances.dao.UserDao;
import net.luvina.manageinsurances.entities.CompanyBean;
import net.luvina.manageinsurances.entities.UserBean;
import net.luvina.manageinsurances.entities.UserInsuranceBean;
import net.luvina.manageinsurances.logic.impl.dto.AccountDto;
import net.luvina.manageinsurances.logic.impl.dto.InforSearchDto;
import net.luvina.manageinsurances.logic.impl.dto.UserInsuranceDto;
import net.luvina.manageinsurances.utils.Common;

@RunWith(MockitoJUnitRunner.class)
public class UserLogicImplTest {
	
	@InjectMocks
	private UserLogicImpl sut;
	@Mock
	private UserDao userDao;
	@Mock
	private CompanyDao companyDao;
	
	@Before
	public void setUp() {

		// Test method checkExistedAccount
		when(userDao.findByUserNameAndPassword(anyString(), anyString())).thenAnswer(new Answer<List<UserBean>>() {

			@Override
			public List<UserBean> answer(InvocationOnMock invocation) throws Throwable {
				List<UserBean> listAccount = new ArrayList<>();
				listAccount.add(new UserBean());
				return listAccount;
			}
		});
		
		// Test method getListInfor
		when(userDao.getListInfor(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString(), anyInt(), anyInt())).thenAnswer(new Answer<List<UserInsuranceBean>>() {

			@Override
			public List<UserInsuranceBean> answer(InvocationOnMock invocation) throws Throwable {
				List<UserInsuranceBean> listUserInsuranceBean = new ArrayList<>();
				listUserInsuranceBean.add(new UserInsuranceBean(1, "Thuan"));
				listUserInsuranceBean.add(new UserInsuranceBean(2, "Nam"));
				return listUserInsuranceBean;
			}
			
		});		
		
		// Test method getTotalRecords
		when(userDao.getTotalRecords(anyInt(), anyString(), anyString(), anyString())).thenReturn(1);
		
//		// Test method getDetailsInfor
//		when(userDao.getDetailsInfor(anyInt())).thenAnswer(new Answer<UserInsuranceBean>() {
//
//			@Override
//			public UserInsuranceBean answer(InvocationOnMock invocation) throws Throwable {
//				return new UserInsuranceBean(1, 1, "Luvina", "Thuan", "1", "1995-03-20", "0123456789", "1995-03-12", "1995-03-15", "HN");
//			}
//		});
	}
	
	/**
	 * [IN]
	 * InforSearchDto: companyInternalID = 1, fullName = "", insuranceNumber = "", placeOfRegister = "", sortType = "ASC"
	 * sortBy = "u.fullName"
	 * limit = 5
	 * offset = 0
	 * [OUT]
	 * List<UserInternalDto> = Arrays.asList(new UserInsuranceDto(2, "Nam"), new UserInsuranceDto(1, "Thuan"))
	 */
	@Test
	public void getListInfor(){
		//setup
		InforSearchDto inforSearchDto = new InforSearchDto("1","","","","ASC");
		
		//exercise
		List<UserInsuranceDto> list = sut.getListInfor(inforSearchDto, "u.fullName", 5, 0);
		
		//verify
		assertThat(list, containsInAnyOrder(new UserInsuranceDto(2, "Nam"), new UserInsuranceDto(1, "Thuan")));
	}
	
	/**
	 * [IN]
	 * InforSearchDto: companyInternalID = 1, fullName = "", insuranceNumber = "", placeOfRegister = "", sortType = "ASC"
	 * [OUT]
	 * 1
	 */
	@Test
	public void getTotalRecords(){
		//setup
		InforSearchDto inforSearchDto = new InforSearchDto("1","","","","ASC");
		
		//exercise
		int count = sut.getTotalRecords(inforSearchDto);
		
		//verify
		assertEquals(1, count);
	}
	
	/**
	 * [IN]
	 * userName = 1
	 * password = 1
	 * [OUT]
	 * true
	 */
	@Test
	public void checkExistedAccount() {
		boolean rsCheck = sut.checkExistedAcc("1", "1");
		assertTrue(rsCheck == true);
	}
	
//	/**
//	 * [IN]
//	 * userInternalID = 1
//	 * [OUT]
//	 * UserInsranceDto: internalID = 1, companyID = 1, companyName = "Luvina", fullName = "Thuan", sex = "1", birthday = "20/03/1995", insuranceNumber = "0123456789"
//	 * 					insuranceStartDate = "02/03/1995", insuranceEndDate = "02/05/1995", placeOfRegister = "HN"
//	 */
//	@Test
//	public void getDetailsInforTest() {
//		//setup
//		int userInternalID = 1;		
//		UserInsuranceDto expect = new UserInsuranceDto(1, 1, "Luvina", "Thuan", "Nam", "20/03/1995", "0123456789", "12/03/1995", "15/03/1995", "HN");
//		
//		//exercise
//		UserInsuranceDto actual = sut.getDetailsInfor(userInternalID);
//		
//		//verify
//		verify(userDao, times(1)).getDetailsInfor(userInternalID);
//		assertThat(actual, is(expect));
//	}
//	
//	/**
//	 * [IN]
//	 * userInternalID = 1
//	 * [OUT]
//	 * null
//	 */
//	@Test(expected = IllegalAccessException.class)
//	public void getDetailsInforTestThrowIAE() {
//		// setup
//		when(userDao.getDetailsInfor(anyInt())).thenThrow(IllegalAccessException.class);
//		
//		//exercise
//		UserInsuranceDto actual = sut.getDetailsInfor(1);
//		
//		//verify
//		verify(userDao, times(1)).getDetailsInfor(1);
//		assertNull(actual);
//	}
//	
//	/**
//	 * [IN]
//	 * userInternalID = 1
//	 * [OUT]
//	 * null
//	 */
//	@Test(expected = NoSuchMethodException.class)
//	public void getDetailsInforTestThrowNSME() {
//		// setup
//		when(userDao.getDetailsInfor(anyInt())).thenThrow(NoSuchMethodException.class);
//		
//		//exercise
//		UserInsuranceDto actual = sut.getDetailsInfor(1);
//		
//		//verify
//		verify(userDao, times(1)).getDetailsInfor(1);
//		assertNull(actual);
//	}
//	
	/**
	 * [IN]
	 * userInternalID = 1
	 * [OUT]
	 * true
	 */
	@Test
	public void checkExistUserTest() {
		// setup
		when(userDao.findByUserInternalID(anyInt())).thenReturn(new UserBean());
		
		//exercise
		boolean rsCheck = sut.checkExistUser(1);
		
		//verify
		verify(userDao, times(1)).findByUserInternalID(anyInt());
		assertTrue(rsCheck);
	}
	
	/**
	 * [IN]
	 * UserInsuranceDto: insuranceNumber = "5125484512", fullName = "Thuan", birthday = "20/03/1995", companyInternalID = 1,
	 * 					 placeOfRegister = "HN", insuranceStartDate = "20/03/1995", insuranceEndDate = "20/05/1995"
	 * AccountDto: userName = "1", password = "1"
	 * [OUT]
	 * true
	 */
	@Test
	public void insertOrUpdateUserExistedComTest() {
		// setup
		when(userDao.insertOrUpdateUser(anyObject(), anyObject(), anyObject())).thenReturn(true);
		when(companyDao.findByCompanyInternalId(anyInt())).thenReturn(new CompanyBean());
		AccountDto account = new AccountDto("1", "1");
		UserInsuranceDto userInsuranceDto = new UserInsuranceDto();
		userInsuranceDto.setFullName("Thuan");
		userInsuranceDto.setInsuranceNumber("5125484512");
		userInsuranceDto.setBirthday("20/03/1995");
		userInsuranceDto.setCompanyInternalID(2);
		userInsuranceDto.setPlaceOfRegister("HN");
		userInsuranceDto.setInsuranceStartDate("20/03/1995");
		userInsuranceDto.setInsuranceEndDate("20/05/1995");
		
		// exercise
		Boolean rsInsert = sut.insertOrUpdateUser(userInsuranceDto, account);
		
		// verify
		verify(userDao, times(1)).insertOrUpdateUser(anyObject(), anyObject(), anyObject());
		verify(companyDao, times(1)).findByCompanyInternalId(anyInt());
		assertTrue(rsInsert);
	}
	
	/**
	 * [IN]
	 * UserInsuranceDto: insuranceNumber = "5125484512", fullName = "Thuan", birthday = "20/03/1995", companyInternalID = 1,
	 * 					 placeOfRegister = "HN", insuranceStartDate = "20/03/1995", insuranceEndDate = "20/05/1995"
	 * AccountDto: userName = "1", password = "1"
	 * [OUT]
	 * true
	 */
	@Test
	public void insertOrUpdateUserNotExistedComTest() {
		// setup
		when(userDao.insertOrUpdateUser(anyObject(), anyObject(), anyObject())).thenReturn(true);
		when(companyDao.findByCompanyInternalId(anyInt())).thenReturn(null);
		AccountDto account = new AccountDto("1", "1");
		UserInsuranceDto userInsuranceDto = new UserInsuranceDto();
		userInsuranceDto.setFullName("Thuan");
		userInsuranceDto.setInsuranceNumber("5125484512");
		userInsuranceDto.setBirthday("20/03/1995");
		userInsuranceDto.setCompanyInternalID(2);
		userInsuranceDto.setPlaceOfRegister("HN");
		userInsuranceDto.setInsuranceStartDate("20/03/1995");
		userInsuranceDto.setInsuranceEndDate("20/05/1995");
		
		// exercise
		Boolean rsInsert = sut.insertOrUpdateUser(userInsuranceDto, account);
		
		// verify
		verify(userDao, times(1)).insertOrUpdateUser(anyObject(), anyObject(), anyObject());
		verify(companyDao, times(1)).findByCompanyInternalId(anyInt());
		assertTrue(rsInsert);
	}
}