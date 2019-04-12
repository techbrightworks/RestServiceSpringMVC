package org.srinivas.siteworks.config;


import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static com.google.common.collect.Lists.newArrayList;


@Configuration
@ComponentScan(basePackages = {"org.srinivas.siteworks"}, excludeFilters = {@Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)})
public class AppConfig {


    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer ();
        c.setIgnoreUnresolvablePlaceholders ( true );
        return c;
    }






}
