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
import org.mockito.runners.MockitoJUnitRunner;

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
		List<UserBean> listAccount = new ArrayList<>();
		listAccount.add(new UserBean());
		when(userDao.findByUserNameAndPassword("1", Common.encryptMD5("1"))).thenReturn(listAccount);
		
		List<UserInsuranceBean> listUserInsuranceBean = new ArrayList<>();
		UserInsuranceBean userInsuranceBean1 = new UserInsuranceBean();
		UserInsuranceBean userInsuranceBean2 = new UserInsuranceBean();
		userInsuranceBean1.setFullName("Thuan");
		userInsuranceBean2.setFullName("Nam");
		listUserInsuranceBean.add(userInsuranceBean1);
		listUserInsuranceBean.add(userInsuranceBean2);
		when(userDao.getListInfor(1, "", "", "", "ASC", "u.fullName", 5, 0)).thenReturn(listUserInsuranceBean);
		
		when(userDao.getTotalRecords(1, "", "", "")).thenReturn(1);
	}
	
	@Test
	public void getListInfor(){
		InforSearchDto inforSearchDto = new InforSearchDto();
		inforSearchDto.setCompanyInternalID("1");
		inforSearchDto.setFullName("");
		inforSearchDto.setInsuranceNumber("");
		inforSearchDto.setPlaceOfRegister("");
		inforSearchDto.setSortType("ASC");
		
		List<UserInsuranceDto> list = userLogicImpl.getListInfor(inforSearchDto, "u.fullName", 5, 0);
		assertThat(list, containsInAnyOrder(new UserInsuranceDto("Thuan"), new UserInsuranceDto("Nam")));
	}
	
	
	@Test
	public void getTotalRecords(){
		InforSearchDto inforSearchDto = new InforSearchDto();
		inforSearchDto.setCompanyInternalID("1");
		inforSearchDto.setFullName("");
		inforSearchDto.setInsuranceNumber("");
		inforSearchDto.setPlaceOfRegister("");
		int count = userLogicImpl.getTotalRecords(inforSearchDto);
		assertEquals(1, count);
	}
	
	@Test
	public void checkExistedAccount() {
		boolean rsCheck = userLogicImpl.checkExistedAcc("1", Common.encryptMD5("1"));
		assertTrue(rsCheck == true);
	}
}