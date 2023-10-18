package com.zhangteng.androidpermission.checker;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.zhangteng.androidpermission.utils.VerifyUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by swing on 2018/5/10.
 */
public class StandardChecker implements Checker {
    private List<String> permissions;

    public StandardChecker(String... permissions) {
        this.permissions = Arrays.asList(permissions);
    }

    @Override
    public boolean hasPermission(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (VerifyUtils.isProcess(context, permission)) {
                    //如果是Android11并且请求存储管理权限时检查权限
                    if (VerifyUtils.isManageExternalStorage(permission)) {
                        if (!VerifyUtils.hasManageExternalStorage(permission)) {
                            return false;
                        } else {
                            continue;
                        }
                    }

                    int result = context.checkPermission(permission, android.os.Process.myPid(), android.os.Process.myUid());
                    if (result == PackageManager.PERMISSION_DENIED) {
                        return false;
                    }

                    String op = AppOpsManager.permissionToOp(permission);
                    if (TextUtils.isEmpty(op)) {
                        continue;
                    }

                    AppOpsManager appOpsManager = context.getSystemService(AppOpsManager.class);
                    result = appOpsManager.noteProxyOp(op, context.getPackageName());
                    if (result != AppOpsManager.MODE_ALLOWED) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
