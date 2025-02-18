package com.wegroceries.wegroceriesapi.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<MyCustomFilter> loggingFilter() {
        FilterRegistrationBean<MyCustomFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MyCustomFilter());
        registrationBean.addUrlPatterns("/api/*"); // Specify URL patterns to apply the filter to
        return registrationBean;
    }

    @Bean
public JwtTokenFilter jwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
    return new JwtTokenFilter(jwtTokenProvider);
}
}

