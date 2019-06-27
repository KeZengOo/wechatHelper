package com.nuoxin.virtual.rep.api;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.Api;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@EnableAsync
@EnableScheduling
@MapperScan(basePackages = "com.nuoxin.virtual.rep.api.mybatis")
public class VirtualRepApiApplication {
	
	@Autowired
	private TypeResolver typeResolver;
	
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	/**
	 * 生成API文档的入口
	 */
	@Bean
	public Docket generateApi() {
		Docket docket = new Docket( DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
				.paths(PathSelectors.any()).build().pathMapping("/")
				.directModelSubstitute(LocalDate.class, String.class)
				.genericModelSubstitutes(ResponseEntity.class)
				.alternateTypeRules(newRule(typeResolver.resolve(
						DeferredResult.class,
						typeResolver.resolve(
								ResponseEntity.class,
					 		WildcardType.class
						)
				), typeResolver.resolve(WildcardType.class)))
				.useDefaultResponseMessages(false);
		return docket;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(VirtualRepApiApplication.class, args);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ApiInfo apiInfo() {
		return new ApiInfo("系统接口", "virtual-rep-api  系统接口", "0.0.1", "", "", "", "");
	}

}
