package com.swain.common.utils.utils.constans;

/**
 * 结果返回状态码
 */
public enum SystemStatus {
    /**
     * 成功 定义为 大于0
     * 失败 定义为 小于0
     * 不要定义 等于 0
     * 不要定义-10000
     */
    SUCCESS(0, "操作成功"),
    FAIL(1, "操作失败")
    ;

    SystemStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    /**
     * 根据状态码获取 SystemStatus
     *
     * @param code
     * @return
     */
    public static SystemStatus getSystemStatus(Integer code) {
        if (null != code) {
            SystemStatus[] values = SystemStatus.values();
            for (SystemStatus systemStatus : values) {
                if (code.equals(systemStatus.getCode())) {
                    return systemStatus;
                }
            }
        }
        return FAIL;
    }

    @Override
    public String toString() {
        return this.code + " : " + this.msg;
    }

    public boolean success() {
        return this == SUCCESS;
    }

    public boolean notSuccess() {
        return this != SUCCESS;
    }

    // 重复状态码定位
    public static void main(String[] args) {
        SystemStatus[] values = SystemStatus.values();
        boolean flag = true;
        for (int i = 0; i < values.length - 1; i++) {
            for (int j = i + 1; j < values.length; j++) {
                if (values[i].getCode() == values[j].getCode()) {
                    System.out.println("code: " + values[j].getCode() + " 重复 [详细信息：" + values[j].toString() + "]");
                    flag = false;
                }
            }
        }
        if (flag) {
            System.out.println("没有重复状态码");
        }
    }


}
