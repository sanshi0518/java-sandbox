package org.n3r.validator.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.n3r.validator.EasyValidator;
import org.n3r.validator.EasyValidatorParamAware;
import org.n3r.validator.EasyValidatorTag;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 范围校验器.
 *
 * @author wanglei
 * @since 14-9-6 下午3:25
 */
@EasyValidatorTag("range")
public class RangeValidator extends RequiredValidator implements EasyValidatorParamAware {
    public Pattern pattern = Pattern.compile("^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)[dD]?$");
    protected double min = 0;
    protected double max = Double.MAX_VALUE;

    @Override
    public Object validate(Object value) {
        super.validate(value);

        String strValue = value.toString();
        if (!pattern.matcher(strValue).matches())
            throw new RuntimeException(value + "is not a number, and number is the only case for RangeValidator");

        double doubleValue = Double.parseDouble(strValue);

        if (doubleValue >= min && doubleValue <= max) return value;

        throw new RuntimeException("value [" + value + "]'s size is not in range ("
                + min + ", " + max + ")");
    }

    @Override
    public void setParameter(String parameter) {
        if (StringUtils.isEmpty(parameter)) return;

        List<String> parts = Lists.newArrayList(Splitter.on(',').trimResults().split(parameter));
        if (parts.size() > 2)
            throw new RuntimeException(parameter + "has too many paramerters for RangeValidator");

        if (parts.size() == 1) {
            if (StringUtils.isNotEmpty(parts.get(0))) max = Double.parseDouble(parts.get(0));
        } else if (parts.size() == 2) {
            if (StringUtils.isNotEmpty(parts.get(0))) min = Double.parseDouble(parts.get(0));
            if (StringUtils.isNotEmpty(parts.get(1))) max = Double.parseDouble(parts.get(1));
        }

        if (min > max)
            throw new RuntimeException(parameter + "is invalid for RangeValidator");
    }

}
