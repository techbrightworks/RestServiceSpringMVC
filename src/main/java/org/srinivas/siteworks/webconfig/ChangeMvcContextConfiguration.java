/**
 * @author SrinivasJasti
 * 
 */
package org.srinivas.siteworks.webconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * The Class ChangeMvcContextConfiguration.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "org.srinivas.siteworks" })
public class ChangeMvcContextConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
		configurer.enable();

	}

}
