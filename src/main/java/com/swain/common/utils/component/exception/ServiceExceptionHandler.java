package com.swain.common.utils.component.exception;

import com.swain.common.constant.HttpStatus;
import com.swain.common.utils.component.threadLocal.ThreadLocalHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.swain.common.constant.ReturnInfo.FAILED;

@ControllerAdvice
public class ServiceExceptionHandler {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(ServiceExceptionHandler.class);

    @ExceptionHandler(value = ProjectServiceException.class)
    @ResponseBody
    public ErrorReturn serviceExceptionHandler(HttpServletRequest httpServletRequest, HttpServletResponse
            httpServletResponse, Exception e) throws Exception {
        ErrorReturn errorReturn = new ErrorReturn();
        try {
            if (e instanceof ProjectServiceException) {
                errorReturn.setCode(((ProjectServiceException) e).getId());
                errorReturn.setMsg(e.getMessage());
                httpServletResponse.setStatus(((ProjectServiceException) e).getHttpCode());
            } else {
                errorReturn.setCode(FAILED.getCode());
                errorReturn.setMsg(FAILED.getMsg() + ":" + e.getMessage());
                httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        } catch (RuntimeException ee) {
            throw e;
        } catch (Exception ee) {
            errorReturn.setCode(FAILED.getCode());
            errorReturn.setMsg(FAILED.getMsg() + ":" + e.getMessage());
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

//        LOGGER.error(e.getMessage() + ":{}", getErrorTrackSpace(e));
//        if (errorReturn.getCode().startsWith("DEV.00") && !errorReturn.getCode()
//                .equals(BLACKLIST_VALID_FAILED.getCode())
//                && !errorReturn.getCode().equals(TOKEN_OVER_TIME.getCode())) {
//            AlarmLog.printAlarmLog(e.getMessage(), e);
//        }

        if (httpServletRequest.getRequestURI().equals("/rest/cbc/developerresservice/v1/publish/active_check")
                && HttpStatus.INTERNAL_SERVER_ERROR.value() == httpServletResponse.getStatus()) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
        }

        Long costTime;
        if (null == ThreadLocalHolder.getStartTime()) {
            costTime = 0L;
        } else {
            costTime = System.currentTimeMillis() - ThreadLocalHolder.getStartTime();
        }

        StringBuffer callLogSb = new StringBuffer();
        callLogSb.append("[interface]");
        callLogSb.append("|");
        callLogSb.append("|" + errorReturn.toString());
        callLogSb.append("|" + httpServletRequest.toString());
        callLogSb.append("|" + httpServletResponse.getStatus());
        callLogSb.append("|" + costTime);
        LOGGER.info(callLogSb.toString());
        return errorReturn;
    }
}
