package ru.systemairac.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CalculatorApplication implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/styles/css/**")
				.addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/img/**")
				.addResourceLocations("classpath:/static/img/");
		registry.addResourceHandler("/js/**")
				.addResourceLocations("classpath:/static/js/");
	}

	public static void main(String[] args) {
		SpringApplication.run(CalculatorApplication.class, args);
	}

}
