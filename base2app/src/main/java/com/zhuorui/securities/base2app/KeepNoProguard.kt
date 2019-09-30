package com.zhuorui.securities.base2app

import java.lang.annotation.RetentionPolicy

/**
 * Create by xieyingwu on 2018/7/4
 * 申明被该注解注释的class，field，method都不被混淆
 */
@java.lang.annotation.Retention(RetentionPolicy.CLASS)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FILE,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.FIELD
)
annotation class KeepNoProguard
