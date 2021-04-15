package com.example.permission.aspect;

import android.content.Context;

import com.example.permission.MyPermissionActivity;
import com.example.permission.annotation.Permission;
import com.example.permission.annotation.PermissionCancel;
import com.example.permission.annotation.PermissionDenied;
import com.example.permission.core.IPermissionListener;
import com.example.permission.utils.PermissionUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import androidx.fragment.app.Fragment;

/**
 * @des:
 * @author: yyf
 * @date: 4/15/21 11:06 AM
 */
@Aspect
public class PermissionAspect {
    @Pointcut("execution(@com.example.permission.annotation.Permission * *(..)) && @annotation(permission)")
    public void pointActionMethod(Permission permission) {
    }

    @Around("pointActionMethod(permission)")
    public void aProceedingJoinPoint(ProceedingJoinPoint joinPoint, Permission permission) throws Exception {
        Object aThis = joinPoint.getThis();
        Context context = null;
        if (aThis instanceof Context) {
            context = (Context) aThis;
        } else if (aThis instanceof Fragment) {
            context = ((Fragment) aThis).getContext();
        }
        if (context == null || permission == null) {
            throw new Exception("context == null || permission == null");
        }

        Context finalContext = context;
        MyPermissionActivity.requestPermission(context, permission.value(), permission.requestCode(), new IPermissionListener() {
            @Override
            public void granted() {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void cancel() {
                invokeAnnotation(aThis, PermissionCancel.class);
            }

            @Override
            public void denied() {
                invokeAnnotation(aThis, PermissionDenied.class);
                // TODO: 4/15/21 打开设置
                PermissionUtils.startAndroidSettings(finalContext);
            }
        });
    }

    public void invokeAnnotation(Object object, Class annotationClass) {
        Class<?> aClass = object.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();
        if (declaredMethods == null || declaredMethods.length <= 0) {
            return;
        }
        for (Method declaredMethod : declaredMethods) {
            declaredMethod.setAccessible(true);
            boolean annotationPresent = declaredMethod.isAnnotationPresent(annotationClass);
            if (annotationPresent) {
                try {
                    declaredMethod.invoke(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
