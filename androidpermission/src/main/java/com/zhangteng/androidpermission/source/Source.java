package com.zhangteng.androidpermission.source;

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
public interface Source {

    Context getContext();

    void startActivity(Intent intent);

    void startActivityForResult(Intent intent, int requestCode);

    boolean checkSelfPermission(Checker checker);

    boolean shouldShowRequestPermissionRationale(Rationale rationale);

    void requestPermissions(Request request, int permissionCode, Callback callback);

    void toSetting(SettingService settingService);

    void toSetting(SettingService settingService, int requestcode);
}
