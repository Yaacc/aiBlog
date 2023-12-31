package cn.ndky.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    private static final long MAX_AGE = 24*60*60;
    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:8080");    //设置前端访问源地址
        corsConfiguration.addAllowedHeader("*");    //设置访问源请求头
        corsConfiguration.addAllowedMethod("*");    //设置访问源请求方法
        source.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsFilter(source);
    }
}