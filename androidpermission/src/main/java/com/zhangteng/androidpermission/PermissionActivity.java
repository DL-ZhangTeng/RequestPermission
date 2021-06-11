package com.zhangteng.androidpermission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.zhangteng.androidpermission.callback.Callback;

import java.util.Arrays;
import java.util.List;

/**
 * Created by swing on 2018/5/10.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public final class PermissionActivity extends Activity {

    private static final String KEY_INPUT_PERMISSIONS = "KEY_INPUT_PERMISSIONS";
    private static final String KEY_PERMISSIONCODE = "KEY_PERMISSIONSCODE";
    private static Callback mcallback;
    private int permissionsCode;
    private String[] permissions;

    /**
     * Request for permissions.
     */

    public static void requestPermission(Context context, String[] permissions, int permissionCode, Callback callback) {
        mcallback = callback;
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KEY_INPUT_PERMISSIONS, permissions);
        intent.putExtra(KEY_PERMISSIONCODE, permissionCode);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        invasionStatusBar(this);
        Intent intent = getIntent();
        permissions = intent.getStringArrayExtra(KEY_INPUT_PERMISSIONS);
        permissionsCode = intent.getIntExtra(KEY_PERMISSIONCODE, 1);
        if (permissions != null && mcallback != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                List<String> permissionsList = Arrays.asList(permissions);
                if (permissionsList.contains(Permission.MANAGE_EXTERNAL_STORAGE)) {
                    if (Environment.isExternalStorageManager()) {
                        requestPermissions(permissions, permissionsCode);
                    } else {
                        Intent intent1 = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                        intent1.setData(Uri.parse("package:" + PermissionActivity.this.getPackageName()));
                        startActivityForResult(intent1, permissionsCode);
                    }
                } else {
                    requestPermissions(permissions, permissionsCode);
                }
            } else {
                requestPermissions(permissions, permissionsCode);
            }
        } else {
            mcallback = null;
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mcallback != null) {
            if (requestCode == permissionsCode) {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        if (grantResult == PackageManager.PERMISSION_DENIED && !Permission.MANAGE_EXTERNAL_STORAGE.equals(permissions[i])) {
                            mcallback.failure(this);
                            mcallback = null;
                            finish();
                            return;
                        }
                    }
                    mcallback.success(this);
                } else {
                    mcallback.failure(this);
                }
            } else {
                mcallback.nonExecution(this);
            }
            mcallback = null;
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == permissionsCode && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                if (mcallback != null) {
                    mcallback.success(this);
                }
            } else {
                if (mcallback != null) {
                    mcallback.failure(this);
                }
            }
        }
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
}
