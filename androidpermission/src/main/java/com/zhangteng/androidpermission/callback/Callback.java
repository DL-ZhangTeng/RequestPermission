package com.zhangteng.androidpermission.callback;

import android.app.Activity;
import android.content.Context;

/**
 * Created by swing on 2018/5/10.
 */
public interface Callback {

    void success(Activity permissionActivity);

    void failure(Activity permissionActivity);

    void nonExecution(Activity permissionActivity);
}
