package com.zhangteng.androidpermission;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.zhangteng.androidpermission.callback.Callback;
import com.zhangteng.androidpermission.checker.Checker;
import com.zhangteng.androidpermission.checker.StandardChecker;
import com.zhangteng.androidpermission.rationale.Rationale;
import com.zhangteng.androidpermission.rationale.StandardRationale;
import com.zhangteng.androidpermission.request.Request;
import com.zhangteng.androidpermission.request.RequestFactory;
import com.zhangteng.androidpermission.setting.PermissionSetting;
import com.zhangteng.androidpermission.setting.SettingService;
import com.zhangteng.androidpermission.source.ActivitySource;
import com.zhangteng.androidpermission.source.AppCompatActivitySource;
import com.zhangteng.androidpermission.source.FragmentActivitySource;
import com.zhangteng.androidpermission.source.FragmentSource;
import com.zhangteng.androidpermission.source.Source;
import com.zhangteng.androidpermission.source.SupportFragmentSource;
import com.zhangteng.androidpermission.utils.VerifyUtils;

import java.io.Serializable;

/**
 * Created by swing on 2018/5/10.
 */
public class AndroidPermission {
    private static final int PERMISSION_CODE = 1 << 15;
    private static final int SETTING_CODE = 2 << 15;
    private Source source;
    private Request request;
    private Rationale rationale;
    private Checker checker;
    private SettingService settingService;
    private Callback callback;

    public AndroidPermission(Builder builder) {
        setBuilder(builder);
    }

    private void setBuilder(Builder builder) {
        this.source = builder.source;
        this.checker = builder.checker;
        this.rationale = builder.rationale;
        this.request = builder.request;
        this.callback = builder.callback;
        this.settingService = builder.settingService;
    }

    /**
     * description 开始申请权限
     */
    public void execute() {
        if (!checkPermission()) {
            requestPermissions();
        } else {
            if (callback != null) {
                callback.nonExecution(null);
            }
        }
    }

    /**
     * description 重新开始申请权限（用于再次请求权限，如果shouldShowRequestPermissionRationale=true执行请求权限，否则跳转设置页面）
     */
    public void retryExecute() {
        if (!checkPermission()) {
            if (shouldShowRequestPermissionRationale()) {
                requestPermissions();
            } else {
                toSetting();
            }
        } else {
            if (callback != null) {
                callback.nonExecution(null);
            }
        }
    }

    /**
     * description 检测是否有权限
     *
     * @return 是否有相关权限
     */
    public boolean checkPermission() {
        return source.checkSelfPermission(checker);
    }

    /**
     * description  是否显示权限请求弹窗
     * 第一次请求权限时ActivityCompat.shouldShowRequestPermissionRationale=false;
     * 第一次请求权限被禁止，但未选择【不再提醒】ActivityCompat.shouldShowRequestPermissionRationale=true;
     * 允许某权限后ActivityCompat.shouldShowRequestPermissionRationale=false;
     * 禁止权限，并选中【禁止后不再询问】ActivityCompat.shouldShowRequestPermissionRationale=false；
     *
     * @return 是否显示权限请求弹窗
     */
    public boolean shouldShowRequestPermissionRationale() {
        return source.shouldShowRequestPermissionRationale(rationale);
    }

    /**
     * description 申请权限
     */
    public void requestPermissions() {
        source.requestPermissions(request, PERMISSION_CODE, callback);
    }

    /**
     * description 前往app设置页面
     */
    public void toSetting() {
        source.toSetting(settingService, SETTING_CODE, callback);
    }

    /**
     * description 自定义Request时接收权限结果，在activity的onRequestPermissionsResult中调用
     *
     * @param requestCode  请求code
     * @param permissions  权限
     * @param grantResults 权限结果
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (callback != null && source != null) {
            Activity activity = source.getContext() instanceof Activity ? (Activity) source.getContext() : null;
            if (requestCode == PERMISSION_CODE) {
                if (grantResults.length > 0) {
                    int[] processedGrantResults = VerifyUtils.grantResults(source.getContext(), permissions, grantResults);
                    for (int grantResult : processedGrantResults) {
                        if (grantResult == PackageManager.PERMISSION_DENIED) {
                            callback.failure(activity);
                            callback = null;
                            return;
                        }
                    }
                    callback.success(activity);
                } else {
                    callback.failure(activity);
                }
            } else {
                callback.nonExecution(activity);
            }
        }
    }

    /**
     * description 自定义Request时接收权限结果, 在activity的onActivityResult中调用
     */
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (callback != null && source != null) {
            Activity activity = source.getContext() instanceof Activity ? (Activity) source.getContext() : null;
            if (requestCode == PERMISSION_CODE) {
                if (VerifyUtils.hasManageExternalStorage(Permission.MANAGE_EXTERNAL_STORAGE)) {
                    requestPermissions();
                } else {
                    if (callback != null) {
                        callback.failure(activity);
                    }
                }
            }
        }
    }

    public static class Builder implements Serializable {
        private Source source;
        private Request request;
        private Rationale rationale;
        private Checker checker;
        private Callback callback;
        private SettingService settingService;

        public Builder with(Activity activity) {
            this.source = new ActivitySource(activity);
            return this;
        }

        public Builder with(AppCompatActivity activity) {
            this.source = new AppCompatActivitySource(activity);
            return this;
        }

        public Builder with(FragmentActivity activity) {
            this.source = new FragmentActivitySource(activity);
            return this;
        }

        public Builder with(Fragment fragment) {
            this.source = new FragmentSource(fragment);
            return this;
        }

        public Builder with(androidx.fragment.app.Fragment fragment) {
            this.source = new SupportFragmentSource(fragment);
            if (fragment instanceof Request) {
                request = (Request) fragment;
            }
            return this;
        }

        public Builder source(Source source) {
            this.source = source;
            return this;
        }

        public Builder checker(Checker checker) {
            this.checker = checker;
            return this;
        }

        public Builder request(Request request) {
            this.request = request;
            return this;
        }

        public Builder rationale(Rationale rationale) {
            this.rationale = rationale;
            return this;
        }

        public Builder callback(Callback callback) {
            this.callback = callback;
            return this;
        }

        public Builder settingService(SettingService settingService) {
            this.settingService = settingService;
            return this;
        }

        public Builder permission(String... permissions) {
            if (this.checker == null) {
                this.checker = new StandardChecker(permissions);
            }
            if (this.rationale == null) {
                this.rationale = new StandardRationale("", permissions);
            }
            if (this.request == null) {
                this.request = new RequestFactory().createRequest(permissions);
            }
            if (this.settingService == null) {
                this.settingService = new PermissionSetting(permissions);
            }
            return this;
        }

        public AndroidPermission build() {
            return new AndroidPermission(this);
        }
    }
}
