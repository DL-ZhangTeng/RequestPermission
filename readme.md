# 6.0权限请求AndroidPermission

AndroidPermission是一个Android6.0+动态权限请求库。
[GitHub仓库地址](https://github.com/DL-ZhangTeng/RequestPermission)

## 引入

### gradle

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

implementation 'com.github.DL-ZhangTeng:RequestPermission:1.3.0'
```

## 使用默认透明背景PermissionActivity执行实际权限请求

```java
public class MainActivity extends AppCompatActivity {

    private AndroidPermission androidPermission;
    private String[] permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        permissions = new String[]{Permission.MANAGE_EXTERNAL_STORAGE, Permission.CAMERA};
        androidPermission = new AndroidPermission.Builder()
                .with(this)
                .permission(permissions)
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
}

```

## 使用自定义的页面执行实际请求（实现Request接口）

```java
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
        androidPermission = new AndroidPermission.Builder()
                .with(this)
                .request(this)
                .permission(permissions)
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
        if (Build.VERSION.SDK_INT >= 30) {
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

```

## 混淆

-keep public class com.zhangteng.**.*{ *; }

## 历史版本

| 版本     | 更新                     | 更新时间               |
|--------|------------------------|--------------------|
| v1.3.0 | 权限请求被拒绝再次请求权限跳转设置页面    | 2023/4/10 at 18:00 |
| v1.2.2 | Android12、13的新增权限兼容    | 2023/3/3 at 12:00  |
| v1.2.1 | Android11的存储写入权限拒绝结果过滤 | 2023/2/14 at 0:07  |
| v1.2.0 | 重新构建                   | 2022/1/26 at 12:42 |
| v1.1.5 | 兼容Android11的存储权限       | 2021/6/11 at 15:29 |
| v1.1.4 | 支持自定义Request           | 25 April 2021      |
| v1.1.0 | 迁移到androidx            | 22 July 2020       |
| v1.0.0 | 内存优化                   | 15 May 2020        |

## 赞赏

如果您喜欢AndroidPermission，或感觉AndroidPermission帮助到了您，可以点右上角“Star”支持一下，您的支持就是我的动力，谢谢您

## 联系我

邮箱：763263311@qq.com/ztxiaoran@foxmail.com

## License

MIT License

Copyright (c) [2020] [Swing]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
