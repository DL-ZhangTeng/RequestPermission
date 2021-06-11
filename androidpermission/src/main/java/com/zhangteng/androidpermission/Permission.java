package com.zhangteng.androidpermission;

/**
 * Created by swing on 2018/5/10.
 */
public class Permission {
    public static final String READ_CALENDAR = "android.permission.READ_CALENDAR";
    public static final String WRITE_CALENDAR = "android.permission.WRITE_CALENDAR";

    public static final String CAMERA = "android.permission.CAMERA";

    public static final String READ_CONTACTS = "android.permission.READ_CONTACTS";
    public static final String WRITE_CONTACTS = "android.permission.WRITE_CONTACTS";
    public static final String GET_ACCOUNTS = "android.permission.GET_ACCOUNTS";

    public static final String ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
    public static final String ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";

    public static final String RECORD_AUDIO = "android.permission.RECORD_AUDIO";

    public static final String READ_PHONE_STATE = "android.permission.READ_PHONE_STATE";
    public static final String CALL_PHONE = "android.permission.CALL_PHONE";
    public static final String READ_CALL_LOG = "android.permission.READ_CALL_LOG";
    public static final String WRITE_CALL_LOG = "android.permission.WRITE_CALL_LOG";
    public static final String ADD_VOICEMAIL = "com.android.voicemail.permission.ADD_VOICEMAIL";
    public static final String USE_SIP = "android.permission.USE_SIP";
    public static final String PROCESS_OUTGOING_CALLS = "android.permission.PROCESS_OUTGOING_CALLS";

    public static final String BODY_SENSORS = "android.permission.BODY_SENSORS";

    public static final String SEND_SMS = "android.permission.SEND_SMS";
    public static final String RECEIVE_SMS = "android.permission.RECEIVE_SMS";
    public static final String READ_SMS = "android.permission.READ_SMS";
    public static final String RECEIVE_WAP_PUSH = "android.permission.RECEIVE_WAP_PUSH";
    public static final String RECEIVE_MMS = "android.permission.RECEIVE_MMS";

    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    public static final String WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";

    /**
     * @description: 如果Android11存储权限与其它Android6权限同时请求时，只请求MANAGE_EXTERNAL_STORAGE权限，其它权限需要重新execute
     * (是否同时执行可以在自定义的Request中处理，在PermissionActivity的onCreate中互斥执行)
     */
    public static final String MANAGE_EXTERNAL_STORAGE = "android.permission.MANAGE_EXTERNAL_STORAGE";

    public static final class Group {
        public static final String[] CALENDAR = new String[]{
                Permission.READ_CALENDAR,
                Permission.WRITE_CALENDAR};

        public static final String[] CAMERA = new String[]{Permission.CAMERA};

        public static final String[] CONTACTS = new String[]{
                Permission.READ_CONTACTS,
                Permission.WRITE_CONTACTS,
                Permission.GET_ACCOUNTS};

        public static final String[] LOCATION = new String[]{
                Permission.ACCESS_FINE_LOCATION,
                Permission.ACCESS_COARSE_LOCATION};

        public static final String[] MICROPHONE = new String[]{Permission.RECORD_AUDIO};

        public static final String[] PHONE = new String[]{
                Permission.READ_PHONE_STATE,
                Permission.CALL_PHONE,
                Permission.READ_CALL_LOG,
                Permission.WRITE_CALL_LOG,
                Permission.ADD_VOICEMAIL,
                Permission.USE_SIP,
                Permission.PROCESS_OUTGOING_CALLS};

        public static final String[] SENSORS = new String[]{Permission.BODY_SENSORS};

        public static final String[] SMS = new String[]{
                Permission.SEND_SMS,
                Permission.RECEIVE_SMS,
                Permission.READ_SMS,
                Permission.RECEIVE_WAP_PUSH,
                Permission.RECEIVE_MMS};

        public static final String[] STORAGE = new String[]{
                Permission.READ_EXTERNAL_STORAGE,
                Permission.WRITE_EXTERNAL_STORAGE};

        /**
         * @description: android11请求存储权限时使用
         */
        public static final String[] STORAGE_R = new String[]{
                Permission.MANAGE_EXTERNAL_STORAGE};
    }

}
