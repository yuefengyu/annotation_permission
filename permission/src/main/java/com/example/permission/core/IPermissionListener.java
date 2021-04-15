package com.example.permission.core;

/**
 * @des:
 * @author: yyf
 * @date: 4/15/21 10:14 AM
 */
public interface IPermissionListener {
    void granted();

    void cancel();

    void denied();
}
