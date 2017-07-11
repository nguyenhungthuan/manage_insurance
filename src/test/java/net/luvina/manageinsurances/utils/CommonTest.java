/**
 * Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 * CommonTest.java, Jul 5, 2017, 2017 nguyenhungthuan
 */
package net.luvina.manageinsurances.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * @author nguyenhungthuan
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CommonTest {
	
	/**
	 * [IN]
	 * currentPage: 1000
	 * totalPage: 10
	 * [OUT]
	 * currentPage = 10
	 */
	@Test
	public void testCurrentPageBiggerThanTotalPage() {
		// setup
		int currentPage = 1000, totalPage = 10;
		
		//exercise
		int actual = Common.exchangeCurrentPage(currentPage, totalPage);
		
		//verify
		assertEquals(actual, 10);
	}
	
	/**
	 * [IN]
	 * currentPage: -10
	 * totalPage: 10
	 * [OUT]
	 * currentPage = 1
	 */
	@Test
	public void testCurrentPageLessThanTotalPage() {
		// setup
		int currentPage = -10, totalPage = 10;
		
		//exercise
		int actual = Common.exchangeCurrentPage(currentPage, totalPage);
		
		//verify
		assertEquals(actual, 1);
	}
	
	/**
	 * [IN]
	 * currentPage: 4
	 * totalRecord: 20
	 * limit: 5
	 * [OUT]
	 * expect = [1,2,3,4]
	 */
	@Test
	public void getListPagingTrue() {
		//setup
		int totalRecord = 20, limit = 5, currentPage = 4;
		List<Integer> expect = Arrays.asList(1,2,3,4);
		
		//exercise
		List<Integer> actual = Common.getListPaging(totalRecord, limit, currentPage); 
		
		//verify
		assertThat(actual, is(expect));
	}
	
	/**
	 * [IN]
	 * currentPage: 15
	 * totalRecord: 100
	 * limit: 5
	 * [OUT]
	 * expect = [13,14,15,16,17]
	 */
	@Test
	public void getListPagingFailure() {
		//setup
		int totalRecord = 100, limit = 5, currentPage = 15;
		List<Integer> list = Arrays.asList(1,2,3,4);
		
		//exercise
		List<Integer> actual = Common.getListPaging(totalRecord, limit, currentPage); 
		
		//verify
		assertThat(actual, not(list));
	}
	
	/**
	 * [IN]
	 * date = "2017-03-15"
	 * [OUT]
	 * 15/03/2017
	 */
	@Test
	public void testFormatDate() {
		// setup
		String date = "2017-03-15";
		String expect = "15/03/2017";
		
		// exercise
		String convertedDate = Common.convertFormatDate(date);
		
		// verify
		assertThat(convertedDate, is(expect));
	}
}