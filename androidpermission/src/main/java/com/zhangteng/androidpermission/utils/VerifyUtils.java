package com.zhangteng.androidpermission.utils;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import com.zhangteng.androidpermission.Permission;

import java.util.Arrays;
import java.util.List;

/**
 * description: 权限版本校验工具
 * author: Swing
 * date: 2023/6/17
 */
public class VerifyUtils {
    /**
     * description 处理授权结果
     * 1、不需要处理的权限结果重置为PackageManager.PERMISSION_GRANTED；
     * 2、Android14（api34）READ_MEDIA_VISUAL_USER_SELECTED授权后认为READ_MEDIA_IMAGES、READ_MEDIA_VIDEO被授权
     *
     * @param permissions  权限列表
     * @param grantResults 授权结果
     * @return 处理后的授权结果
     */
    public static int[] grantResults(String[] permissions, int[] grantResults) {
        //如果授权结果与权限列表长度不一致直接返回grantResults
        if (grantResults.length != permissions.length) {
            return grantResults;
        }

        //如果不需要处理权限请求结果，将结果重置为授权PackageManager.PERMISSION_GRANTED
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                if (!isProcessResult(permissions[i])) {
                    grantResults[i] = PackageManager.PERMISSION_GRANTED;
                }
            }
        }

        //Android14 api34以下3个权限同时请求时READ_MEDIA_VISUAL_USER_SELECTED授权后认为READ_MEDIA_IMAGES、READ_MEDIA_VIDEO被授权
        //Permission.READ_MEDIA_IMAGES
        //Permission.READ_MEDIA_VIDEO
        //Permission.READ_MEDIA_VISUAL_USER_SELECTED
        List<String> permissionList = Arrays.asList(permissions);

        int readMediaVisualUserSelectedGrantResults = PackageManager.PERMISSION_DENIED;
        int readMediaVisualUserSelectedIndex = permissionList.indexOf(Permission.READ_MEDIA_VISUAL_USER_SELECTED);
        if (readMediaVisualUserSelectedIndex >= 0) {
            readMediaVisualUserSelectedGrantResults = grantResults[readMediaVisualUserSelectedIndex];
        }

        if (readMediaVisualUserSelectedGrantResults == PackageManager.PERMISSION_GRANTED) {

            int readMediaImageIndex = permissionList.indexOf(Permission.READ_MEDIA_IMAGES);
            int readMediaVideoIndex = permissionList.indexOf(Permission.READ_MEDIA_VIDEO);
            if (readMediaImageIndex >= 0) {
                grantResults[readMediaImageIndex] = PackageManager.PERMISSION_GRANTED;
            }
            if (readMediaVideoIndex >= 0) {
                grantResults[readMediaVideoIndex] = PackageManager.PERMISSION_GRANTED;
            }
        }
        return grantResults;
    }

    /**
     * description 是否需要处理该权限检测&显示权弹窗业务
     *
     * @param permission 权限
     * @return true 需要 false 不需要
     */
    public static boolean isProcess(String permission) {
        // 11 +READ_PHONE_NUMBERS
        // 11 +MANAGE_EXTERNAL_STORAGE
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

        // 14 +READ_MEDIA_VISUAL_USER_SELECTED
        if (Build.VERSION.SDK_INT >= 34) {
            //运行在Android14及以上时忽略14以上新增的权限与14及以下废除的权限请求失败
            if (!Permission.WRITE_EXTERNAL_STORAGE.equals(permission)
                    && !Permission.READ_EXTERNAL_STORAGE.equals(permission)) {
                return true;
            }
        } else if (Build.VERSION.SDK_INT == 33) {
            //运行在Android13及以上时忽略14及以上新增的权限与13及以下废除的权限请求失败
            if (!Permission.READ_MEDIA_VISUAL_USER_SELECTED.equals(permission)
                    && !Permission.WRITE_EXTERNAL_STORAGE.equals(permission)
                    && !Permission.READ_EXTERNAL_STORAGE.equals(permission)) {
                return true;
            }
        } else if (Build.VERSION.SDK_INT >= 31) {
            //运行在Android12及以上时忽略13及以上新增的权限与12级以下废除的权限请求失败
            if (!Permission.READ_MEDIA_VISUAL_USER_SELECTED.equals(permission)
                    && !Permission.READ_MEDIA_IMAGES.equals(permission)
                    && !Permission.READ_MEDIA_VIDEO.equals(permission)
                    && !Permission.READ_MEDIA_AUDIO.equals(permission)
                    && !Permission.NEARBY_WIFI_DEVICES.equals(permission)
                    && !Permission.BODY_SENSORS_BACKGROUND.equals(permission)
                    && !Permission.POST_NOTIFICATIONS.equals(permission)
                    && !Permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
                return true;
            }
        } else if (Build.VERSION.SDK_INT == 30) {
            //运行在Android11及以上时忽略12及以上新增的权限与11级以下废除的权限请求失败
            if (!Permission.READ_MEDIA_VISUAL_USER_SELECTED.equals(permission)
                    && !Permission.READ_MEDIA_IMAGES.equals(permission)
                    && !Permission.READ_MEDIA_VIDEO.equals(permission)
                    && !Permission.READ_MEDIA_AUDIO.equals(permission)
                    && !Permission.BLUETOOTH_SCAN.equals(permission)
                    && !Permission.BLUETOOTH_ADVERTISE.equals(permission)
                    && !Permission.BLUETOOTH_CONNECT.equals(permission)
                    && !Permission.NEARBY_WIFI_DEVICES.equals(permission)
                    && !Permission.BODY_SENSORS_BACKGROUND.equals(permission)
                    && !Permission.POST_NOTIFICATIONS.equals(permission)
                    && !Permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
                return true;
            }
        } else {
            //运行在Android10及以下时忽略10及以上新增权限请求失败
            if (!Permission.READ_MEDIA_VISUAL_USER_SELECTED.equals(permission)
                    && !Permission.READ_PHONE_NUMBERS.equals(permission)
                    && !Permission.READ_MEDIA_IMAGES.equals(permission)
                    && !Permission.READ_MEDIA_VIDEO.equals(permission)
                    && !Permission.READ_MEDIA_AUDIO.equals(permission)
                    && !Permission.BLUETOOTH_SCAN.equals(permission)
                    && !Permission.BLUETOOTH_ADVERTISE.equals(permission)
                    && !Permission.BLUETOOTH_CONNECT.equals(permission)
                    && !Permission.NEARBY_WIFI_DEVICES.equals(permission)
                    && !Permission.BODY_SENSORS_BACKGROUND.equals(permission)
                    && !Permission.POST_NOTIFICATIONS.equals(permission)
                    && !Permission.MANAGE_EXTERNAL_STORAGE.equals(permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * description 是否需要处理该权限请求结果业务，MANAGE_EXTERNAL_STORAGE（比较特殊，直接前往设置页面开启权限，无法统一方式请求，因此各个api要过滤MANAGE_EXTERNAL_STORAGE失败结果）
     *
     * @param permission 权限
     * @return true 需要 false 不需要
     */
    public static boolean isProcessResult(String permission) {
        if (isManageExternalStorage(permission)) {
            return false;
        }
        return isProcess(permission);
    }

    /**
     * description 是否有存储管理权限MANAGE_EXTERNAL_STORAGE，小于api 30或permissions不包含MANAGE_EXTERNAL_STORAGE时返回true
     *
     * @param permissions 权限列表
     * @return true 有权限 false 无权限
     */
    public static boolean hasManageExternalStorage(String... permissions) {
        if (Build.VERSION.SDK_INT >= 30) {
            if (isManageExternalStorage(permissions)) {
                return Environment.isExternalStorageManager();
            }
        }
        return true;
    }

    /**
     * description 是否 包含存储管理权限MANAGE_EXTERNAL_STORAGE
     *
     * @param permissions 权限列表
     * @return true 是 false 否
     */
    public static boolean isManageExternalStorage(String... permissions) {
        if (permissions.length == 1) {
            return Permission.MANAGE_EXTERNAL_STORAGE.equals(permissions[0]);
        } else {
            return Arrays.asList(permissions).contains(Permission.MANAGE_EXTERNAL_STORAGE);
        }
    }
}
