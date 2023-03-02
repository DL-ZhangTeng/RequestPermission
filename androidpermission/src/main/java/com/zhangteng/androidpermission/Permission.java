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
    /**
     * description: 为了让用户更好地控制应用对位置信息的访问权限，Android 10 引入了新的位置权限 ACCESS_BACKGROUND_LOCATION。
     *              与现有的 ACCESS_FINE_LOCATION和 ACCESS_COARSE_LOCATION权限不同，新权限仅会影响应用在后台运行时对位置信息的访问权。除非应用的某个 Activity 可见或应用正在运行前台服务，否则应用将被视为在后台运行。
     *              如果需要在后台时也获得用户位置(比如滴滴)，就需要动态申请ACCESS_BACKGROUND_LOCATION权限。
     *              如果不需要的话，应用就无需任何改动，且谷歌会按照应用的targetSDK作出不同处理：
     *              targetSDK <= 9 应用如果请求了ACCESS_FINE_LOCATION或 ACCESS_COARSE_LOCATION权限，Android 10设备会自动帮你申请ACCESS_BACKGROUND_LOCATION权限。
     */
    public static final String ACCESS_BACKGROUND_LOCATION = "android.permission.ACCESS_BACKGROUND_LOCATION";

    /**
     * description: Android 13 引入了NEARBY_WIFI_DEVICES运行时权限，该权限属于NEARBY_DEVICES权限组，适用于会管理设备与附近 Wi-Fi 接入点连接情况的应用。
     *              借助此权限，您可以更轻松地说明应用为何访问附近的 Wi-Fi 设备；
     *              在以前的 Android 版本中，这类应用需要声明ACCESS_FINE_LOCATION权限。
     *              如果您的应用以 Android 13 为目标平台并调用多个不同的 Wi-Fi API，则必须从用户处获得这项新权限。
     *              如果您的应用尝试在未获得适当权限的情况下调用 Wi-Fi API，则会发生SecurityException。
     */
    public static final String NEARBY_WIFI_DEVICES = "android.permission.NEARBY_WIFI_DEVICES";

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
    /**
     * description: Android 13 中引入了“在使用时”访问身体传感器（例如心率、体温和血氧饱和度）的概念。此访问模式与Android 10（API 级别 29）系统为位置信息引入的模式非常相似。
     *              如果您的应用以 Android 13 为目标平台，并且在后台运行时需要访问身体传感器信息，那么除了现有的BODY_SENSORS权限外，您还必须声明新的BODY_SENSORS_BACKGROUND权限。
     *              注意：这是受到“硬性限制”的权限，除非设备的安装程序针对您的应用将该权限列入了许可名单，否则您的应用将无法获得此权限。如需了解详情，请参阅有关受限权限的指南。
     */
    public static final String BODY_SENSORS_BACKGROUND = "android.permission.BODY_SENSORS_BACKGROUND";

    public static final String SEND_SMS = "android.permission.SEND_SMS";
    public static final String RECEIVE_SMS = "android.permission.RECEIVE_SMS";
    public static final String READ_SMS = "android.permission.READ_SMS";
    public static final String RECEIVE_WAP_PUSH = "android.permission.RECEIVE_WAP_PUSH";
    public static final String RECEIVE_MMS = "android.permission.RECEIVE_MMS";

    /**
     * description: 读取权限在Android13中被废弃
     *              <uses-permission
     *                  android:name="android.permission.READ_EXTERNAL_STORAGE"
     *                      android:maxSdkVersion="32" />
     */
    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    /**
     * description: 从Android 13开始，以Android13（API 33）为目标平台的应用，系统新增运行时权限READ_MEDIA_IMAGES、READ_MEDIA_VIDEO、READ_MEDIA_AUDIO 替代原有的READ_EXTERNAL_STORAGE权限。
     *              如果用户之前向您的应用授予了 READ_EXTERNAL_STORAGE 权限，系统会自动向您的应用授予细化的媒体权限。
     *              如果您同时请求 READ_MEDIA_IMAGES 权限和 READ_MEDIA_VIDEO 权限，系统只会显示一个系统权限对话框。
     *              注意：如果您的应用只需要访问图片、照片和视频，请考虑使用照片选择器，而不是声明 READ_MEDIA_IMAGES 和 READ_MEDIA_VIDEO 权限。
     */
    public static final String READ_MEDIA_IMAGES = "android.permission.READ_MEDIA_IMAGES";
    public static final String READ_MEDIA_VIDEO = "android.permission.READ_MEDIA_VIDEO";
    public static final String READ_MEDIA_AUDIO = "android.permission.READ_MEDIA_AUDIO";
    /**
     * description: 写入权限在Android11中被废弃，Android10中可以通过requestLegacyExternalStorage关闭沙盒存储
     *              <uses-permission
     *                  android:name="android.permission.WRITE_EXTERNAL_STORAGE"
     *                      android:maxSdkVersion="28" />
     */
    public static final String WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
    /**
     * description: 如果Android11存储权限与其它Android6权限同时请求时，只请求MANAGE_EXTERNAL_STORAGE权限，其它权限需要重新execute
     */
    public static final String MANAGE_EXTERNAL_STORAGE = "android.permission.MANAGE_EXTERNAL_STORAGE";

    /**
     * description:Android 13 中引入了新的运行时权限，用于从应用发送非豁免通知：POST_NOTIFICATIONS。要确认用户是否已启用通知，请调用NotificationManager.areNotificationsEnabled()
     */
    public static final String POST_NOTIFICATIONS = "android.permission.POST_NOTIFICATIONS";

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
                Permission.ACCESS_COARSE_LOCATION,
                Permission.ACCESS_BACKGROUND_LOCATION};

        /**
         * description: Android 13 引入了NEARBY_WIFI_DEVICES运行时权限，该权限属于NEARBY_DEVICES权限组，适用于会管理设备与附近 Wi-Fi 接入点连接情况的应用。
         *              借助此权限，您可以更轻松地说明应用为何访问附近的 Wi-Fi 设备；
         *              在以前的 Android 版本中，这类应用需要声明ACCESS_FINE_LOCATION权限。
         *              如果您的应用以 Android 13 为目标平台并调用多个不同的 Wi-Fi API，则必须从用户处获得这项新权限。
         *              如果您的应用尝试在未获得适当权限的情况下调用 Wi-Fi API，则会发生SecurityException。
         */
        public static final String[] NEARBY_DEVICES = new String[]{Permission.NEARBY_WIFI_DEVICES};

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

        public static final String[] SENSORS = new String[]{
                Permission.BODY_SENSORS,
                Permission.BODY_SENSORS_BACKGROUND};

        public static final String[] SMS = new String[]{
                Permission.SEND_SMS,
                Permission.RECEIVE_SMS,
                Permission.READ_SMS,
                Permission.RECEIVE_WAP_PUSH,
                Permission.RECEIVE_MMS};

        public static final String[] NOTIFICATIONS = new String[]{
                Permission.POST_NOTIFICATIONS};

        /**
         * description: android10及以下请求文件读写权限时WRITE_EXTERNAL_STORAGE生效，并且获取管理所有文件权限；
         *              android11+请求存储权限时WRITE_EXTERNAL_STORAGE不生效，并且只获取访问媒体文件权限，如果需要要写入权限可以使用Android10+的分区存储或请求权限MANAGE_EXTERNAL_STORAGE
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

        /**
         * description: 从Android 13开始，以Android13（API 33）为目标平台的应用，系统新增运行时权限READ_MEDIA_IMAGES、READ_MEDIA_VIDEO、READ_MEDIA_AUDIO 替代原有的READ_EXTERNAL_STORAGE权限。
         *              如果用户之前向您的应用授予了 READ_EXTERNAL_STORAGE 权限，系统会自动向您的应用授予细化的媒体权限。
         *              如果您同时请求 READ_MEDIA_IMAGES 权限和 READ_MEDIA_VIDEO 权限，系统只会显示一个系统权限对话框。
         *              注意：如果您的应用只需要访问图片、照片和视频，请考虑使用照片选择器，而不是声明 READ_MEDIA_IMAGES 和 READ_MEDIA_VIDEO 权限。
         */
        public static final String[] STORAGE_T = new String[]{
                Permission.READ_MEDIA_IMAGES,
                Permission.READ_MEDIA_VIDEO,
                Permission.READ_MEDIA_AUDIO};

        /**
         * description: 用于所有安卓版本请求文件读写权限，如果是11及以上不获取文件所有控制权限
         */
        public static final String[] STORAGE_MEDIA = new String[]{
                Permission.READ_EXTERNAL_STORAGE,
                Permission.WRITE_EXTERNAL_STORAGE,
                Permission.READ_MEDIA_IMAGES,
                Permission.READ_MEDIA_VIDEO,
                Permission.READ_MEDIA_AUDIO};

        /**
         * description: 用于所有安卓版本请求文件读写权限，如果是11及以上获取文件所有控制权限
         */
        public static final String[] STORAGE_ALL = new String[]{
                Permission.MANAGE_EXTERNAL_STORAGE,
                Permission.READ_EXTERNAL_STORAGE,
                Permission.WRITE_EXTERNAL_STORAGE,
                Permission.READ_MEDIA_IMAGES,
                Permission.READ_MEDIA_VIDEO,
                Permission.READ_MEDIA_AUDIO};
    }
}
