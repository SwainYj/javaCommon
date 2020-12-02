package com.swain.common.aop.aspect;

import com.swain.common.aop.annotation.TokenCheck;
import com.swain.common.utils.component.threadLocal.ThreadLocalHolder;
import com.swain.common.utils.component.token.SimpleUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.swain.common.constant.Constants.TokenType.TOKEN_CHILD_ACCT;
import static com.swain.common.constant.Constants.TokenType.TOKEN_MAIN_ACCT;
import static com.swain.common.constant.Constants.TokenType.TOKEN_SYSTEM;
import static com.swain.common.constant.Constants.TokenType.TOKEN_USER;

@Aspect
@Component
public class TokenCheckAspect {
    @Autowired
    private HttpServletRequest request;

    @Pointcut("@annotation(com.swain.common.aop.annotation.TokenCheck)")
    private void tokenCheckCut() {
    }

    //定义了切面的处理逻辑。即方法上加了@TokenCheck
    @Before("tokenCheckCut()&&@annotation(tokenCheck)")
    public void before(JoinPoint point, TokenCheck tokenCheck) throws Exception {
        SimpleUser user = ThreadLocalHolder.getSimpleUser();
        if (null == user) {
            return;
        }

        int type = tokenCheck.type();
//        if (TOKEN_SYSTEM.equals(type)) {
//            //系统token校验
//            if (!TOKEN_PRESETTING.equals(user.getTokenRole())) {
//                throw new DeveloperServiceException(TOKEN_TYPE_IS_NOT_CORRECT);
//            }
//        } else if (TOKEN_USER.equals(type)) {
//            //用户token校验
//            if (TOKEN_PRESETTING.equals(user.getTokenRole())) {
//                throw new DeveloperServiceException(TOKEN_TYPE_IS_NOT_CORRECT);
//            }
//        } else if (TOKEN_MAIN_ACCT.equals(type)) {
//            if (TOKEN_PRESETTING.equals(user.getTokenRole())) {
//                throw new DeveloperServiceException(TOKEN_TYPE_IS_NOT_CORRECT);
//            }
//            //主账户token校验
//            if (!user.getDomainName().equals(user.getName())) {
//                throw new DeveloperServiceException(CHILD_ACCOUNT);
//            }
//        } else if (TOKEN_CHILD_ACCT.equals(type)) {
//            if (TOKEN_PRESETTING.equals(user.getTokenRole())) {
//                throw new DeveloperServiceException(TOKEN_TYPE_IS_NOT_CORRECT);
//            }
//            //子账户token校验
//            if (user.getDomainName().equals(user.getName())) {
//                throw new DeveloperServiceException(CHILD_ACCOUNT);
//            }
//        }
    }
}
