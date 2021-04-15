package com.example.permission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.permission.core.IPermissionListener;
import com.example.permission.utils.PermissionUtils;

public class MyPermissionActivity extends AppCompatActivity {
    private static final String PERMISSION_PARAMS = "permission_params";
    private static final String PERMISSION_REQUEST_CODE = "permission_request_code";
    public static final int PERMISSION_REQUEST_CODE_DEFAULT = -1;

    private String[] permissions;
    private int requestCode;
    private static IPermissionListener sIPermissionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_permission);
        permissions = getIntent().getStringArrayExtra(PERMISSION_PARAMS);
        requestCode = getIntent().getIntExtra(PERMISSION_REQUEST_CODE, PERMISSION_REQUEST_CODE_DEFAULT);

        if (permissions == null || requestCode < 0 || sIPermissionListener == null) {
            finish();
            return;
        }
        boolean hasPermissionRequest = PermissionUtils.hasPermissionRequest(this, permissions);
        if (hasPermissionRequest) {
            sIPermissionListener.granted();
            finish();
            return;
        }

        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 真正申请成功了
        if (PermissionUtils.requestPermissionSuccess(grantResults)) {
            sIPermissionListener.granted();
            finish();
            return;
        }
        // 如果用户点击了，拒绝，（不再提示打勾） 等操作，告诉外界
        if (!PermissionUtils.shouldShowRequestPermissionRationale(this, permissions)) {
            sIPermissionListener.denied();
            finish();
            return;
        }
        // 走到这里，说明用户取消了
        sIPermissionListener.cancel();
        finish();

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    public static void requestPermission(Context context, String[] permissions, int requestCode, IPermissionListener permissionListener) {
        sIPermissionListener = permissionListener;
        Intent intent = new Intent(context, MyPermissionActivity.class);
        intent.putExtra(PERMISSION_PARAMS, permissions);
        intent.putExtra(PERMISSION_REQUEST_CODE, requestCode);
        context.startActivity(intent);
    }
}