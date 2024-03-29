package com.zhangteng.androidpermission.source;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.zhangteng.androidpermission.callback.Callback;
import com.zhangteng.androidpermission.checker.Checker;
import com.zhangteng.androidpermission.rationale.Rationale;
import com.zhangteng.androidpermission.request.Request;
import com.zhangteng.androidpermission.setting.SettingService;

/**
 * Created by swing on 2018/5/10.
 */
public class AppCompatActivitySource implements Source {
    private AppCompatActivity appCompatActivity;

    public AppCompatActivitySource(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    @Override
    public Context getContext() {
        return appCompatActivity;
    }

    @Override
    public void startActivity(Intent intent) {
        getContext().startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        appCompatActivity.startActivityForResult(intent, requestCode);
    }

    @Override
    public boolean checkSelfPermission(Checker checker) {
        return checker.hasPermission(getContext());
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(Rationale rationale) {
        return rationale.shouldShowRequestPermissionRationale(getContext());
    }

    @Override
    public void requestPermissions(Request request, int permissionCode, Callback callback) {
        request.requestPermissions(getContext(), permissionCode, callback);
    }

    public void toSetting(SettingService settingService, int requestCode, Callback callback) {
        settingService.execute(getContext(), requestCode, callback);
    }
}
