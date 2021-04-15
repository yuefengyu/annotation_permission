package com.example.permission.annotation;

import com.example.permission.MyPermissionActivity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @des:
 * @author: yyf
 * @date: 4/15/21 10:46 AM
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionDenied {
    int requestCode() default MyPermissionActivity.PERMISSION_REQUEST_CODE_DEFAULT;
}
