package com.zhangteng.requestpermission;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.zhangteng.androidpermission.AndroidPermission;
import com.zhangteng.androidpermission.Permission;
import com.zhangteng.androidpermission.callback.Callback;

public class MainActivity extends AppCompatActivity {

    private AndroidPermission androidPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        androidPermission = new AndroidPermission.Buidler()
                .with(this)
                .permission(Permission.Group.CALENDAR)
                .callback(new Callback() {
                    @Override
                    public void success() {
                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure() {
                        Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void nonExecution() {
                        Toast.makeText(MainActivity.this, "nonExecution", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        //用于初次请求权限
        androidPermission.excute();
        //用于再次请求权限
        androidPermission.excute(100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && androidPermission.checkPermission()) {
            Toast.makeText(MainActivity.this, "从设置页返回", Toast.LENGTH_SHORT).show();
        }
    }
}
