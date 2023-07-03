package org.eclipse.shoply.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("org.eclipse.shoply")
@EnableJpaRepositories("org.eclipse.shoply.repository")
@ComponentScan(basePackages = {"org.eclipse.shoply","org.eclipse.shoply.app","org.eclipse.shoply.enumeration","org.eclipse.shoply.model","org.eclipse.shoply.repository","org.eclipse.shoply.security","org.eclipse.shoply.support","org.eclipse.shoply.web.controller","org.eclipse.shoply.web.dto","org.eclipse.shoply.service","org.eclipse.shoply.service.impl"})
@SpringBootApplication( scanBasePackages = {"org.eclipse.shoply","org.eclipse.shoply.app","org.eclipse.shoply.enumeration","org.eclipse.shoply.model","org.eclipse.shoply.repository","org.eclipse.shoply.security","org.eclipse.shoply.support","org.eclipse.shoply.web.controller","org.eclipse.shoply.web.dto","org.eclipse.shoply.service","org.eclipse.shoply.service.impl"})
public class ShoplyApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		 SpringApplication.run(ShoplyApplication.class, args);
	}

}
