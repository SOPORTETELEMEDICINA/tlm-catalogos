package net.amentum.niomedic.catalogos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class NioCatalogosApplication {

	public static void main(String[] args) {
		SpringApplication.run(NioCatalogosApplication.class, args);
	}

   @Bean
   public Docket docket(){
      return new Docket(DocumentationType.SWAGGER_2)
         .apiInfo(apiInfo())
         .select()
         .apis(RequestHandlerSelectors.basePackage("net.amentum.niomedic.catalogos"))
         .paths(PathSelectors.any())
         .build();
   }

   public ApiInfo apiInfo(){
      return new ApiInfoBuilder()
         .title("Microservicio de catalogos")
         .description("Obtener datos de los catalogos")
         .contact("Amentum IT Services")
         .licenseUrl("http://www.amentum.net")
         .build();
   }
}
