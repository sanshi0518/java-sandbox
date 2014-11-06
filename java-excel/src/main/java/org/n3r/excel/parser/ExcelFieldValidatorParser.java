package org.n3r.excel.parser;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.n3r.core.joor.Reflect;
import org.n3r.validator.EasyValidator;
import org.n3r.validator.EasyValidatorParamAware;
import org.n3r.validator.EasyValidatorTagParser;

import java.util.List;

/**
 * FIXME.
 *
 * @author wanglei
 * @since 14-11-5 下午5:18
 */
public class ExcelFieldValidatorParser {

    public static List<EasyValidator> parse(String validatorStr) {
        List<EasyValidator> validators = Lists.newArrayList();

        if (StringUtils.isEmpty(validatorStr)) return validators;

        Iterable<String> validatorIter = Splitter.on("@").omitEmptyStrings().trimResults().split(validatorStr);

        for (String validator : validatorIter) {
            String tagName = null;
            String parameter = null;

            int leftBracketPos = validator.indexOf('(');

            if (leftBracketPos > 0) {
                int rightBracketPos = validator.indexOf(')', leftBracketPos);
                if (rightBracketPos < 0)
                    throw new RuntimeException("There is no matched brackets in " + validator);

                tagName = validator.substring(0, leftBracketPos);
                parameter = validator.substring(leftBracketPos + 1, rightBracketPos);
            } else {
                tagName = validator;
                parameter = "";
            }

            Class<?> clazz = EasyValidatorTagParser.parse(tagName);
            if (clazz == null)
                throw new RuntimeException("There is no class annotated by tag " + tagName);

            EasyValidator validatorInstance = Reflect.on(clazz).create().get();
            if (validatorInstance instanceof EasyValidatorParamAware)
                ((EasyValidatorParamAware) validatorInstance).setParameter(parameter);

            validators.add(validatorInstance);
        }

        return validators;
    }
}
