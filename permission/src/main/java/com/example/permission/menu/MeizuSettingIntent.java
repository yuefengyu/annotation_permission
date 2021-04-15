package com.example.permission.menu;

import android.content.Context;
import android.content.Intent;

/**
 * @des:
 * @author: yyf
 * @date: 4/15/21 11:49 AM
 */
public class MeizuSettingIntent implements IMenu {
    @Override
    public Intent getStartActivity(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", context.getPackageName());
        return intent;
    }
}
