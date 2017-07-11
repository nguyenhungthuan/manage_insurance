/**
 * Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 * LoginInterceptor.java, Jul 10, 2017, 2017 nguyenhungthuan
 */
package net.luvina.manageinsurances.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import net.luvina.manageinsurances.controller.formbean.AccountFormBean;

/**
 * @author nguyenhungthuan
 *
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(request.getRequestURI().contains(".do") ||
		   request.getRequestURI().contains("ViewDetails/") ||
		   request.getRequestURI().contains("Update/")) {
			AccountFormBean account = (AccountFormBean) request.getSession().getAttribute("accountSess");
			if(account == null) {
				response.sendRedirect("/");
				return false;
			}
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {	
	}	
}