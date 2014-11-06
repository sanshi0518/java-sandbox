package org.n3r.validator.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.n3r.validator.EasyValidator;
import org.n3r.validator.EasyValidatorParamAware;
import org.n3r.validator.EasyValidatorTag;

import java.util.List;

/**
 * 枚举校验器.
 *
 * @author wanglei
 * @since 14-9-6 下午3:25
 */
@EasyValidatorTag("enum")
public class EnumValidator implements EasyValidator, EasyValidatorParamAware {
    private List<String> enums;

    @Override
    public Object validate(Object value) {
        if (enums.contains(value)) return value;

        throw new RuntimeException("value in not one of " + enums);
    }

    @Override
    public void setParameter(String parameter) {
        enums = Lists.newArrayList(Splitter.on(',').trimResults().split(parameter));
    }
}
