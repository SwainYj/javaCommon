package com.swain.common.constant;

import static com.swain.common.constant.HttpStatus.BAD_REQUEST;
import static com.swain.common.constant.HttpStatus.INTERNAL_SERVER_ERROR;
import static com.swain.common.constant.HttpStatus.OK;

public enum ReturnInfo {
    SUCCESS("DEV.0000", "SUCCESS", OK.value()),
    FAILED("DEV.0001", "FAILED"),
    RETURN_NULL("DEV.0002", "Return null."),
    Query_FAIELD("DEV.0003", "Query database failed."),
    UPDATE_FAIELD("DEV.0004", "Update database failed."),
    INSERT_FAIELD("DEV.0005", "Insert database failed."),
    DELETE_FAIELD("DEV.0006", "Delete database failed."),

    //通用校验错误
    VALIDATE_ERROR("DEV.0020", "JSON validate error", BAD_REQUEST.value()),
    DATA_TYPE_ERROR("DEV.0021", "data type is error", BAD_REQUEST.value()),
    DATA_MISS_ERROR("DEV.0022", "missing required paramter", BAD_REQUEST.value()),
    HEADER_MISS_ERROR("DEV.0023", "missing required header", BAD_REQUEST.value()),
    PARAM_VALID_ERROR("DEV.0024", "paramter validate failed", BAD_REQUEST.value()),
    REQUEST_METHOD_ERROR("DEV.0025", "method is error", BAD_REQUEST.value()),
    BLACKLIST_VALID_FAILED("DEV.0026", "blacklist valid error", OK.value()),
    TOKEN_IS_NOT_CORRECT("DEV.0027", "token is not correct", OK.value()),
    TOKEN_TYPE_IS_NOT_CORRECT("DEV.0028", "token type is not correct", OK.value()),
    TOKEN_OVER_TIME("DEV.0029", "token IS overtime", OK.value()),
    PARAM_VALID_ERROR_500("DEV.0024", "paramter validate failed", INTERNAL_SERVER_ERROR.value()),
    PARAM_VALID_ERROR_200("DEV.0024", "paramter validate failed", OK.value());

    private String msg;

    private String code;

    private Integer httpcode;

    private ReturnInfo(String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.httpcode = INTERNAL_SERVER_ERROR.value();
    }

    private ReturnInfo(String code, String msg, Integer httpcode) {
        this.code = code;
        this.msg = msg;
        this.httpcode = httpcode;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public Integer getHttpcode() {
        return this.httpcode;
    }
}
