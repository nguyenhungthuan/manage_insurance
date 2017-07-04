package net.luvina.manageinsurances.logic.impl;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import net.luvina.manageinsurances.dao.UserDao;
import net.luvina.manageinsurances.entities.UserBean;
import net.luvina.manageinsurances.entities.UserInsuranceBean;
import net.luvina.manageinsurances.logic.impl.dto.InforSearchDto;
import net.luvina.manageinsurances.logic.impl.dto.UserInsuranceDto;
import net.luvina.manageinsurances.utils.Common;

@RunWith(MockitoJUnitRunner.class)
public class UserLogicImplTest {
	
	@InjectMocks
	private UserLogicImpl userLogicImpl;
	@Mock
	private UserDao userDao;
	
	@Before
	public void setUp() {

		// Test method checkExistedAccount
		when(userDao.findByUserNameAndPassword("1", Common.encryptMD5("1"))).thenAnswer(new Answer<List<UserBean>>() {

			@Override
			public List<UserBean> answer(InvocationOnMock invocation) throws Throwable {
				List<UserBean> listAccount = new ArrayList<>();
				listAccount.add(new UserBean());
				return listAccount;
			}
		});
		
		// Test method getListInfor
		when(userDao.getListInfor(1, "", "", "", "ASC", "u.fullName", 5, 0)).thenAnswer(new Answer<List<UserInsuranceBean>>() {

			@Override
			public List<UserInsuranceBean> answer(InvocationOnMock invocation) throws Throwable {
				List<UserInsuranceBean> listUserInsuranceBean = new ArrayList<>();
				listUserInsuranceBean.add(new UserInsuranceBean(1, "Thuan"));
				listUserInsuranceBean.add(new UserInsuranceBean(2, "Nam"));
				return listUserInsuranceBean;
			}
			
		});		
		
		// Test method getTotalRecords
		when(userDao.getTotalRecords(1, "", "", "")).thenReturn(1);
	}
	
	@Test
	public void getListInfor(){
		InforSearchDto inforSearchDto = new InforSearchDto("1","","","","ASC");
		
		List<UserInsuranceDto> list = userLogicImpl.getListInfor(inforSearchDto, "u.fullName", 5, 0);
		assertThat(list, containsInAnyOrder(new UserInsuranceDto(2, "Nam"), new UserInsuranceDto(1, "Thuan")));
	}
	
	
	@Test
	public void getTotalRecords(){
		InforSearchDto inforSearchDto = new InforSearchDto("1","","","","ASC");
		int count = userLogicImpl.getTotalRecords(inforSearchDto);
		assertEquals(1, count);
	}
	
	@Test
	public void checkExistedAccount() {
		boolean rsCheck = userLogicImpl.checkExistedAcc("1", Common.encryptMD5("1"));
		assertTrue(rsCheck == true);
	}
}