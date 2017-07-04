package net.luvina.manageinsurances.logic.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.luvina.manageinsurances.dao.CompanyDao;
import net.luvina.manageinsurances.entities.CompanyBean;
import net.luvina.manageinsurances.logic.impl.dto.CompanyDto;

@RunWith(MockitoJUnitRunner.class)
public class CompanyLogicImplTest {
	
	@InjectMocks
	private CompanyLogicImpl  companyLogicImpl;
	
	@Mock
	private CompanyDao companyDao;
	
	@Before
	public void setUp() {
		List<CompanyBean> listCompany = new ArrayList<>();
		CompanyBean companyBean1 = new CompanyBean(1, "Luvina");
		CompanyBean companyBean2 = new CompanyBean(2, "HQV");
		listCompany.add(companyBean1);
		listCompany.add(companyBean2);
		
		when(companyDao.findAll()).thenReturn(listCompany);
	}
	
	@Test
	public void getAllCom() {
		List<CompanyDto> listCompany = companyLogicImpl.getAllCom();
		assertThat(listCompany, containsInAnyOrder(new CompanyDto(1, "Luvina"), new CompanyDto(2, "HQV")));
	}	
}
