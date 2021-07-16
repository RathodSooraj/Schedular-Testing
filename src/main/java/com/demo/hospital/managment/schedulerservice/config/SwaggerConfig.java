package com.demo.hospital.managment.schedulerservice.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	public static final Contact DEFAULT_CONTACT = new Contact("Suraj Rathod", "http://www.citiustech.com",
			"Suraj.Rathod@citiustech.com");

	private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Scheduling API Documentation",
			"Scheduling Api Documentation Description", "Version 1.0", "urn:tos", DEFAULT_CONTACT, "Apache 2.0",
			"http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());

	private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<String>(
			Arrays.asList("application/json", "application/xml"));

	// Docket API
	@Bean
	public Docket api() {
		// Returns a prepared Docket instance
		return new Docket(DocumentationType.SWAGGER_2).select() // ApiSelectorBuilder
				// .paths(PathSelectors.ant("/api/*"))
				.apis(RequestHandlerSelectors.basePackage("com.demo.hospital.managment.schedulerservice")).build()
				.apiInfo(DEFAULT_API_INFO).produces(DEFAULT_PRODUCES_AND_CONSUMES)
				.consumes(DEFAULT_PRODUCES_AND_CONSUMES);
	}
}

/*
 * //endpoints - localhost:5011/v2/api-docs - json format //endpoints -
 * localhost:5011/swagger-ui/index.html
 * 
 * websecurityconfig http.authorizeRequests().antMatchers("/v2/api-docs",
 * "/configuration/ui", "/swagger-resources/**", "/configuration/security",
 * "/swagger-ui.html", "/webjars/**").permitAll();
 * 
 */
