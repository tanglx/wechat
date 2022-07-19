//package com.tanglx.wechat.interceptor;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import javax.annotation.Resource;
//
///**
// * @Describe Spring MVC 的辅助配置, 用来注册拦截器.
// * @Author tanglingxiao
// * @Date 2022-05-25
// */
//@Configuration
//public class DefaultWebMvcConfigurerAdapter implements WebMvcConfigurer {
//    @Resource
//    private ApplicationContext applicationContext;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 访问权限控制
//        AccessControlInterceptor accessControlInterceptor = applicationContext.getBean(AccessControlInterceptor.class);
//        registry.addInterceptor(accessControlInterceptor);
//
//    }
//}
