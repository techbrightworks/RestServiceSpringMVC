
package org.srinivas.siteworks.webconfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

@Configuration
public class ChangeViewConfiguration {
    private static final Logger logger = LogManager.getLogger ( ChangeViewConfiguration.class );

    @Bean
    public ViewResolver viewResolver() {
        logger.info ( "ChangeViewConfiguration viewResolver()" );
        return new ContentNegotiatingViewResolver ();
    }

}
