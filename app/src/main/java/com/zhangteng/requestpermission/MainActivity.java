package com.zhangteng.requestpermission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.zhangteng.androidpermission.AndroidPermission;
import com.zhangteng.androidpermission.Permission;
import com.zhangteng.androidpermission.callback.Callback;
import com.zhangteng.androidpermission.request.Request;
import com.zhangteng.androidpermission.utils.VerifyUtils;
import com.zhangteng.utils.SPUtilsKt;

public class MainActivity extends AppCompatActivity implements Request {

    private AndroidPermission androidPermission;
    private String[] permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        permissions = new String[]{Permission.READ_MEDIA_IMAGES, Permission.READ_MEDIA_VIDEO};
        androidPermission = new AndroidPermission.Builder()
                .with(this)
//                .request(this)
                .permission(Permission.Group.STORAGE)
                .callback(new Callback() {
                    @Override
                    public void success(Activity activity) {
                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(Activity activity) {
                        Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void nonExecution(Activity activity) {
                        Toast.makeText(MainActivity.this, "nonExecution", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        Boolean isRequestPermissions = (Boolean) SPUtilsKt.getFromSP(this, "isRequestPermissions", false, "currentUser");
        if (Boolean.TRUE.equals(isRequestPermissions)) {
            //用于再次请求权限
            androidPermission.retryExecute();
        } else {
            //用于初次请求权限
            androidPermission.execute();
            SPUtilsKt.putToSP(this, "isRequestPermissions", true, "currentUser");
        }
    }

    @Override
    public void requestPermissions(Context context, int permissionCode, Callback callback) {
        //如果Android11存储权限与其它Android6权限同时请求时，先请求MANAGE_EXTERNAL_STORAGE权限
        if (VerifyUtils.hasManageExternalStorage(permissions)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, permissionCode);
            }
        } else {
            Intent intent1 = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            intent1.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent1, permissionCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        androidPermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //自定义request时执行结果
        androidPermission.onActivityResult(requestCode, resultCode, data);
    }
}
