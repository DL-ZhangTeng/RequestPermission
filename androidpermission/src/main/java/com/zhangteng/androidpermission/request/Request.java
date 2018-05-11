package com.zhangteng.androidpermission.request;

import android.content.Context;

import com.zhangteng.androidpermission.callback.Callback;

/**
 * Created by swing on 2018/5/10.
 */
public interface Request {
    void requestPermissions(Context context, int permissionCode, Callback callback);
}
