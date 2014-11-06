package org.n3r.validator;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;
import org.n3r.core.lang.RClassPath;

import java.util.List;

/**
 * FIXME.
 *
 * @author wanglei
 * @since 14-11-4 下午3:35
 */
public class EasyValidatorTagParser {

    private static final String BASE_PACKAGE = "org.n3r.validator";

    private static LoadingCache<String, Class<?>> cache = CacheBuilder.newBuilder().build(
            new CacheLoader<String, Class<?>>() {
                @Override
                public Class<?> load(String tagName) throws Exception {
                    List<Class<?>> annClasses = RClassPath.getAnnotatedClasses(BASE_PACKAGE, EasyValidatorTag.class);

                    for (Class<?> clazz : annClasses) {
                        EasyValidatorTag validatorTag = clazz.getAnnotation(EasyValidatorTag.class);
                        if (!StringUtils.equalsIgnoreCase(tagName, validatorTag.value())) continue;
                        return  clazz;
                    }

                    return null;
                }
            }
    );

    public static Class<?> parse(String tagName) {
        return cache.getUnchecked(tagName);
    }

}
