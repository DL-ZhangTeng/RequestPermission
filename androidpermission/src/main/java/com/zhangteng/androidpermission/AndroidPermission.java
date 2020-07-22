package com.zhangteng.androidpermission;

import android.app.Activity;
import android.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;

import com.zhangteng.androidpermission.callback.Callback;
import com.zhangteng.androidpermission.checker.Checker;
import com.zhangteng.androidpermission.checker.StandardChecker;
import com.zhangteng.androidpermission.rationale.Rationale;
import com.zhangteng.androidpermission.rationale.StandardRationale;
import com.zhangteng.androidpermission.request.Request;
import com.zhangteng.androidpermission.request.RequestFactory;
import com.zhangteng.androidpermission.setting.PermissionSetting;
import com.zhangteng.androidpermission.setting.SettingService;
import com.zhangteng.androidpermission.source.ActivityScource;
import com.zhangteng.androidpermission.source.AppCompatActivitySource;
import com.zhangteng.androidpermission.source.FragmentActivitySource;
import com.zhangteng.androidpermission.source.FragmentSource;
import com.zhangteng.androidpermission.source.Sourse;
import com.zhangteng.androidpermission.source.SupportFragmentSource;

import java.io.Serializable;

/**
 * Created by swing on 2018/5/10.
 */
public class AndroidPermission {
    private Sourse sourse;
    private Request request;
    private Rationale rationale;
    private Checker checker;
    private SettingService settingService;
    private Callback callback;

    private Buidler buidler = null;

    private static final int PERMISSION_CODE = 110000;

    public AndroidPermission(Buidler buidler) {
        setBuidler(buidler);
    }

    private void setBuidler(Buidler buidler) {
        this.sourse = buidler.sourse;
        this.checker = buidler.checker;
        this.rationale = buidler.rationale;
        this.request = buidler.request;
        this.callback = buidler.callback;
        this.settingService = buidler.settingService;
        this.buidler = buidler;
    }

    public void excute() {
        if (!sourse.checkSelfPermission(checker)) {
            sourse.requestPermissions(request, PERMISSION_CODE, callback);
        } else {
            if (callback != null) {
                callback.nonExecution();
            }
        }
    }

    public void excute(int requestCode) {
        if (!sourse.checkSelfPermission(checker)) {
            if (sourse.shouldShowRequestPermissionRationale(rationale)) {
                sourse.requestPermissions(request, PERMISSION_CODE, callback);
            } else {
                sourse.toSetting(settingService, requestCode);
            }
        } else {
            if (callback != null) {
                callback.nonExecution();
            }
        }
    }

    public boolean checkPermission() {
        return sourse.checkSelfPermission(checker);
    }

    public static class Buidler implements Serializable {
        private Sourse sourse;
        private Request request;
        private Rationale rationale;
        private Checker checker;
        private Callback callback;
        private SettingService settingService;

        public Buidler with(Activity activity) {
            this.sourse = new ActivityScource(activity);
            return this;
        }

        public Buidler with(AppCompatActivity activity) {
            this.sourse = new AppCompatActivitySource(activity);
            return this;
        }

        public Buidler with(FragmentActivity activity) {
            this.sourse = new FragmentActivitySource(activity);
            return this;
        }

        public Buidler with(Fragment fragment) {
            this.sourse = new FragmentSource(fragment);
            return this;
        }

        public Buidler with(androidx.fragment.app.Fragment fragment) {
            this.sourse = new SupportFragmentSource(fragment);
            return this;
        }

        public Buidler sourse(Sourse sourse) {
            this.sourse = sourse;
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
                this.request = new RequestFactory().creatRequest(permissions);
            }
            return this;
        }

        public AndroidPermission build() {
            if (this.settingService == null) {
                this.settingService = new PermissionSetting(sourse);
            }
            return new AndroidPermission(this);
        }
    }
}
