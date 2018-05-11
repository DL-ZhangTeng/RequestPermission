package com.zhangteng.androidpermission.request;

import android.content.Context;

import com.zhangteng.androidpermission.callback.Callback;

import java.util.Arrays;
import java.util.List;

/**
 * Created by swing on 2018/5/10.
 */
public class LRequest implements Request {
    private List<String> permissions;

    public LRequest(String... permissions) {
        this.permissions = Arrays.asList(permissions);
    }

    @Override
    public void requestPermissions(Context context, int permissionCode, Callback callback) {

    }
}
