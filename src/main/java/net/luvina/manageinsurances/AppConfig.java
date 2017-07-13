/**
 * Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 * AppConfig.java, Jul 10, 2017, 2017 nguyenhungthuan
 */
package net.luvina.manageinsurances;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import net.luvina.manageinsurances.interceptor.LoginInterceptor;

/**
 * @author nguyenhungthuan
 *
 */
@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {
	@Autowired
	LoginInterceptor loginInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor);
	}
}