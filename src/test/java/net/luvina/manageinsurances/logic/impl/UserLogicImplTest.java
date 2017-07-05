package net.luvina.manageinsurances.logic.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
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
		boolean rsCheck = userLogicImpl.checkExistedAcc("1", "1");
		assertTrue(rsCheck == true);
	}
	
	/**
	 * Test method paging - getListPaging(int totalRecords, int limit, int currentPage)
	 * @param totalRecords tổng số bản ghi
	 * @param limit số bản ghi trên 1 trang
	 * @param currentPage trang hiện tại
	 * @return List<Integer> danh sách các trang sẽ hiển thị
	 */
	@Test
	public void testPaging() {
		List<Integer> listPaging = Common.getListPaging(10, 1, 4);
		List<Integer> list = Arrays.asList(2,3,4,5,6);
		assertThat(listPaging, is(list));
	}
	
	/**
	 * Test method paging - getListPaging(int totalRecords, int limit, int currentPage)
	 * @param totalRecords tổng số bản ghi
	 * @param limit số bản ghi trên 1 trang
	 * @param currentPage trang hiện tại
	 * @return List<Integer> danh sách các trang sẽ hiển thị
	 */
	@Test
	public void testPagingFailure() {
		List<Integer> listPaging = Common.getListPaging(20, 5, 3);
		List<Integer> list = Arrays.asList(2,3,4,5,6);
		assertThat(listPaging, is(list));
	}
	
	/**
	 * Test method paging - getListPaging(int totalRecords, int limit, int currentPage)
	 * @param totalRecords tổng số bản ghi
	 * @param limit số bản ghi trên 1 trang
	 * @param currentPage trang hiện tại
	 * @return List<Integer> danh sách các trang sẽ hiển thị
	 */
	@Test
	public void testPagingTrue() {
		List<Integer> listPaging = Common.getListPaging(20, 5, 3);
		List<Integer> list = Arrays.asList(1,2,3,4);
		assertThat(listPaging, is(list));
	}
}