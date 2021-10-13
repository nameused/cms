package com.github.cms.component;


import java.lang.annotation.*;

/**
 * @author zhangmingyang
 * @Date: 2021/10/13
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SysLog {
    /**
     * 操作对象
     *
     * @return
     */
    String operateType() default "";

    /**
     * 操作内容
     *
     * @return
     */
    String operateContent() default "";
}
