package org.n3r.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Nested Excel Object annotation should be used at Field level.
 *
 * @author wanglei
 * @since 14-11-3 下午3:14
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD })
public @interface NestedExcelObject {

}
