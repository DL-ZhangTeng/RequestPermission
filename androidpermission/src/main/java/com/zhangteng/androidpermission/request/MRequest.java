package com.zhangteng.androidpermission.request;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.zhangteng.androidpermission.PermissionActivity;
import com.zhangteng.androidpermission.callback.Callback;

/**
 * Android6权限请求
 * Created by swing on 2018/5/10.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class MRequest implements Request {
    private String[] permissions;

    public MRequest(String... permissions) {
        this.permissions = permissions;
    }

    @Override
    public void requestPermissions(Context context, int permissionCode, Callback callback) {
        PermissionActivity.requestPermission(context, this.permissions, permissionCode, callback);
    }
}
