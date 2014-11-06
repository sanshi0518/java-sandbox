package org.n3r.validator.impl;

import org.n3r.validator.EasyValidatorTag;

/**
 * 字符串校验器。
 * 当未定义校验器时，采用StringValidator作为默认的校验器。
 *
 * @author wanglei
 * @since 14-9-6 下午3:25
 */
@EasyValidatorTag("str")
public class StringValidator extends LengthValidator {

    @Override
    public Object validate(Object value) {
        super.validate(value);
        return value;
    }
}
