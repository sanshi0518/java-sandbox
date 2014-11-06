package org.n3r.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel Field annotation should be used at Class level with the below fields populated.
 *
 * @author wanglei
 * @since 14-11-3 下午3:02
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD })
public @interface ExcelField {

    int position();

    boolean zeroIfNull() default false;

    String validator();
}
