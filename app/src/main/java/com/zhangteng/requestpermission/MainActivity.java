package com.zhangteng.requestpermission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.zhangteng.androidpermission.AndroidPermission;
import com.zhangteng.androidpermission.Permission;
import com.zhangteng.androidpermission.callback.Callback;
import com.zhangteng.androidpermission.request.Request;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Request {

    private AndroidPermission androidPermission;
    private String[] permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        permissions = new String[]{Permission.MANAGE_EXTERNAL_STORAGE, Permission.CAMERA};
        androidPermission = new AndroidPermission.Buidler()
                .with(this)
//                .request(this)
                .permission(permissions)
                .callback(new Callback() {
                    @Override
                    public void success(Activity permissionActivity) {
                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(Activity permissionActivity) {
                        Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void nonExecution(Activity permissionActivity) {
                        Toast.makeText(MainActivity.this, "nonExecution", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        //用于初次请求权限
        androidPermission.execute();
        //用于再次请求权限
//        androidPermission.execute(100);
    }

    @Override
    public void requestPermissions(Context context, int permissionCode, Callback callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            List<String> permissionsList = Arrays.asList(permissions);
            if (permissionsList.contains(Permission.MANAGE_EXTERNAL_STORAGE)) {
                //如果Android11存储权限与其它Android6权限同时请求时，只请求MANAGE_EXTERNAL_STORAGE权限，其它权限需要重新execute
                if (Environment.isExternalStorageManager()) {
                    requestPermissions(permissions, permissionCode);
                } else {
                    Intent intent1 = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent1.setData(Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent1, permissionCode);
                }
            } else {
                requestPermissions(permissions, permissionCode);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, permissionCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        androidPermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && androidPermission.checkPermission()) {
            Toast.makeText(MainActivity.this, "从设置页返回", Toast.LENGTH_SHORT).show();
        }
        //自定义request时执行结果
        androidPermission.onActivityResult(requestCode, resultCode, data);
    }
}
