package com.zhangteng.androidpermission;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.zhangteng.androidpermission.callback.Callback;
import com.zhangteng.androidpermission.checker.StandardChecker;
import com.zhangteng.androidpermission.utils.VerifyUtils;

/**
 * Created by swing on 2018/5/10.
 */
public final class SettingActivity extends Activity {
    private static final String MARK = Build.MANUFACTURER.toLowerCase();
    private static final String KEY_INPUT_PERMISSIONS = "KEY_INPUT_PERMISSIONS";
    private static final String KEY_SETTINGCODE = "KEY_SETTINGCODE";
    private static Callback mcallback;
    private int settingCode;
    private String[] permissions;

    public static void toSetting(Context context, String[] permissions, int settingCode, Callback callback) {
        mcallback = callback;
        Intent intent = new Intent(context, SettingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KEY_INPUT_PERMISSIONS, permissions);
        intent.putExtra(KEY_SETTINGCODE, settingCode);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        invasionStatusBar(this);
        Intent intent = getIntent();
        permissions = intent.getStringArrayExtra(KEY_INPUT_PERMISSIONS);
        settingCode = intent.getIntExtra(KEY_SETTINGCODE, 1);
        if (permissions != null && mcallback != null) {
            if (VerifyUtils.hasManageExternalStorage(permissions)) {
                toSettingPage();
            } else {
                Intent intent1 = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent1.setData(Uri.parse("package:" + SettingActivity.this.getPackageName()));
                startActivityForResult(intent1, settingCode);
            }
        } else {
            mcallback = null;
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == settingCode && Build.VERSION.SDK_INT >= 30) {
            if (new StandardChecker(permissions).hasPermission(this)) {
                if (mcallback != null) {
                    mcallback.success(this);
                }
            } else {
                if (Environment.isExternalStorageManager()) {
                    toSettingPage();
                } else {
                    if (mcallback != null) {
                        mcallback.failure(this);
                    }
                }
            }
        }
        mcallback = null;
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Set the content layout full the StatusBar, but do not hide StatusBar.
     */
    private static void invasionStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility()
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void toSettingPage() {
        Intent intent = obtainSettingIntent();
        try {
            startActivityForResult(intent, settingCode);
        } catch (Exception e) {
            startActivityForResult(defaultApi(this), settingCode);
        }
    }

    private Intent obtainSettingIntent() {
        if (MARK.contains("huawei")) {
            return huaweiApi(this);
        } else if (MARK.contains("xiaomi")) {
            return xiaomiApi(this);
        } else if (MARK.contains("oppo")) {
            return oppoApi(this);
        } else if (MARK.contains("vivo")) {
            return vivoApi(this);
        } else if (MARK.contains("samsung")) {
            return samsungApi(this);
        } else if (MARK.contains("meizu")) {
            return meizuApi(this);
        } else if (MARK.contains("smartisan")) {
            return smartisanApi(this);
        }
        return defaultApi(this);
    }

    /**
     * App details page.
     */
    private Intent defaultApi(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        return intent;
    }

    /**
     * Huawei cell phone Api23 the following method.
     */
    private Intent huaweiApi(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return defaultApi(context);
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
        return intent;
    }

    /**
     * Xiaomi phone to achieve the method.
     */
    private Intent xiaomiApi(Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.putExtra("extra_pkgname", context.getPackageName());
        return intent;
    }

    /**
     * Vivo phone to achieve the method.
     */
    private Intent vivoApi(Context context) {
        Intent intent = new Intent();
        intent.putExtra("packagename", context.getPackageName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity"));
        } else {
            intent.setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.safeguard.SoftPermissionDetailActivity"));
        }
        return intent;
    }

    /**
     * Oppo phone to achieve the method.
     */
    private Intent oppoApi(Context context) {
        return defaultApi(context);
    }

    /**
     * Meizu phone to achieve the method.
     */
    private Intent meizuApi(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            return defaultApi(context);
        }
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.putExtra("packageName", context.getPackageName());
        intent.setComponent(new ComponentName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity"));
        return intent;
    }

    /**
     * Smartisan phone to achieve the method.
     */
    private Intent smartisanApi(Context context) {
        return defaultApi(context);
    }

    /**
     * Samsung phone to achieve the method.
     */
    private Intent samsungApi(Context context) {
        return defaultApi(context);
    }
}
