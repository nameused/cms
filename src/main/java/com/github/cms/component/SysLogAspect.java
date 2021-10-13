package com.github.cms.component;

import com.github.cms.entity.SystemLog;
import com.github.cms.service.SystemLogService;
import com.github.cms.util.JwtTokenUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Date;

@Aspect
@Component
public class SysLogAspect {
    private static final Logger log = LoggerFactory.getLogger(SysLogAspect.class);
    //private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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

        String authHeader = request.getHeader(this.tokenHeader);
        String operator=null;
        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
            // The part after "Bearer "
            String authToken = authHeader.substring(this.tokenHead.length());
            operator = jwtTokenUtil.getUserNameFromToken(authToken);
            log.info("operator值为:" + operator);

        }


//        String token = request.getHeader("Authorization");
//        log.info("token值为:" + token);
//
//        String newToken = token.substring("Bearer".length());
//        log.info("新的token值为:" + newToken);
//
//        String operator=new JwtTokenUtil().getUserNameFromToken(newToken);

        String operateType = sysLog.operateType();

        String operateContent = sysLog.operateContent();
        SystemLog systemLog = new SystemLog();
        systemLog.setOperatetype(operateType);
        systemLog.setOperatedate(new Date());
        systemLog.setOperateor(operator);

        systemLogService.save(systemLog);
        Object result = null;
        try {
            //让代理方法执行
            result = pjp.proceed();
            // 2.相当于后置通知(方法成功执行之后走这里)
            systemLog.setOperateresult("正常");
            // 设置操作结果
        } catch (SQLException e) {
            // 3.相当于异常通知部分
            systemLog.setOperateresult("失败");
            // 设置操作结果
        } finally {
            // 4.相当于最终通知
            systemLog.setOperatedate(new Date());
            // 设置操作日期
            systemLogService.save(systemLog);
        }
        return result;
    }


}
