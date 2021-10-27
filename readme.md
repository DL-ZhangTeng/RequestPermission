# 6.0权限请求AndroidPermission
AndroidPermission是一个Android6.0+动态权限请求库。（该项目是3年前借鉴某位仁兄的代码下完成的权限请求库后来又经过了多次优化，但时间久远已经找不到该仁兄，如有代码侵权请联系我）
[GitHub仓库地址](https://github.com/DL-ZhangTeng/RequestPermission)
## 引入
### gradle
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

implementation 'com.github.DL-ZhangTeng:RequestPermission:1.1.0'
```

## 使用默认透明背景PermissionActivity执行实际权限请求

```java
       AndroidPermission androidPermission = new AndroidPermission.Buidler()
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
                    	//权限已通过，请求未执行
                        Toast.makeText(MainActivity.this, "nonExecution", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        androidPermission.excute();
```

```java
//有回调
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
```
## 使用自定义的页面执行实际请求（实现Request接口）

```java
public class MainActivity extends AppCompatActivity implements Request {

    private AndroidPermission androidPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && androidPermission.checkPermission()) {
            Toast.makeText(MainActivity.this, "从设置页返回", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View view) {
        androidPermission = new AndroidPermission.Buidler()
                .with(this)
                //自定义的Request
                .request(this)
                .permission(Permission.Group.CALENDAR)
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

    //execute()执行后调用的方法
    @Override
    public void requestPermissions(Context context, int permissionCode, Callback callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(Permission.Group.CALENDAR, permissionCode);
        }
    }
    //请求完成后调用的方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        androidPermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
```

## 混淆
-keep public class com.zhangteng.**.*{ *; }
## 历史版本
版本| 更新| 更新时间
-------- | ----- | -----
v1.1.4| 支持自定义Request|25 April 2021
v1.1.0| 迁移到androidx|22 July 2020
v1.0.0| 内存优化| 15 May 2020

## 赞赏
如果您喜欢AndroidPermission，或感觉AndroidPermission帮助到了您，可以点右上角“Star”支持一下，您的支持就是我的动力，谢谢您也可以扫描下面的二维码，请作者喝杯茶 tea

![支付宝收款码](https://img-blog.csdnimg.cn/20200807160902219.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2R1b2x1bzk=,size_16,color_FFFFFF,t_70)
![微信收款码](https://img-blog.csdnimg.cn/20200807160902112.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2R1b2x1bzk=,size_16,color_FFFFFF,t_70)

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
