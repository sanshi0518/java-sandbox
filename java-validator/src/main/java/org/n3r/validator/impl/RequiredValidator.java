package org.n3r.validator.impl;

import org.apache.commons.lang3.StringUtils;
import org.n3r.validator.EasyValidator;
import org.n3r.validator.EasyValidatorTag;

/**
 * 非空校验器.
 *
 * @author wanglei
 * @since 14-9-6 下午3:25
 */
@EasyValidatorTag("required")
public class RequiredValidator implements EasyValidator {

    @Override
    public Object validate(Object value) {
        if (value == null || StringUtils.isEmpty(value.toString()))
            throw new RuntimeException("required check failed");

        return value;
    }

}
