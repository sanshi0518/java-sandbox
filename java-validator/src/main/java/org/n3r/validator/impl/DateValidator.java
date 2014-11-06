package org.n3r.validator.impl;

import org.apache.commons.lang3.StringUtils;
import org.n3r.validator.EasyValidator;
import org.n3r.validator.EasyValidatorParamAware;
import org.n3r.validator.EasyValidatorTag;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期校验器.
 *
 * @author wanglei
 * @since 14-9-6 下午3:25
 */
@EasyValidatorTag("date")
public class DateValidator implements EasyValidator, EasyValidatorParamAware {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Object validate(Object value) {
        if (value == null) return null;

        return value instanceof Date ? (Date) value : validateStringFormat(value + "");
    }

    private Object validateStringFormat(String value) {
        if (StringUtils.isEmpty(value)) return null;

        try {
            return format.parse(value);
        } catch (ParseException e) {
            throw new RuntimeException(value + " can not parsed as " + format);
        }
    }

    @Override
    public void setParameter(String parameter) {
        if (StringUtils.isNotEmpty(parameter))
            format = new SimpleDateFormat(parameter);
    }
}
