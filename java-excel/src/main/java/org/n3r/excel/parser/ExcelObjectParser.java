package org.n3r.excel.parser;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;
import org.n3r.core.lang.RClassPath;
import org.n3r.excel.annotation.ExcelField;
import org.n3r.validator.EasyValidator;
import org.n3r.validator.EasyValidatorTag;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * FIXME.
 *
 * @author wanglei
 * @since 14-11-4 下午3:23
 */
public class ExcelObjectParser {

    private static LoadingCache<Class, List<ExcelFieldInstance>> cache = CacheBuilder.newBuilder().build(
            new CacheLoader<Class, List<ExcelFieldInstance>>() {
                @Override
                public List<ExcelFieldInstance> load(Class excelObjectClazz) throws Exception {
                    List<ExcelFieldInstance> excelFieldInstances = new ArrayList<ExcelFieldInstance>();

                    Field[] fields = excelObjectClazz.getDeclaredFields();
                    for (Field field : fields) {
                        ExcelField excelField = field.getAnnotation(ExcelField.class);
                        if (excelField == null) continue;

                        field.setAccessible(true);

                        ExcelFieldInstance instance = new ExcelFieldInstance();
                        instance.setField(field);
                        instance.setPosition(excelField.position());
                        instance.setZeroIfNull(excelField.zeroIfNull());

                        List<EasyValidator> validators = ExcelFieldValidatorParser.parse(excelField.validator());
                        instance.setValidators(validators);

                        excelFieldInstances.add(instance);
                    }

                    return excelFieldInstances;
                }
            }
    );

    public static List<ExcelFieldInstance> parse(Class excelObjectClazz) {
        return cache.getUnchecked(excelObjectClazz);
    }

}
