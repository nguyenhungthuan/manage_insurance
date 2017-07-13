package net.luvina.manageinsurances.logic.impl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
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

import net.luvina.manageinsurances.dao.CompanyDao;
import net.luvina.manageinsurances.entities.CompanyBean;
import net.luvina.manageinsurances.logic.impl.dto.CompanyDto;

@RunWith(MockitoJUnitRunner.class)
public class CompanyLogicImplTest {
	
	@InjectMocks
	private CompanyLogicImpl  sut;
	
	@Mock
	private CompanyDao companyDao;
	
	@Before
	public void setUp() {
		
		when(companyDao.findAll()).thenAnswer(new Answer<List<CompanyBean>>() {

			@Override
			public List<CompanyBean> answer(InvocationOnMock invocation) throws Throwable {
				List<CompanyBean> listCompany = new ArrayList<>();
				CompanyBean companyBean1 = new CompanyBean(1, "Luvina");
				CompanyBean companyBean2 = new CompanyBean(2, "HQV");
				listCompany.add(companyBean1);
				listCompany.add(companyBean2);
				return listCompany;
			}
		});
		
		when(companyDao.findByCompanyInternalId(anyInt())).thenReturn(new CompanyBean());
	}
	
	/**
	 * [OUT]
	 * true
	 */
	@Test
	public void getAllCom() {
		// setup
		List<CompanyDto> listCompanyDto = new ArrayList<>();
		listCompanyDto.add(new CompanyDto(1, "Luvina"));
		listCompanyDto.add(new CompanyDto(2, "HQV"));
		
		// exercise
		List<CompanyDto> listCompany = sut.getAllCom();
		
		//verify
		assertTrue(listCompany.equals(listCompanyDto));
	}
	
	/**
	 * [IN]
	 * companyInternalID = 1
	 * [OUT]
	 * true
	 */
	@Test
	public void checkExistCompany() {
		// exercise
		Boolean rsCheck = sut.checkExistCompany(1);
		
		// verify
		assertTrue(rsCheck);
	}
	
	/**
	 * [IN]
	 * companyInternalID = 1
	 * [OUT]
	 * false
	 */
	@Test
	public void checkExistCompanyFailure() {
		// setup
		when(companyDao.findByCompanyInternalId(anyInt())).thenReturn(null);
		
		// exercise
		Boolean rsCheck = sut.checkExistCompany(1);
		
		// verify
		assertFalse(rsCheck);
	}
	
	/**
	 * [IN]
	 * email = "example@gmail.com
	 * [OUT]
	 * true
	 */
	@Test
	public void checkExistEmail() {
		// setup
		when(companyDao.findByEmail(anyString())).thenReturn(new CompanyBean());
		
		// exercise
		Boolean rsCheck = sut.checkExistEmail("example@gmail.com");
		
		// verify
		assertTrue(rsCheck);
	}
	
	/**
	 * [IN]
	 * tel = "0903320395"
	 * [OUT]
	 * true
	 */
	@Test
	public void checkExistTel() {
		// setup
		when(companyDao.findByTel(anyString())).thenReturn(new CompanyBean());
		
		// exercise
		Boolean rsCheck = sut.checkExistTel("0903320395");
		
		// verify
		assertTrue(rsCheck);
	}
}
