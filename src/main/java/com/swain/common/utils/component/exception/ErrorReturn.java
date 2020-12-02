package com.swain.common.utils.component.exception;

import java.io.Serializable;
import java.util.Objects;

/**
 * 错误返回
 */
public class ErrorReturn implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String code = null;
    private String msg = null;

    public ErrorReturn()
    {
        super();
    }

    /**
     * 错误码,DEV.0000为成功,其余失败
     **/
    public String getCode()
    {
        return code;
    }
    public void setCode(String code_in)
    {
        this.code = code_in;
    }

    /**
     * 错误信息
     **/
    public String getMsg()
    {
        return msg;
    }
    public void setMsg(String msg_in)
    {
        this.msg = msg_in;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorReturn errorReturn = (ErrorReturn) o;
        return Objects.equals(this.code,
                errorReturn.code)
                && Objects.equals(this.msg,
                errorReturn.msg);
    }


    @Override
    public int hashCode() {
        return Objects.hash(code
                , msg);
    }




    @Override
    public String toString()  {
        StringBuilder sb = new StringBuilder();
        sb.append("class ErrorReturn { ");

        sb.append("  code: ").append(code).append(", ");
        sb.append("  msg: ").append(msg).append(", ");
        sb.append("}");
        return sb.toString();
    }
}
