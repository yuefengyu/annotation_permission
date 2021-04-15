package com.example.permission.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * @des:
 * @author: yyf
 * @date: 4/15/21 11:36 AM
 */
public class HWSettingIntent implements IMenu {
    @Override
    public Intent getStartActivity(Context context) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
        intent.setComponent(comp);
        return intent;
    }
}
