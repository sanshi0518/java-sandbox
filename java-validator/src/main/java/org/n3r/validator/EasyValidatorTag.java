package org.n3r.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * FIXME.
 *
 * @author wanglei
 * @since 14-11-4 下午3:37
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EasyValidatorTag {

    String value() default "";

}
