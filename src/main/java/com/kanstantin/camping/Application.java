package com.kanstantin.camping;

import org.dozer.BeanFactory;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.kanstantin.camping.controllers",
        "com.kanstantin.camping.services"})
@EnableJpaRepositories(basePackages = {
        "com.kanstantin.camping.domain"
})
public class Application {

    @Bean
    public Mapper mapper() {
        return new DozerBeanMapper();
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}