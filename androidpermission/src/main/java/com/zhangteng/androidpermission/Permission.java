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
    /**
     * description: 如果您的应用以 Android 11 或更高版本为目标平台，并且需要访问以下列表中显示的电话号码 API，则必须请求 READ_PHONE_NUMBERS 权限，而不是 READ_PHONE_STATE 权限。
     *              <p>
     *              TelephonyManager 类和 TelecomManager 类中的 getLine1Number() 方法。
     *              TelephonyManager 类中不受支持的 getMsisdn() 方法。
     *              如果您的应用声明 READ_PHONE_STATE 以调用前面列表中的方法以外的方法，您可以继续在所有 Android 版本中请求 READ_PHONE_STATE。不过，如果您仅对前面列表中的方法使用 READ_PHONE_STATE 权限，请按以下方式更新您的清单文件：
     *              <p>
     *              更改 READ_PHONE_STATE 的声明，以使您的应用仅在 Android 10（API 级别 29）及更低版本中使用该权限。
     *              添加 READ_PHONE_NUMBERS 权限。
     *              <p>
     *              以下清单声明代码段演示了此过程：
     *              <manifest>
     *                  <!-- Grants the READ_PHONE_STATE permission only on devices that run
     *                          Android 10 (API level 29) and lower. -->
     *                  <uses-permission android:name="READ_PHONE_STATE"
     *                                      android:maxSdkVersion="29" />
     *                  <uses-permission android:name="READ_PHONE_NUMBERS" />
     *              </manifest>
     */
    public static final String READ_PHONE_NUMBERS = "android.permission.READ_PHONE_NUMBERS";
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
    /**
     * description: 写入权限在Android11中被废弃，Android10中可以通过requestLegacyExternalStorage关闭沙盒存储
     *              <uses-permission
     *                  android:name="android.permission.WRITE_EXTERNAL_STORAGE"
     *                      android:maxSdkVersion="28" />
     */
    public static final String WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
    /**
     * description: 如果Android11存储权限与其它Android6权限同时请求时，只请求MANAGE_EXTERNAL_STORAGE权限，其它权限需要重新execute
     *              (是否同时执行可以在自定义的Request中处理，在PermissionActivity的onCreate中互斥执行)
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


        /**
         * description: 如果您的应用以 Android 11 或更高版本为目标平台，并且需要访问以下列表中显示的电话号码 API，则必须请求 READ_PHONE_NUMBERS 权限，而不是 READ_PHONE_STATE 权限。
         *              <p>
         *              TelephonyManager 类和 TelecomManager 类中的 getLine1Number() 方法。
         *              TelephonyManager 类中不受支持的 getMsisdn() 方法。
         *              如果您的应用声明 READ_PHONE_STATE 以调用前面列表中的方法以外的方法，您可以继续在所有 Android 版本中请求 READ_PHONE_STATE。不过，如果您仅对前面列表中的方法使用 READ_PHONE_STATE 权限，请按以下方式更新您的清单文件：
         *              <p>
         *              更改 READ_PHONE_STATE 的声明，以使您的应用仅在 Android 10（API 级别 29）及更低版本中使用该权限。
         *              添加 READ_PHONE_NUMBERS 权限。
         *              <p>
         *              以下清单声明代码段演示了此过程：
         *              <manifest>
         *                  <!-- Grants the READ_PHONE_STATE permission only on devices that run
         *                          Android 10 (API level 29) and lower. -->
         *                  <uses-permission android:name="READ_PHONE_STATE"
         *                                      android:maxSdkVersion="29" />
         *                  <uses-permission android:name="READ_PHONE_NUMBERS" />
         *              </manifest>
         */
        public static final String[] PHONE = new String[]{
                Permission.READ_PHONE_STATE,
                Permission.READ_PHONE_NUMBERS,
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

        /**
         * description: android10及以下请求文件读写权限时WRITE_EXTERNAL_STORAGE生效，并且获取管理所有文件权限；
         *              android11+请求存储权限时WRITE_EXTERNAL_STORAGE不生效，并且只获取访问媒体文件权限；
         */
        public static final String[] STORAGE = new String[]{
                Permission.READ_EXTERNAL_STORAGE,
                Permission.WRITE_EXTERNAL_STORAGE};

        /**
         * description: android10及以下请求MANAGE_EXTERNAL_STORAGE不生效；
         *              android11+请求MANAGE_EXTERNAL_STORAGE生效，并且获取管理所有文件权限；
         */
        public static final String[] STORAGE_R = new String[]{
                Permission.MANAGE_EXTERNAL_STORAGE};
    }

}
