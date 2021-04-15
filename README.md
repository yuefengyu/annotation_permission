使用手册：


@Permission(value = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode = 200)
public void testPermission() {
    showToast("权限申请成功");
}

@PermissionCancel
public void cancel() {
    showToast("权限申请取消");
}

@PermissionDenied
public void deny() {
     showToast("权限申请被拒绝");
}
