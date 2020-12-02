package com.swain.common.utils.utils;

import com.swain.common.constant.HttpStatus;
import com.swain.common.constant.ReturnInfo;
import com.swain.common.utils.component.exception.ErrorReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.swain.common.constant.ReturnInfo.SUCCESS;

public class ReturnUtils {
    private static final Logger LOGGER =
        LoggerFactory.getLogger(ReturnUtils.class);

    /**
     * 封装返回结果
     */
//    public static CommonReturn buildReturnResultInfo(ReturnInfo returnInfo,
//                                                     Object data, HttpServletRequest request,
//                                                     HttpServletResponse httpServletResponse, Long startTime) {
//        ReturnDetails returnDetails = new ReturnDetails();
//        CommonReturn commonReturn = new CommonReturn();
//        HttpStatus httpStatus;
//
//        if (returnInfo.equals(SUCCESS)) {
//            httpStatus = HttpStatus.OK;
//        } else {
//            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//
//        }
//        httpServletResponse.setStatus(httpStatus.value());
//
//        returnDetails.setCode(returnInfo.getCode());
//        returnDetails.setMsg(returnInfo.getMsg());
//        commonReturn.setData(data);
//        commonReturn.setDetails(returnDetails);
//
//        return commonReturn;
//    }

    public static ErrorReturn buildSuccessReturn() {
        ErrorReturn errorReturn = new ErrorReturn();
        errorReturn.setCode(SUCCESS.getCode());
        errorReturn.setMsg(SUCCESS.getMsg());
        return errorReturn;
    }
}
