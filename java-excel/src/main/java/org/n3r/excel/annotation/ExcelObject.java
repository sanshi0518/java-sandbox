package org.n3r.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel Object annotation should be used at Class level with the below fields populated.
 *
 * @author wanglei
 * @since 14-11-3 下午2:51
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE })
public @interface ExcelObject {

    ParseType parseType() default ParseType.ROW;

    int start() default 1;

    int end() default -1;

    boolean ignoreAllZerosOrNullRows() default false;

}
