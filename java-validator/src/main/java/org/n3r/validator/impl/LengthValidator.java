package org.n3r.validator.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.n3r.validator.EasyValidator;
import org.n3r.validator.EasyValidatorParamAware;
import org.n3r.validator.EasyValidatorTag;

import java.util.List;

/**
 * 长度校验器.
 *
 * @author wanglei
 * @since 14-9-6 下午3:25
 */
@EasyValidatorTag("len")
public class LengthValidator implements EasyValidator, EasyValidatorParamAware {
    protected int minLength = 0;
    protected int maxLength = Integer.MAX_VALUE;

    @Override
    public Object validate(Object value) {
        int length = value == null ? 0 : value.toString().length();

        if (length >= minLength && length <= maxLength) return value;

        throw new RuntimeException("value [" + value + "]'s length is not in range ("
                + minLength + ", " + maxLength + ")");
    }

    @Override
    public void setParameter(String parameter) {
        if (StringUtils.isEmpty(parameter)) return;

        List<String> parts = Lists.newArrayList(Splitter.on(',').trimResults().split(parameter));

        if (parts.size() > 2) {
            throw new RuntimeException(parameter + "has too many paramerters for LengthValidator");
        }

        if (parts.size() == 1) {
            if (StringUtils.isNotEmpty(parts.get(0))) {
                maxLength = Integer.parseInt(parts.get(0));
            }
        } else if (parts.size() == 2) {
            if (StringUtils.isNotEmpty(parts.get(0))) {
                minLength = Integer.parseInt(parts.get(0));
            }
            if (StringUtils.isNotEmpty(parts.get(1))) {
                maxLength = Integer.parseInt(parts.get(1));
            }
        }

        if (minLength > maxLength) {
            throw new RuntimeException(parameter + "is invalid for LengthValidator");
        }
    }
}
