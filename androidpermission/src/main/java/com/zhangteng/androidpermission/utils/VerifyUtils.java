package com.zhangteng.androidpermission.utils;

import android.content.Context;
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
     * @param context      上下文
     * @param permissions  权限列表
     * @param grantResults 授权结果
     * @return 处理后的授权结果
     */
    public static int[] grantResults(Context context, String[] permissions, int[] grantResults) {
        //如果授权结果与权限列表长度不一致直接返回grantResults
        if (grantResults.length != permissions.length) {
            return grantResults;
        }

        //如果不需要处理权限请求结果，将结果重置为授权PackageManager.PERMISSION_GRANTED
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                if (!isProcessResult(context, permissions[i])) {
                    grantResults[i] = PackageManager.PERMISSION_GRANTED;
                }
            }
        }

        //1、从Android 14开始，如果您manifest声明了 READ_MEDIA_VISUAL_USER_SELECTED 权限，并且用户在系统权限对话框中选择了选择照片和视频，则会发生以下行为：
        //READ_MEDIA_IMAGES 和 READ_MEDIA_VIDEO 权限都会被拒绝。
        //会授予 READ_MEDIA_VISUAL_USER_SELECTED 权限，可提供对用户照片和视频的部分和临时访问权限。
        //如果您的应用需要访问其他照片和视频，您必须再次手动请求 READ_MEDIA_IMAGES 权限和/或 READ_MEDIA_VIDEO 权限。
        //2、从Android 14开始，如果您manifest未声明 READ_MEDIA_VISUAL_USER_SELECTED 权限，则会发生以下行为：
        //在应用会话期间会授予 READ_MEDIA_IMAGES 和 READ_MEDIA_VIDEO 权限，从而提供对用户选择的照片和视频的临时授权和临时访问权限。当您的应用转到后台或当用户主动终止您的应用时，系统最终会拒绝这些权限。此行为就像其他单次授权一样。
        //如果您的应用稍后需要访问其他照片和视频，您必须再次手动请求 READ_MEDIA_IMAGES 权限或 READ_MEDIA_VIDEO 权限。系统遵循与初始权限请求相同的流程，提示用户选择照片和视频。

        //以下处理第一条manifest声明了 READ_MEDIA_VISUAL_USER_SELECTED 权限，并且用户在系统权限对话框中选择了选择照片和视频场景
        //Android14(api34) READ_MEDIA_VISUAL_USER_SELECTED授权后认为READ_MEDIA_IMAGES、READ_MEDIA_VIDEO被授权
        List<String> permissionList = Arrays.asList(permissions);

        int readMediaVisualUserSelectedGrantResults;

        int readMediaVisualUserSelectedIndex = permissionList.indexOf(Permission.READ_MEDIA_VISUAL_USER_SELECTED);
        if (readMediaVisualUserSelectedIndex >= 0) {
            //如果请求了READ_MEDIA_VISUAL_USER_SELECTED权限，直接获取请求结果
            readMediaVisualUserSelectedGrantResults = grantResults[readMediaVisualUserSelectedIndex];
        } else {
            //如果未请求READ_MEDIA_VISUAL_USER_SELECTED权限，检测权限授予情况
            readMediaVisualUserSelectedGrantResults = context.checkPermission(Permission.READ_MEDIA_VISUAL_USER_SELECTED, android.os.Process.myPid(), android.os.Process.myUid());
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
     * description 是否需要处理该权限请求结果业务，MANAGE_EXTERNAL_STORAGE（比较特殊，直接前往设置页面开启权限，无法统一方式请求，因此各个api要过滤MANAGE_EXTERNAL_STORAGE失败结果）
     *
     * @param permission 权限
     * @return true 需要 false 不需要
     */
    public static boolean isProcessResult(Context context, String permission) {
        if (isManageExternalStorage(permission)) {
            return false;
        }
        return isProcess(context, permission);
    }

    /**
     * description 是否需要处理该权限(权限检测&显示权弹窗&请求结果)
     *
     * @param permission 权限
     * @return true 需要 false 不需要
     */
    public static boolean isProcess(Context context, String permission) {
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
        int devicesVersion = Build.VERSION.SDK_INT;
        int targetSdkVersion = context.getApplicationInfo().targetSdkVersion;
        int minVersion = Math.min(devicesVersion, targetSdkVersion);
        if (devicesVersion >= 34) {
            //运行在Android14及以上时忽略targetSdkVersion及以下废除的权限请求失败
            return !isRemovedPermission(minVersion, permission);
        } else if (devicesVersion == 33) {
            //运行在Android13及以上时忽略14及以上新增的权限与targetSdkVersion及以下废除的权限请求失败
            return !isAddedPermission(34, permission) && !isRemovedPermission(minVersion, permission);
        } else if (devicesVersion >= 31) {
            //运行在Android12及以上时忽略13及以上新增的权限与targetSdkVersion及以下废除的权限请求失败
            return !isAddedPermission(33, permission) && !isRemovedPermission(minVersion, permission);
        } else if (devicesVersion == 30) {
            //运行在Android11及以上时忽略12及以上新增的权限与targetSdkVersion及以下废除的权限请求失败
            return !isAddedPermission(31, permission) && !isRemovedPermission(minVersion, permission);
        } else {
            //运行在Android10及以下时忽略11及以上新增权限请求失败
            return !isAddedPermission(30, permission);
        }
    }

    /**
     * description 是否是api版本及以上新增的权限
     *
     * @param apiVersion api版本
     * @param permission 权限
     * @return true 新增权限 false 不是新增权限
     */
    public static boolean isAddedPermission(int apiVersion, String permission) {
        switch (apiVersion) {
            case 30:
                // 11 +READ_PHONE_NUMBERS
                // 11 +MANAGE_EXTERNAL_STORAGE
                if (Permission.READ_PHONE_NUMBERS.equals(permission)
                        || Permission.MANAGE_EXTERNAL_STORAGE.equals(permission)) {
                    return true;
                }
            case 31:
            case 32:
                // 12 +Permission.BLUETOOTH_SCAN
                // 12 +Permission.BLUETOOTH_ADVERTISE
                // 12 +Permission.BLUETOOTH_CONNECT
                if (Permission.BLUETOOTH_SCAN.equals(permission)
                        || Permission.BLUETOOTH_ADVERTISE.equals(permission)
                        || Permission.BLUETOOTH_CONNECT.equals(permission)) {
                    return true;
                }
            case 33:
                // 13 +READ_MEDIA_IMAGES
                // 13 +READ_MEDIA_VIDEO
                // 13 +READ_MEDIA_AUDIO
                // 13 +NEARBY_WIFI_DEVICES
                // 13 +BODY_SENSORS_BACKGROUND
                // 13 +POST_NOTIFICATIONS
                if (Permission.READ_MEDIA_IMAGES.equals(permission)
                        || Permission.READ_MEDIA_VIDEO.equals(permission)
                        || Permission.READ_MEDIA_AUDIO.equals(permission)
                        || Permission.NEARBY_WIFI_DEVICES.equals(permission)
                        || Permission.BODY_SENSORS_BACKGROUND.equals(permission)
                        || Permission.POST_NOTIFICATIONS.equals(permission)) {
                    return true;
                }
            case 34:
                // 14 +READ_MEDIA_VISUAL_USER_SELECTED
                if (Permission.READ_MEDIA_VISUAL_USER_SELECTED.equals(permission)) {
                    return true;
                }
        }
        return false;
    }

    /**
     * description 是否是api版本及以下被移除的权限
     *
     * @param apiVersion api版本
     * @param permission 权限
     * @return true 新增权限 false 不是新增权限
     */
    public static boolean isRemovedPermission(int apiVersion, String permission) {
        switch (apiVersion) {
            case 34:
            case 33:
                // 13 -READ_EXTERNAL_STORAGE
                if (Permission.READ_EXTERNAL_STORAGE.equals(permission)) {
                    return true;
                }
            case 32:
            case 31:
            case 30:
                // 11 -WRITE_EXTERNAL_STORAGE
                if (Permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
                    return true;
                }
        }
        return false;
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
