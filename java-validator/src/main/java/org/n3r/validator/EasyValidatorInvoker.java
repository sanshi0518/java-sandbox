package org.n3r.validator;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * FIXME.
 *
 * @author wanglei
 * @since 14-11-5 上午2:20
 */
public class EasyValidatorInvoker {

    public static Object invoke(Object value, EasyValidator validator) {
        List<EasyValidator> validators = Lists.newArrayList(validator);
        return invoke(value, validators);
    }

    public static Object invoke(Object value, List<EasyValidator> validators) {
        if (CollectionUtils.isEmpty(validators)) return value;

        Object convertedValue = value;

        for (EasyValidator validator : validators) {
            if (validator == null) continue;
            convertedValue = validator.validate(value);
        }

        return convertedValue;
    }


}
