package com.hubject.common.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;


@Slf4j
@Aspect
@Component
public class AccessLog {

    private static final String LOCALHOST_IP = "127.0.0.1";
    private static final String COMMA = ",";
    private static final int MAX_IP_LENGTH = 15;

    /**
     * 打印日志与响应时间
     */
    @Around("within(com.hubject.*.controller.*)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        long curTime = System.currentTimeMillis();

        ServletRequestAttributes req = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String method = pjp.getTarget().getClass().getName() + "." + pjp.getSignature().getName();

        log.info("请求IP: {}, 调用接口: {}, 请求参数: {}",
                req !=null ?AccessLog.getClientHost(req.getRequest()): "unknown",
                method, JSON.toJSONString(pjp.getArgs()));
        Object result = pjp.proceed();
        log.info("调用接口: {}, 返回值: {}, 执行耗时: {} ms", method, JSON.toJSONString(result), System.currentTimeMillis() - curTime);
        return result;
    }


    /**
     * 获取客户端IP
     */
    private static String getClientHost(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (LOCALHOST_IP.equals(ip)) {
            InetAddress inet;
            try {
                inet = InetAddress.getLocalHost();
                ip = inet.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        if (ip != null && ip.length() > MAX_IP_LENGTH) {
            if (ip.indexOf(COMMA) > 0) {
                ip = ip.substring(0, ip.indexOf(COMMA));
            }
        }
        return ip;
    }
}
