package com.zhangteng.androidpermission.request;

import android.os.Build;

/**
 * Created by swing on 2018/5/10.
 */
public class RequestFactory {

    public Request createRequest(String... permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return new LRequest(permissions);
        } else {
            return new MRequest(permissions);
        }
    }
}
