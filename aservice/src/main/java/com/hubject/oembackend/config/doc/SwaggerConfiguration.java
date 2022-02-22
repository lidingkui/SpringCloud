package com.hubject.oembackend.config.doc;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@EnableKnife4j
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfiguration {
    @Bean
    public Docket restApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hubject.oembackend.controller"))
                .paths(PathSelectors.any())
                .build();
    }

//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder().title("OAuth2认证中心")
//                .description("<div style='font-size:14px;color:red;'>OAuth2认证、注销、获取验签公钥接口</div>")
//                .termsOfServiceUrl("https://www.danir.store")
//                .contact(new Contact("yl", "https://github.com/dnir", "994242104@qq.com"))
//                .license("Open Source")
//                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
//                .version("1.0.0")
//                .build();
//    }

}


