package com.tanglx.wechat.interceptor;//package com.shkingyu.basic.interceptor;
//
//import com.alibaba.fastjson.JSON;
//import com.shkingyu.basic.common.domain.SysLog;
//import com.shkingyu.basic.common.util.EntityUtils;
//import com.shkingyu.basic.service.SysLogService;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @Describe
// * @Author tanglingxiao
// * @Date 2022-06-10
// */
//@Component
//@Aspect
//@Slf4j
//@Order(-98)
//public class RequestAspect {
//
//    @Resource
//    private SysLogService sysLogService;
//
//    @Pointcut("execution(public * com.shkingyu.basic.api.*Controller.*(..))")
//    public void pointcut() {
//
//    }
//
//    @Around("pointcut()")
//    public Object handle(ProceedingJoinPoint joinPoint) {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//
//        //IP地址
//        String ipAddress = getRemoteHost(request);
//        String url = request.getRequestURL().toString();
//        String reqParam = preHandle(joinPoint);
//        log.info("请求IP:【{}】,请求URL:【{}】,请求参数:【{}】", ipAddress, url, reqParam);
//        long begin = System.currentTimeMillis();
//        Object result = "";
//        try {
//            result = joinPoint.proceed();
//        } catch (Throwable e) {
//            throw new RuntimeException(e);
//        } finally {
//            String respParam = postHandle(result);
//            log.info("请求IP:【{}】, 耗时：【{}ms】,请求URL:【{}】,返回参数:【{}】", ipAddress, (System.currentTimeMillis() - begin), url, respParam);
//            SysLog sysLog = new SysLog();
//            sysLog.setUrl(url).setRequest(reqParam).setResponse(respParam).setIp(ipAddress).setTime((System.currentTimeMillis() - begin) + "").setMethod(request.getMethod());
//            EntityUtils.setCreateInfo(sysLog);
//            new Thread(() -> sysLogService.save(sysLog)).start();
//        }
//        return result;
//    }
//
//    /**
//     * 入参数据
//     *
//     * @param joinPoint
//     */
//    private String preHandle(ProceedingJoinPoint joinPoint) {
//        Object[] args = joinPoint.getArgs();//获取所有参数包括body和Parameter
//        String reqParam = "";
//        for (Object arg : args) {
//            //            reqParam = reqParam + JSON.toJSONString(arg);
//        }
//        return reqParam;
//    }
//
//    /**
//     * 返回数据
//     *
//     * @param retVal
//     * @return
//     */
//    private String postHandle(Object retVal) {
//        if (null == retVal) {
//            return "";
//        }
//        return JSON.toJSONString(retVal);
//    }
//
//
//    /**
//     * 获取目标主机的ip
//     *
//     * @param request
//     * @return
//     */
//    private String getRemoteHost(HttpServletRequest request) {
//        String ip = request.getHeader("x-forwarded-for");
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
//    }
//}
