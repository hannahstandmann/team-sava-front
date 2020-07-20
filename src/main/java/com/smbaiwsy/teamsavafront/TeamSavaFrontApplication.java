package com.smbaiwsy.teamsavafront;

import com.smbaiwsy.teamsavafront.filter.LoggingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
public class TeamSavaFrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamSavaFrontApplication.class, args);
    }

    @Bean
    public LoggingFilter simpleFilter() {
        return new LoggingFilter();
    }

}
