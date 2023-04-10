package com.zhangteng.androidpermission.rationale;

import android.content.Context;

/**
 * Created by swing on 2018/5/10.
 */
public interface Rationale {
    String getRationaleStr();

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
    boolean shouldShowRequestPermissionRationale(Context context);
}
