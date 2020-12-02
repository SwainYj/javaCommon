package com.swain.common.utils.component.exception;

import com.swain.common.constant.ReturnInfo;

public class ProjectServiceException extends RuntimeException {
    private int httpCode = 500;

    private String id = "framwork.remote.SystemError";

    public ProjectServiceException(ReturnInfo returnInfo) {
        super(returnInfo.getMsg());
        this.setId(returnInfo.getCode());
        this.setHttpCode(returnInfo.getHttpcode());
    }

    public ProjectServiceException(ReturnInfo returnInfo, Throwable e) {
        super(returnInfo.getMsg() + ":" + e.getMessage());
        this.setId(returnInfo.getCode());
        this.setHttpCode(returnInfo.getHttpcode());
    }

    public ProjectServiceException(ReturnInfo returnInfo, String e) {
        super(returnInfo.getMsg() + ":" + e);
        this.setId(returnInfo.getCode());
        this.setHttpCode(returnInfo.getHttpcode());
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public int getHttpCode() {
        return this.httpCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}