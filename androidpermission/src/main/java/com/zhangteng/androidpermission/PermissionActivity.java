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
                        if (grantResult == PackageManager.PERMISSION_DENIED) {
                            // 11 +READ_PHONE_NUMBERS
                            // 11 +MANAGE_EXTERNAL_STORAGE（比较特殊，直接前往设置页面开启权限，无法统一方式请求，因此各个api要过滤MANAGE_EXTERNAL_STORAGE失败结果）
                            // 11 -WRITE_EXTERNAL_STORAGE

                            // 12 +Permission.BLUETOOTH_SCAN
                            // 12 +Permission.BLUETOOTH_ADVERTISE
                            // 12 +Permission.BLUETOOTH_CONNECT

                            // 13 +READ_MEDIA_IMAGES
                            // 13 +READ_MEDIA_VIDEO
                            // 13 +READ_MEDIA_AUDIO
                            // 13 -READ_EXTERNAL_STORAGE
                            // 13 +NEARBY_WIFI_DEVICES
                            // 13 +BODY_SENSORS_BACKGROUND
                            // 13 +POST_NOTIFICATIONS
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                //运行在Android13及以上时忽略13及以上新增的权限与13及以下废除的权限请求失败
                                if (!Permission.WRITE_EXTERNAL_STORAGE.equals(permissions[i])
                                        && !Permission.READ_EXTERNAL_STORAGE.equals(permissions[i])
                                        && !Permission.MANAGE_EXTERNAL_STORAGE.equals(permissions[i])) {
                                    mcallback.failure(this);
                                    mcallback = null;
                                    finish();
                                    return;
                                }
                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                //运行在Android12及以上时忽略13及以上新增的权限与12级以下废除的权限请求失败
                                if (!Permission.READ_MEDIA_IMAGES.equals(permissions[i])
                                        && !Permission.READ_MEDIA_VIDEO.equals(permissions[i])
                                        && !Permission.READ_MEDIA_AUDIO.equals(permissions[i])
                                        && !Permission.NEARBY_WIFI_DEVICES.equals(permissions[i])
                                        && !Permission.BODY_SENSORS_BACKGROUND.equals(permissions[i])
                                        && !Permission.POST_NOTIFICATIONS.equals(permissions[i])
                                        && !Permission.WRITE_EXTERNAL_STORAGE.equals(permissions[i])
                                        && !Permission.MANAGE_EXTERNAL_STORAGE.equals(permissions[i])) {
                                    mcallback.failure(this);
                                    mcallback = null;
                                    finish();
                                    return;
                                }
                            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
                                //运行在Android11及以上时忽略12及以上新增的权限与11级以下废除的权限请求失败
                                if (!Permission.READ_MEDIA_IMAGES.equals(permissions[i])
                                        && !Permission.READ_MEDIA_VIDEO.equals(permissions[i])
                                        && !Permission.READ_MEDIA_AUDIO.equals(permissions[i])
                                        && !Permission.BLUETOOTH_SCAN.equals(permissions[i])
                                        && !Permission.BLUETOOTH_ADVERTISE.equals(permissions[i])
                                        && !Permission.BLUETOOTH_CONNECT.equals(permissions[i])
                                        && !Permission.NEARBY_WIFI_DEVICES.equals(permissions[i])
                                        && !Permission.BODY_SENSORS_BACKGROUND.equals(permissions[i])
                                        && !Permission.POST_NOTIFICATIONS.equals(permissions[i])
                                        && !Permission.WRITE_EXTERNAL_STORAGE.equals(permissions[i])
                                        && !Permission.MANAGE_EXTERNAL_STORAGE.equals(permissions[i])) {
                                    mcallback.failure(this);
                                    mcallback = null;
                                    finish();
                                    return;
                                }
                            } else {
                                //运行在Android10及以下时忽略10及以上新增权限请求失败
                                if (!Permission.READ_PHONE_NUMBERS.equals(permissions[i])
                                        && !Permission.READ_MEDIA_IMAGES.equals(permissions[i])
                                        && !Permission.READ_MEDIA_VIDEO.equals(permissions[i])
                                        && !Permission.READ_MEDIA_AUDIO.equals(permissions[i])
                                        && !Permission.BLUETOOTH_SCAN.equals(permissions[i])
                                        && !Permission.BLUETOOTH_ADVERTISE.equals(permissions[i])
                                        && !Permission.BLUETOOTH_CONNECT.equals(permissions[i])
                                        && !Permission.NEARBY_WIFI_DEVICES.equals(permissions[i])
                                        && !Permission.BODY_SENSORS_BACKGROUND.equals(permissions[i])
                                        && !Permission.POST_NOTIFICATIONS.equals(permissions[i])
                                        && !Permission.MANAGE_EXTERNAL_STORAGE.equals(permissions[i])) {
                                    mcallback.failure(this);
                                    mcallback = null;
                                    finish();
                                    return;
                                }
                            }
                        }
                    }
                    mcallback.success(this);
                } else {
                    mcallback.failure(this);
                }
            } else {
                mcallback.nonExecution(this);
            }
        }
        mcallback = null;
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == permissionsCode && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                requestPermissions(permissions, permissionsCode);
            } else {
                if (mcallback != null) {
                    mcallback.failure(this);
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
}
