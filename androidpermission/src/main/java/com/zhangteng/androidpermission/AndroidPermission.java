package com.zhangteng.androidpermission;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

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

import java.io.Serializable;

/**
 * Created by swing on 2018/5/10.
 */
public class AndroidPermission {
    private static final int PERMISSION_CODE = 1 << 15;
    private Source source;
    private Request request;
    private Rationale rationale;
    private Checker checker;
    private SettingService settingService;
    private Callback callback;

    public AndroidPermission(Buidler buidler) {
        setBuidler(buidler);
    }

    private void setBuidler(Buidler buidler) {
        this.source = buidler.source;
        this.checker = buidler.checker;
        this.rationale = buidler.rationale;
        this.request = buidler.request;
        this.callback = buidler.callback;
        this.settingService = buidler.settingService;
    }

    public void execute() {
        if (!checkPermission()) {
            requestPermissions();
        } else {
            if (callback != null) {
                callback.nonExecution(null);
            }
        }
    }

    public void execute(int settingRequestCode) {
        if (!checkPermission()) {
            if (shouldShowRequestPermissionRationale()) {
                requestPermissions();
            } else {
                toSetting(settingRequestCode);
            }
        } else {
            if (callback != null) {
                callback.nonExecution(null);
            }
        }
    }

    public boolean checkPermission() {
        return source.checkSelfPermission(checker);
    }

    public boolean shouldShowRequestPermissionRationale() {
        return source.shouldShowRequestPermissionRationale(rationale);
    }

    public void requestPermissions() {
        source.requestPermissions(request, PERMISSION_CODE, callback);
    }

    public void toSetting(int settingRequestCode) {
        source.toSetting(settingService, settingRequestCode);
    }

    /**
     * @param requestCode  请求code
     * @param permissions  权限
     * @param grantResults 权限结果
     * @return void
     * @description 自定义Request时接收权限结果，在activity的onRequestPermissionsResult中调用
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (callback != null && source != null) {
            Activity activity = source.getContext() instanceof Activity ? (Activity) source.getContext() : null;
            if (requestCode == PERMISSION_CODE) {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        if (grantResult == PackageManager.PERMISSION_DENIED && !Permission.MANAGE_EXTERNAL_STORAGE.equals(permissions[i])) {
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
     * @description 自定义Request时接收权限结果, 在activity的onActivityResult中调用
     */
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (callback != null && source != null) {
            Activity activity = source.getContext() instanceof Activity ? (Activity) source.getContext() : null;
            if (requestCode == PERMISSION_CODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    if (callback != null) {
                        callback.success(activity);
                    }
                } else {
                    if (callback != null) {
                        callback.failure(activity);
                    }
                }
            }
        }
    }

    public static class Buidler implements Serializable {
        private Source source;
        private Request request;
        private Rationale rationale;
        private Checker checker;
        private Callback callback;
        private SettingService settingService;

        public Buidler with(Activity activity) {
            this.source = new ActivitySource(activity);
            return this;
        }

        public Buidler with(AppCompatActivity activity) {
            this.source = new AppCompatActivitySource(activity);
            return this;
        }

        public Buidler with(FragmentActivity activity) {
            this.source = new FragmentActivitySource(activity);
            return this;
        }

        public Buidler with(Fragment fragment) {
            this.source = new FragmentSource(fragment);
            return this;
        }

        public Buidler with(androidx.fragment.app.Fragment fragment) {
            this.source = new SupportFragmentSource(fragment);
            if (fragment instanceof Request) {
                request = (Request) fragment;
            }
            return this;
        }

        public Buidler source(Source source) {
            this.source = source;
            return this;
        }

        public Buidler checker(Checker checker) {
            this.checker = checker;
            return this;
        }

        public Buidler request(Request request) {
            this.request = request;
            return this;
        }

        public Buidler rationable(Rationale rationale) {
            this.rationale = rationale;
            return this;
        }

        public Buidler callback(Callback callback) {
            this.callback = callback;
            return this;
        }

        public Buidler settingService(SettingService settingService) {
            this.settingService = settingService;
            return this;
        }

        public Buidler permission(String... permissions) {
            if (this.checker == null) {
                this.checker = new StandardChecker(permissions);
            }
            if (this.rationale == null) {
                this.rationale = new StandardRationale("", permissions);
            }
            if (this.request == null) {
                this.request = new RequestFactory().createRequest(permissions);
            }
            return this;
        }

        public AndroidPermission build() {
            if (this.settingService == null) {
                this.settingService = new PermissionSetting(source);
            }
            return new AndroidPermission(this);
        }
    }
}
