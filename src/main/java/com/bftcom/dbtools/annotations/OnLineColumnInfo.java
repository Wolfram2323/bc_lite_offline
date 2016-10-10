package com.bftcom.dbtools.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by k.nikitin on 04.10.2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OnLineColumnInfo {
    String columnName() default "";
    String joinAlias() default "";
}
