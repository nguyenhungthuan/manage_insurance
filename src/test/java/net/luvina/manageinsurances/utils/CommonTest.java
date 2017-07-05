/**
 * Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 * CommonTest.java, Jul 5, 2017, 2017 nguyenhungthuan
 */
package net.luvina.manageinsurances.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

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
	 * currentPage: trang hiện tại
	 * totalPage: tổng số trang
	 * [OUT]
	 * trang hiện tại đã chuyển đổi
	 */
	@Test
	public void testCurrentPageBiggerThanTotalPage() {
		// setup
		int expect = 10, currentPage = 1000, totalPage = 10;
		
		//exercise
		int actual = Common.exchangeCurrentPage(currentPage, totalPage);
		
		//verify
		assertEquals(expect, actual);
	}
	
	/**
	 * [IN]
	 * currentPage: trang hiện tại
	 * totalPage: tổng số trang
	 * [OUT]
	 * trang hiện tại đã chuyển đổi
	 */
	@Test
	public void testCurrentPageLessThanTotalPage() {
		// setup
		int expect = 1, currentPage = -10, totalPage = 10;
		
		//exercise
		int actual = Common.exchangeCurrentPage(currentPage, totalPage);
		
		//verify
		assertEquals(expect, actual);
	}
	
	/**
	 * [IN]
	 * currentPage: trang hiện tại
	 * totalPage: tổng số trang
	 * limit: số bản ghi trên 1 trang
	 * [OUT]
	 * danh sách trang hiển thị
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
	 * currentPage: trang hiện tại
	 * totalPage: tổng số trang
	 * limit: số bản ghi trên 1 trang
	 * [OUT]
	 * danh sách trang hiển thị
	 */
	@Test
	public void getListPagingFailure() {
		//setup
		int totalRecord = 100, limit = 5, currentPage = 15;
		List<Integer> list = Arrays.asList(1,2,3,4);
		
		//exercise
		List<Integer> actual = Common.getListPaging(totalRecord, limit, currentPage); 
		
		//verify
		assertThat(actual, is(list));
	}
}
