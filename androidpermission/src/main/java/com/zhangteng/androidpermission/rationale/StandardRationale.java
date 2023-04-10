package com.zhangteng.androidpermission.rationale;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import java.util.Arrays;
import java.util.List;

/**
 * Created by swing on 2018/5/10.
 */
public class StandardRationale implements Rationale {
    private List<String> permissions;
    private String rationale;

    public StandardRationale(String rationale, String... permissions) {
        this.rationale = rationale;
        this.permissions = Arrays.asList(permissions);
    }

    @Override
    public String getRationaleStr() {
        return rationale;
    }

    /**
     * description  是否显示权限请求弹窗
     *              第一次请求权限时ActivityCompat.shouldShowRequestPermissionRationale=false;
     *              第一次请求权限被禁止，但未选择【不再提醒】ActivityCompat.shouldShowRequestPermissionRationale=true;
     *              允许某权限后ActivityCompat.shouldShowRequestPermissionRationale=false;
     *              禁止权限，并选中【禁止后不再询问】ActivityCompat.shouldShowRequestPermissionRationale=false；
     *
     * @param context 上下文
     * @return 是否显示权限请求弹窗
     */
    @Override
    public boolean shouldShowRequestPermissionRationale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context instanceof Activity) {
                for (String permission : permissions) {
                    if (((Activity) context).shouldShowRequestPermissionRationale(permission)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
