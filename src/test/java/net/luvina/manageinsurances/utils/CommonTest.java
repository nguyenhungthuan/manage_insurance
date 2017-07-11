/**
 * Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 * CommonTest.java, Jul 5, 2017, 2017 nguyenhungthuan
 */
package net.luvina.manageinsurances.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
	
	/**
	 * [IN]
	 * date = "20/03/1995"
	 * [OUT]
	 * 1
	 */
	@Test
	public void testCheckExistDate() {
		// setup
		String date = "20/03/1995";
		
		// exercise
		int actual = Common.checkExistDate(date);
		
		// verify
		assertEquals(1, actual);
	}
	
	/**
	 * [IN]
	 * date = "20/03"
	 * [OUT]
	 * 2
	 */
	@Test
	public void testCheckExistDateInvalid() {
		// setup
		String date = "20/03";
		
		// exercise
		int actual = Common.checkExistDate(date);
		
		// verify
		assertEquals(2, actual);
	}
	
	/**
	 * [IN]
	 * date = "32/03/1995"
	 * [OUT]
	 * 0
	 */
	@Test
	public void checkExistDate() {
		// setup
		String date = "32/03/1995";
		
		// exercise
		int actual = Common.checkExistDate(date);
		
		// verify
		assertEquals(0, actual);
	}
	
	/**
	 * [IN]
	 * name = "Nguyễn Hưng Thuận"
	 * [OUT]
	 * Nguyen Hung Thuan
	 */
	@Test
	public void testConvertName() {
		// setup 
		String name = "Nguyễn Hưng Thuận";
		String expect = "Nguyen Hung Thuan";
		
		// exercise
		String actual = Common.convertName(name);
		
		// verify
		assertThat(actual, is(expect));
	}
	
	/**
	 * [IN]
	 * date1 = "20/03/1995"
	 * date2 = "22/03/1995"
	 * [OUT]
	 * true
	 */
	@Test
	public void testCompareDate() {
		// setup
		String date1 = "20/03/1995";
		String date2 = "22/03/1995";
		
		// exercise
		Boolean rsCompare = Common.compareDate(date1, date2);
		
		// verify
		assertTrue(rsCompare);
	}
	
	/**
	 * [IN]
	 * date1 = "20/03/1995"
	 * date2 = "20/03/1995"
	 * [OUT]
	 * true
	 */
	@Test
	public void testCompareDateFailure() {
		// setup
		String date1 = "20/03/1995";
		String date2 = "20/03/1995";
		
		// exercise
		Boolean rsCompare = Common.compareDate(date1, date2);
		
		// verify
		assertFalse(rsCompare);
	}
}