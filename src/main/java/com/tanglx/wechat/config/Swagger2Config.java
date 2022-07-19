package com.tanglx.wechat.config;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.time.LocalDate;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * @Describe swagger配置
 * @Author tanglingxiao
 * @Date 2022-05-25
 */
@EnableSwagger2
@Configuration
public class Swagger2Config implements WebMvcConfigurer {
    @Resource
    private TypeResolver typeResolver;

    /**
     * swagger ui资源映射
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket commonDocket() {
        //        //添加head参数
        //        List<Parameter> pars = Lists.newArrayList();
        //        ParameterBuilder tokenPar = new ParameterBuilder();
        //        tokenPar.name("String")
        //                .description("登录令牌(Bearer 用户TOKEN)")
        //                .modelRef(new ModelRef("string"))
        //                .parameterType("header")
        //                .required(false)
        //                .build();
        //        pars.add(tokenPar.build());

        Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .groupName("基础数据")
                .select()
                // 标示只有被 @Api 标注的才能生成API.
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                // 遇到 LocalDate时，输出成String
                .genericModelSubstitutes(ResponseEntity.class)
                //                .globalOperationParameters(pars)
                .alternateTypeRules(newRule(typeResolver.resolve(DeferredResult.class, typeResolver.resolve(ResponseEntity.class, WildcardType.class)), typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(false);
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("基础数据").description("操作接口").contact(new Contact("唐凌潇", "", "")).build();
    }
}
