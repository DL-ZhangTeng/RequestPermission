package com.zhangteng.androidpermission.source;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.zhangteng.androidpermission.callback.Callback;
import com.zhangteng.androidpermission.checker.Checker;
import com.zhangteng.androidpermission.rationale.Rationale;
import com.zhangteng.androidpermission.request.Request;
import com.zhangteng.androidpermission.setting.SettingService;

/**
 * Created by swing on 2018/5/10.
 */
public class FragmentSource implements Source {
    private Fragment fragment;

    public FragmentSource(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public Context getContext() {
        return fragment.getActivity();
    }

    @Override
    public void startActivity(Intent intent) {
        getContext().startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        fragment.startActivityForResult(intent, requestCode);
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

    @Override
    public void toSetting(SettingService settingService) {
        settingService.execute();
    }

    @Override
    public void toSetting(SettingService settingService, int requestcode) {
        settingService.execute(requestcode);
    }
}
