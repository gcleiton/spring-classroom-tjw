package br.edu.ifce.springclassroomapi;

import jakarta.validation.Validator;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
public class SpringClassroomApiApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SpringClassroomApiApplication.class, args);
	}

	@Bean
	public Validator validator() {
		return new LocalValidatorFactoryBean();
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new SpecificationArgumentResolver());
	}
}
