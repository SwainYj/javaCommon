
package com.swain.common.utils.utils;

import com.swain.common.constant.ReturnInfo;
import com.swain.common.utils.component.exception.ProjectServiceException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.swain.common.constant.ReturnInfo.PARAM_VALID_ERROR;
import static com.swain.common.constant.ReturnInfo.PARAM_VALID_ERROR_500;

public class ParamValidateUtils {
    /**
     * 校验ISV的ID是否合法
     *
     * @param id
     * @return
     */
    public static boolean isISVId(String id) {
        if (StringUtils.isNotEmpty(id)) {
            String rexp = "^[0-9a-zA-Z-]{0,49}$";
            Pattern pattern = Pattern.compile(rexp);
            Matcher match = pattern.matcher(id);

            return match.matches();
        } else {
            return false;
        }
    }

    public static void postiveNumValid(Integer num, String msg) throws ProjectServiceException {
        if (num <= 0) {
            throw new ProjectServiceException(PARAM_VALID_ERROR, msg + " should be large than 0");
        }
    }

    public static void weekValid(String weekId) throws ProjectServiceException {
        String format = "yyyyww";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (weekId.length() != format.length()) {
            throw new ProjectServiceException(PARAM_VALID_ERROR_500, "weekId");
        }
        try {
            sdf.parse(weekId);
        } catch (ParseException e) {
            throw new ProjectServiceException(PARAM_VALID_ERROR_500, "weekId");
        }
        Integer week = Integer.valueOf(weekId.substring(4));
        if (week < 1 || week > 53) {
            throw new ProjectServiceException(PARAM_VALID_ERROR_500, "weekId");
        }
    }

    public static void monthValid(String monthId) throws ProjectServiceException {
        String format = "yyyyMM";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (monthId.length() != format.length()) {
            throw new ProjectServiceException(PARAM_VALID_ERROR_500, "monthId");
        }
        try {
            sdf.parse(monthId);
        } catch (ParseException e) {
            throw new ProjectServiceException(PARAM_VALID_ERROR_500, "monthId");
        }
        Integer month = Integer.valueOf(monthId.substring(4));
        if (month < 1 || month > 12) {
            throw new ProjectServiceException(PARAM_VALID_ERROR_500, "monthId");
        }
    }

    public static void enumValid(Object o, String msg, Object... objs) throws ProjectServiceException {
        if (null == o) {
            throw new ProjectServiceException(PARAM_VALID_ERROR, msg + " should not be null");
        } else {
            for (Object obj : objs) {
                if (o.equals(obj)) {
                    return;
                }
            }
        }
        throw new ProjectServiceException(PARAM_VALID_ERROR, msg + ":" + o
            + " is not within the correct range");
    }

    public static void enumValid4Http500(Object o, String msg, Object... objs) throws ProjectServiceException {
        if (null == o) {
            throw new ProjectServiceException(PARAM_VALID_ERROR_500, msg + " should not be null");
        } else {
            for (Object obj : objs) {
                if (o.equals(obj)) {
                    return;
                }
            }
        }
        throw new ProjectServiceException(PARAM_VALID_ERROR_500, msg + ":" + o
            + " is not within the correct range");
    }

    public static void enumValidAllowNull(Object o, String msg, Object... objs) throws ProjectServiceException {
        if (o == null) {
            return;
        }
        for (Object obj : objs) {
            if (obj.equals(o)) {
                return;
            }
        }
        throw new ProjectServiceException(PARAM_VALID_ERROR, msg + ":" + o
            + " is not within the correct range");
    }

    public static void notNullValid(Object o, String msg) throws ProjectServiceException {
        if (null == o) {
            throw new ProjectServiceException(PARAM_VALID_ERROR, msg + " should not be null");
        }

        if (o instanceof String) {
            String s = (String) o;
            if (StringUtils.isEmpty(s)) {
                throw new ProjectServiceException(PARAM_VALID_ERROR, msg + " should not be null");
            }
        }
    }

    public static void notNullValid(Object o, String msg, ReturnInfo returnInfo) throws ProjectServiceException {
        try {
            notNullValid(o, msg);
        } catch (ProjectServiceException e) {
            throw new ProjectServiceException(returnInfo, e.getMessage());
        }
    }

    public static void dateFormatValid(String str, String format, String msg) throws ProjectServiceException {
        DateFormat formatter = new SimpleDateFormat(format);
        Boolean isPass;
        try {
            Date date = (Date) formatter.parse(str);
            isPass = str.equals(formatter.format(date));
        } catch (Exception e) {
            isPass = false;
        }

        if (!isPass) {
            throw new ProjectServiceException(PARAM_VALID_ERROR_500, msg + " format is error,should be " + format);
        }
    }

    public static void isNumValid(String str, String msg) throws ProjectServiceException {
        String reg = "^[-\\+]?([0-9]+\\.?)?[0-9]+$";
        if (null == str || !str.matches(reg)) {
            throw new ProjectServiceException(PARAM_VALID_ERROR_500, msg + " should be number");
        }
    }

}
