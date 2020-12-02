package com.swain.common.utils.utils;

import com.swain.common.utils.utils.constans.SystemStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 **/
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", SystemStatus.SUCCESS.getCode());
        put("msg", SystemStatus.SUCCESS.getMsg());
    }

    public R(SystemStatus status) {
        set(status);
    }

    public static R error() {
        return error(500, "未知异常");
    }

    public static R error(String msg) {
        return error(500, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public R set(SystemStatus status) {
        put("code", status.getCode());
        put("msg", status.getMsg());
        return this;
    }

    public void addData(Object data) {
        put("data", data);
    }

    public Object getData() {
        return get("data");
    }

    public boolean isOk() {
        Object code = get("code");
        return code != null && SystemStatus.SUCCESS.getCode() == ((Integer) code);
    }
}
