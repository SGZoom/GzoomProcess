package com.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xujiayunew on 2017/10/6.
 * 对注解view进行点击事件绑定
 */
@Retention(RetentionPolicy.CLASS)//只保留到class

@Target(ElementType.METHOD)//适用于方法的注解
public @interface GZClickView {
    int value();
 }
