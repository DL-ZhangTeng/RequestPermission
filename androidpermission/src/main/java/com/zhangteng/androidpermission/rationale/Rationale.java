package com.zhangteng.androidpermission.rationale;

import android.content.Context;

/**
 * Created by swing on 2018/5/10.
 */
public interface Rationale {
    String getRationaleStr();
    boolean shouldShowRequestPermissionRationale(Context context);
}
