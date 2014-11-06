package org.n3r.validator.impl;

import org.n3r.validator.EasyValidatorTag;

/**
 * 整型校验器.
 *
 * @author wanglei
 * @since 14-9-6 下午3:25
 */
@EasyValidatorTag("int")
public class IntegerValidator extends RangeValidator {

    @Override
    public Object validate(Object value) {
        super.validate(value);

        try {
            Integer.parseInt(value.toString());
        } catch (Exception e) {
            throw new RuntimeException(value + " is not a integer");
        }

        return value;
    }

}
