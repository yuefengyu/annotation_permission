package com.example.permission.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * @des:
 * @author: yyf
 * @date: 4/15/21 11:47 AM
 */
public class MIUISettingIntent implements IMenu {

    @Override
    public Intent getStartActivity(Context context) {
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        i.setComponent(componentName);
        i.putExtra("extra_pkgname", context.getPackageName());
        return i;
    }
}
