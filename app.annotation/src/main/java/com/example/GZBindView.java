package com.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Created by xujiayunew on 2017/10/5.
 */
@Retention(RetentionPolicy.CLASS)

@Target(ElementType.FIELD)
public @interface GZBindView {
    int value();
}
