//package com.tanglx.wechat.interceptor;
//
//import com.auth0.jwt.exceptions.AlgorithmMismatchException;
//import com.auth0.jwt.exceptions.TokenExpiredException;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.google.common.collect.Maps;
//import com.shkingyu.basic.common.acs.ACS;
//import com.shkingyu.basic.common.constant.BaseErrors;
//import com.shkingyu.basic.common.constant.Constants;
//import com.shkingyu.basic.common.exception.BaseException;
//import com.shkingyu.basic.common.util.Base64Util;
//import com.shkingyu.basic.common.util.JwtUtils;
//import com.shkingyu.basic.common.util.StringUtil;
//import com.shkingyu.basic.service.RedisService;
//import lombok.extern.java.Log;
//import org.apache.tomcat.util.http.MimeHeaders;
//import org.springframework.stereotype.Component;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//
///**
// * 拦截器
// *
// * @Author tanglingxiao
// * @Date 2022-06-09
// */
//@Log
//@Component
//public class AccessControlInterceptor implements HandlerInterceptor {
//
//    @Resource
//    private RedisService redisService;
//
//    private static final List<String> NOT_LOGIN_RESOURCES = new ArrayList<String>() {
//        private static final long serialVersionUID = 1L;
//
//        {
//            // swagger相关资源不需要登录
//            add("/swagger-ui.html");
//            add("/configuration");
//            add("/swagger-resources");
//            add("/ctrl-docs");
//            add("/v2/ctrl-docs");
//            add("/v2/api-docs");
//            add("/error");
//            add("/webjars");
//            add("/doc.html");
//        }
//    };
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        // 不需要进行访问控制的资源过滤
//        String uri = request.getRequestURI();
//        for (String resource : NOT_LOGIN_RESOURCES) {
//            if (uri.startsWith(resource)) {
//                return true;
//            }
//        }
//        return hasAccessPermission(handler, request);
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
//    }
//
//    /**
//     * 是否有权限访问
//     */
//    private boolean hasAccessPermission(Object handler, HttpServletRequest request) {
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            ACS acs = handlerMethod.getMethodAnnotation(ACS.class);
//            // 判断是否允许匿名访问
//            if (acs != null && acs.allowAnonymous()) {
//                return true;
//            }
//            //判断用户是否登录
//            if (acs != null) {
//                try {
//                    String token = getToken(request);
//                    if (StringUtil.isBlank(token)) {
//                        token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJvcmdhbml6YXRpb25JZCI6IiIsIm9yZ2FuaXphdGlvblBhdGgiOiIiLCJleHAiOjE2NjE5OTk1NzYsInVzZXJJZCI6IjEiLCJhY2NvdW50IjoiYWRtaW4iLCJ1c2VybmFtZSI6ImFkbWluIn0.6ACP3K49_s-R6V4SvE9ajgxaiHdAKp9TGOJn-kGRVkc";
//                    }
//                    if (!token.startsWith(Constants.SYS_USER_KEY)) {
//                        log.warning("token格式错误");
//                        throw new BaseException(BaseErrors.SYSTEM_NOT_LOGIN);
//                    }
//                    token = token.replaceFirst(Constants.SYS_USER_KEY, "");
//                    // TODO: 2022/7/7 用户redis
//
//                    //                    //redis
//                    //                    Object o = redisService.get(token);
//                    //                    if (o == null) {
//                    //                        throw new BaseException(BaseErrors.SYSTEM_NOT_LOGIN);
//                    //                    }
//                    //                    SysUserTokenInfo sysUserTokenInfo = JSON.parseObject(o.toString(), SysUserTokenInfo.class);
//
//                    DecodedJWT decodedJWT = JwtUtils.verifyToken(token);
//                    String payload = decodedJWT.getPayload();
//                    String tokenUserInfo;
//                    try {
//                        tokenUserInfo = new String(Base64Util.decryptBASE64(payload));
//                    } catch (Exception e) {
//                        throw new BaseException(BaseErrors.SYSTEM_NOT_LOGIN);
//                    }
//                    Map<String, Object> headerMap = Maps.newHashMap();
//                    headerMap.put(Constants.HEADER_USERINFO, tokenUserInfo);
//                    //将SysUserInfoResponse类信息放入header
//                    reflectSetParam(request, headerMap);
//                    return true;
//                } catch (TokenExpiredException tokenExpiredException) {
//                    log.warning("token过期");
//                    throw new BaseException(BaseErrors.SYSTEM_NOT_LOGIN);
//                } catch (AlgorithmMismatchException algorithmMismatchException) {
//                    log.warning("token算法不一致");
//                    throw new BaseException(BaseErrors.SYSTEM_NOT_LOGIN);
//                } catch (Exception e) {
//                    log.warning("token无效");
//                    throw new BaseException(BaseErrors.SYSTEM_NOT_LOGIN);
//                }
//            }
//        }
//        return false;
//    }
//
//    private String getToken(HttpServletRequest request) {
//        String sessionId = request.getHeader(Constants.ACCESS_DEFAULT_TOKEN_HEADER_NAME);
//        return sessionId;
//    }
//
//    private void reflectSetParam(HttpServletRequest request, Map<String, Object> headerMap) {
//        Class<? extends HttpServletRequest> requestClass = request.getClass();
//        try {
//            Field request1 = requestClass.getDeclaredField("request");
//            request1.setAccessible(true);
//            Object o = request1.get(request);
//            Field coyoteRequest = o.getClass().getDeclaredField("coyoteRequest");
//            coyoteRequest.setAccessible(true);
//            Object o1 = coyoteRequest.get(o);
//            Field headers = o1.getClass().getDeclaredField("headers");
//            headers.setAccessible(true);
//            MimeHeaders o2 = (MimeHeaders) headers.get(o1);
//            headerMap.forEach((k, v) -> o2.addValue(k).setString(v.toString()));
//            //            o2.addValue(key).setString(value);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
