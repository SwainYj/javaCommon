package com.swain.common.aop.interceptor;

import com.swain.common.utils.component.threadLocal.ThreadLocalHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BaseInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        //记录请求起始时间
        ThreadLocalHolder.setStartTime(System.currentTimeMillis());
        ThreadLocalHolder.setRequestBodyThreadLocal(null);
        ThreadLocalHolder.setSimpleUser(null);
        ThreadLocalHolder.initAttribute();

        return true;
    }
}
