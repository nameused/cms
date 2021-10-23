package com.github.cms.component;

import com.github.cms.entity.SystemLog;
import com.github.cms.entity.WebLog;
import com.github.cms.service.SystemLogService;
import com.github.cms.util.JsonUtil;
import com.github.cms.util.JwtTokenUtil;
import com.github.cms.util.RequestUtil;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.SQLException;
import java.util.*;
/**
 * @author zhangmingyang
 * @Date: 2021/10/18
 */
@Aspect
@Component
public class SysLogAspect {
    private static final Logger log = LoggerFactory.getLogger(SysLogAspect.class);
    private ThreadLocal<Long> startTime = new ThreadLocal<>();
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    SystemLogService systemLogService;

    /**
     * 切入点
     */
    @Pointcut(value = "@annotation(com.github.cms.component.SysLog)")
    private void pointcut() {
    }


    /**
     * 方法执行签
     *
     * @param sysLog
     */
    @Before(value = "pointcut() && @annotation(sysLog)")
    public void before(SysLog sysLog) {
        startTime.set(System.currentTimeMillis());
        log.info("********************执行了before方法************************");
    }


    @AfterReturning(value = "pointcut() && @annotation(sysLog)) ", returning = "result")
    public Object afterReturning(JoinPoint joinPoint, SysLog sysLog, Object result) {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        return null;
    }


    @Around(value = "pointcut() && @annotation(sysLog)")
    public Object aroundAdvice(ProceedingJoinPoint pjp, SysLog sysLog) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        WebLog webLog = new WebLog();
        String authHeader = request.getHeader(this.tokenHeader);
        String operator=null;
        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
            // The part after "Bearer "
            String authToken = authHeader.substring(this.tokenHead.length());
            operator = jwtTokenUtil.getUserNameFromToken(authToken);
            log.info("operator值为:" + operator);

        }
        String operateType = sysLog.operateType();
        String operateContent = sysLog.operateContent();
        SystemLog systemLog = new SystemLog();
        systemLog.setOperateType(operateType);
        systemLog.setOperateDate(new Date());
        systemLog.setOperator(operator);
        systemLog.setOperateContent(operateContent);


        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation log = method.getAnnotation(ApiOperation.class);
            webLog.setDescription(log.value());
        }
        long endTime = System.currentTimeMillis();
        webLog.setBasePath(RequestUtil.getBasePath(request));
        webLog.setIp(getIPAddress(request));
        webLog.setMethod(request.getMethod());
        webLog.setParameter(getParameter(method, pjp.getArgs()));
        webLog.setUsername(operator);
        //webLog.setSpendTime((int) (endTime - startTime.get()));
        //webLog.setStartTime(startTime.get());
        webLog.setUri(request.getRequestURI());
        webLog.setUrl(request.getRequestURL().toString());
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("url",webLog.getUrl());
        logMap.put("method",webLog.getMethod());
        logMap.put("parameter",webLog.getParameter());
        logMap.put("spendTime",webLog.getSpendTime());
        logMap.put("username",webLog.getUsername());
        logMap.put("description",webLog.getDescription());


        Object result = null;
        try {
            //让代理方法执行
            result = pjp.proceed();
            // 2.相当于后置通知(方法成功执行之后走这里)
            systemLog.setOperateResult("正常");
            webLog.setResult("正常");

            String remak=JsonUtil.objectToJson(webLog);

            systemLog.setRemark(remak);
            systemLogService.save(systemLog);
            // 设置操作结果
        } catch (SQLException e) {
            // 3.相当于异常通知部分
            systemLog.setOperateResult("失败");
            webLog.setResult("失败");

            String remak=JsonUtil.objectToJson(webLog);

            systemLog.setRemark(remak);
            systemLogService.save(systemLog);
            // 设置操作结果
        } finally {
            // 4.相当于最终通知
            systemLog.setOperateDate(new Date());
            // 设置操作日期
            systemLogService.save(systemLog);
        }
        return result;
    }



    /**
     * 根据方法和传入的参数获取请求参数
     */
    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.size() == 0) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }

    /**
     * 获取IP地址
     * @param request
     * @return
     */

    public static String getIPAddress(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }

}
