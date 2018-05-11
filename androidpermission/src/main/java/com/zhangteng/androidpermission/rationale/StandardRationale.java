package com.zhangteng.androidpermission.rationale;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import java.util.Arrays;
import java.util.List;

/**
 * Created by swing on 2018/5/10.
 */
public class StandardRationale implements Rationale {
    private List<String> permissions;
    private String rationale;

    public StandardRationale(String rationale, String... permissions) {
        this.rationale = rationale;
        this.permissions = Arrays.asList(permissions);
    }

    @Override
    public String getRationaleStr() {
        return rationale;
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context instanceof Activity) {
                for (String permission : permissions) {
                    return ((Activity) context).shouldShowRequestPermissionRationale(permission);
                }
            }
        }
        return false;
    }
}
