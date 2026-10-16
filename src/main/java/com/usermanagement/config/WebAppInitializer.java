
package com.usermanagement.config;

import com.usermanagement.security.CurrentUserFilter;
import org.springframework.web.servlet.support
        .AbstractAnnotationConfigDispatcherServletInitializer;

import jakarta.servlet.Filter;

public class WebAppInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                RootConfig.class,
                PersistenceConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                WebConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
    @Override
    protected Filter[] getServletFilters(){
        return new Filter[] {new CurrentUserFilter()};
    }
}
