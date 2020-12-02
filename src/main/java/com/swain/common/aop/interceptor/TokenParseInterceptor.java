package com.swain.common.aop.interceptor;

import com.swain.common.utils.component.threadLocal.ThreadLocalHolder;
import com.swain.common.utils.component.token.SimpleUser;
import com.swain.common.utils.component.token.TokenParser;
import com.swain.common.utils.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenParseInterceptor implements HandlerInterceptor {

    TokenParser tokenParser = TokenParser.getInstance();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {

        String token = request.getHeader("X-Auth-Token");
        if (StringUtils.isEmpty(token)) {
            return true;
        }

        SimpleUser user = tokenParser.getUserInfoFromToken(token);
        roleVerify(user);
        ThreadLocalHolder.setSimpleUser(user);

        return true;
    }

    private void roleVerify(SimpleUser user) {
        // 获取当前在用的系统用户名称
//        String domainName = commonService.getConfigInfo("system.user");
//        if (StringUtils.isNotEmpty(user.getDomainName()) && StringUtils.isNotEmpty(domainName)) {
//            String[] domainNames = domainName.split(COMMA);
//            for (String singleDomainName : domainNames) {
//                if (user.getDomainName().startsWith(singleDomainName)) {
//                    user.setTokenRole(TOKEN_PRESETTING);
//                    break;
//                }
//            }
//        }
    }
}
