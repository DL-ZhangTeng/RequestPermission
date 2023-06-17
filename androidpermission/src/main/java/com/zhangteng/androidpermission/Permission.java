package com.zhangteng.androidpermission;

import androidx.annotation.RequiresApi;

/**
 * Created by swing on 2018/5/10.
 */
public class Permission {
    @RequiresApi(29)
    public static final String ACTIVITY_RECOGNITION = "android.permission.ACTIVITY_RECOGNITION";
    public static final String READ_CALENDAR = "android.permission.READ_CALENDAR";
    public static final String WRITE_CALENDAR = "android.permission.WRITE_CALENDAR";

    /**
     * Android 10 移除
     * <p>
     * <manifest>
     * <!-- Grants the READ_PHONE_STATE permission only on devices that run Android 10 (API level 29) and lower. -->
     * <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS"
     * android:maxSdkVersion="29" />
     * </manifest>
     */
    public static final String PROCESS_OUTGOING_CALLS = "android.permission.PROCESS_OUTGOING_CALLS";
    public static final String READ_CALL_LOG = "android.permission.READ_CALL_LOG";
    public static final String WRITE_CALL_LOG = "android.permission.WRITE_CALL_LOG";

    public static final String CAMERA = "android.permission.CAMERA";

    public static final String READ_CONTACTS = "android.permission.READ_CONTACTS";
    public static final String WRITE_CONTACTS = "android.permission.WRITE_CONTACTS";
    public static final String GET_ACCOUNTS = "android.permission.GET_ACCOUNTS";

    @RequiresApi(29)
    public static final String ACCESS_BACKGROUND_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
    public static final String ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
    public static final String ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";

    public static final String RECORD_AUDIO = "android.permission.RECORD_AUDIO";

    /**
     * 如果您的应用以 Android 12（API 级别 31）或更高版本为目标平台，请在应用的清单文件中声明以下权限：
     * 如果您的应用程序寻找蓝牙设备，例如 BLE 外围设备，请声明 BLUETOOTH_SCAN权限。
     * 如果您的应用程序使当前设备可被其他蓝牙设备发现，请声明该 BLUETOOTH_ADVERTISE 权限。
     * 如果您的应用程序与已配对的蓝牙设备通信，请声明 BLUETOOTH_CONNECT权限。
     * 对于遗留的蓝牙相关权限声明，设置android:maxSdkVersion为30. 此应用兼容性步骤有助于系统仅向您的应用授予安装在运行 Android 12 或更高版本的设备上时所需的蓝牙权限。
     * 如果您的应用使用蓝牙扫描结果来获取物理位置，请声明 ACCESS_FINE_LOCATION权限。否则，您可以强烈断言您的应用不会获取物理位置。
     * BLUETOOTH_ADVERTISE，BLUETOOTH_CONNECT 和 BLUETOOTH_SCAN权限是运行时权限。因此，您必须在您的应用程序中明确请求用户批准，然后才能查找蓝牙设备、使设备可被其他设备发现或与已配对的蓝牙设备通信。当您的应用请求至少其中一项权限时，系统会提示用户允许您的应用访问 附近的设备。
     */
    @RequiresApi(31)
    public static final String BLUETOOTH_SCAN = "android.permission.BLUETOOTH_SCAN";
    @RequiresApi(31)
    public static final String BLUETOOTH_ADVERTISE = "android.permission.BLUETOOTH_ADVERTISE";
    @RequiresApi(31)
    public static final String BLUETOOTH_CONNECT = "android.permission.BLUETOOTH_CONNECT";

    /**
     * description:Android 13 中引入了新的运行时权限，用于从应用发送非豁免通知：POST_NOTIFICATIONS。要确认用户是否已启用通知，请调用NotificationManager.areNotificationsEnabled()
     */
    @RequiresApi(33)
    public static final String POST_NOTIFICATIONS = "android.permission.POST_NOTIFICATIONS";

    public static final String READ_PHONE_STATE = "android.permission.READ_PHONE_STATE";
    /**
     * 如果您的应用以 Android 11 或更高版本为目标平台，并且需要访问以下列表中显示的电话号码 API，则必须请求 READ_PHONE_NUMBERS 权限，而不是 READ_PHONE_STATE 权限。
     * <p>
     * TelephonyManager 类和 TelecomManager 类中的 getLine1Number() 方法。
     * TelephonyManager 类中不受支持的 getMsisdn() 方法。
     * 如果您的应用声明 READ_PHONE_STATE 以调用前面列表中的方法以外的方法，您可以继续在所有 Android 版本中请求 READ_PHONE_STATE。不过，如果您仅对前面列表中的方法使用 READ_PHONE_STATE 权限，请按以下方式更新您的清单文件：
     * <p>
     * 更改 READ_PHONE_STATE 的声明，以使您的应用仅在 Android 10（API 级别 29）及更低版本中使用该权限。
     * 添加 READ_PHONE_NUMBERS 权限。
     * <p>
     * 以下清单声明代码段演示了此过程：
     * <manifest>
     * <!-- Grants the READ_PHONE_STATE permission only on devices that run Android 10 (API level 29) and lower. -->
     * <uses-permission android:name="READ_PHONE_STATE"
     * android:maxSdkVersion="29" />
     * <uses-permission android:name="READ_PHONE_NUMBERS" />
     * </manifest>
     */
    @RequiresApi(26)
    public static final String READ_PHONE_NUMBERS = "android.permission.READ_PHONE_NUMBERS";
    @RequiresApi(26)
    public static final String ANSWER_PHONE_CALLS = "android.permission.ANSWER_PHONE_CALLS";
    public static final String CALL_PHONE = "android.permission.CALL_PHONE";
    @RequiresApi(28)
    public static final String ACCEPT_HANDOVER = "android.permission.ACCEPT_HANDOVER";
    public static final String ADD_VOICEMAIL = "com.android.voicemail.permission.ADD_VOICEMAIL";
    public static final String USE_SIP = "android.permission.USE_SIP";

    public static final String BODY_SENSORS = "android.permission.BODY_SENSORS";
    /**
     * Android 13 中引入了“在使用时”访问身体传感器（例如心率、体温和血氧饱和度）的概念。此访问模式与Android 10（API 级别 29）系统为位置信息引入的模式非常相似。
     * 如果您的应用以 Android 13 为目标平台，并且在后台运行时需要访问身体传感器信息，那么除了现有的BODY_SENSORS权限外，您还必须声明新的BODY_SENSORS_BACKGROUND权限。
     */
    @RequiresApi(33)
    public static final String BODY_SENSORS_BACKGROUND = "android.permission.BODY_SENSORS_BACKGROUND";

    public static final String SEND_SMS = "android.permission.SEND_SMS";
    public static final String RECEIVE_SMS = "android.permission.RECEIVE_SMS";
    public static final String READ_SMS = "android.permission.READ_SMS";
    public static final String RECEIVE_WAP_PUSH = "android.permission.RECEIVE_WAP_PUSH";
    public static final String RECEIVE_MMS = "android.permission.RECEIVE_MMS";

    /**
     * 读取权限在Android13中被废弃
     * <uses-permission
     * android:name="android.permission.READ_EXTERNAL_STORAGE"
     * android:maxSdkVersion="32" />
     */
    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    /**
     * 从Android 13开始，以Android13（API 33）为目标平台的应用，系统新增运行时权限READ_MEDIA_IMAGES、READ_MEDIA_VIDEO、READ_MEDIA_AUDIO 替代原有的READ_EXTERNAL_STORAGE权限。
     * 如果用户之前向您的应用授予了 READ_EXTERNAL_STORAGE 权限，系统会自动向您的应用授予细化的媒体权限。
     * 如果您同时请求 READ_MEDIA_IMAGES 权限和 READ_MEDIA_VIDEO 权限，系统只会显示一个系统权限对话框。
     */
    @RequiresApi(33)
    public static final String READ_MEDIA_IMAGES = "android.permission.READ_MEDIA_IMAGES";
    @RequiresApi(33)
    public static final String READ_MEDIA_VIDEO = "android.permission.READ_MEDIA_VIDEO";
    @RequiresApi(33)
    public static final String READ_MEDIA_AUDIO = "android.permission.READ_MEDIA_AUDIO";
    /**
     * 从Android 14开始，如果您声明了 READ_MEDIA_VISUAL_USER_SELECTED 权限，并且用户在系统权限对话框中选择了选择照片和视频，则会发生以下行为：
     * <p>
     * READ_MEDIA_IMAGES 和 READ_MEDIA_VIDEO 权限都会被拒绝。
     * 会授予 READ_MEDIA_VISUAL_USER_SELECTED 权限，可提供对用户照片和视频的部分和临时访问权限。
     * 如果您的应用需要访问其他照片和视频，您必须再次手动请求 READ_MEDIA_IMAGES 权限和/或 READ_MEDIA_VIDEO 权限。
     * <p>
     * 从Android 14开始，如果您未声明 READ_MEDIA_VISUAL_USER_SELECTED 权限，则会发生以下行为：
     * <p>
     * 在应用会话期间会授予 READ_MEDIA_IMAGES 和 READ_MEDIA_VIDEO 权限，从而提供对用户选择的照片和视频的临时授权和临时访问权限。当您的应用转到后台或当用户主动终止您的应用时，系统最终会拒绝这些权限。此行为就像其他单次授权一样。
     * 如果您的应用稍后需要访问其他照片和视频，您必须再次手动请求 READ_MEDIA_IMAGES 权限或 READ_MEDIA_VIDEO 权限。系统遵循与初始权限请求相同的流程，提示用户选择照片和视频。
     */
    @RequiresApi(34)
    public static final String READ_MEDIA_VISUAL_USER_SELECTED = "android.permission.READ_MEDIA_VISUAL_USER_SELECTED";
    /**
     * 写入权限在Android11中被废弃，Android10中可以通过requestLegacyExternalStorage关闭沙盒存储
     * <uses-permission
     * android:name="android.permission.WRITE_EXTERNAL_STORAGE"
     * android:maxSdkVersion="28" />
     */
    public static final String WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";

    /**
     * Android 13 引入了NEARBY_WIFI_DEVICES运行时权限，适用于会管理设备与附近 Wi-Fi 接入点连接情况的应用。
     * 借助此权限，您可以更轻松地说明应用为何访问附近的 Wi-Fi 设备；
     * 在以前的 Android 版本中，这类应用需要声明ACCESS_FINE_LOCATION权限。
     * 如果您的应用以 Android 13 为目标平台并调用多个不同的 Wi-Fi API，则必须从用户处获得这项新权限。
     * 如果您的应用尝试在未获得适当权限的情况下调用 Wi-Fi API，则会发生SecurityException。
     */
    @RequiresApi(33)
    public static final String NEARBY_WIFI_DEVICES = "android.permission.NEARBY_WIFI_DEVICES";

    @RequiresApi(29)
    public static final String ACCESS_MEDIA_LOCATION = "android.permission.ACCESS_MEDIA_LOCATION";

    @RequiresApi(31)
    public static final String UWB_RANGING = "android.permission.UWB_RANGING";

    /**
     * 如果Android11存储权限与其它Android6权限同时请求时，先请求MANAGE_EXTERNAL_STORAGE权限
     */
    @RequiresApi(30)
    public static final String MANAGE_EXTERNAL_STORAGE = "android.permission.MANAGE_EXTERNAL_STORAGE";

    public static final class Group {
        public static final String[] ACTIVITY_RECOGNITION = new String[]{Permission.ACTIVITY_RECOGNITION};

        public static final String[] CALENDAR = new String[]{
                Permission.READ_CALENDAR,
                Permission.WRITE_CALENDAR};

        public static final String[] CALL_LOG = new String[]{
                Permission.PROCESS_OUTGOING_CALLS,
                Permission.READ_CALL_LOG,
                Permission.WRITE_CALL_LOG,};

        public static final String[] CAMERA = new String[]{Permission.CAMERA};

        public static final String[] CONTACTS = new String[]{
                Permission.READ_CONTACTS,
                Permission.WRITE_CONTACTS,
                Permission.GET_ACCOUNTS};

        public static final String[] LOCATION = new String[]{
                Permission.ACCESS_BACKGROUND_LOCATION,
                Permission.ACCESS_FINE_LOCATION,
                Permission.ACCESS_COARSE_LOCATION};

        public static final String[] MICROPHONE = new String[]{Permission.RECORD_AUDIO};

        /**
         * Android 12（API 级别 31）引入了BLUETOOTH_SCAN、BLUETOOTH_ADVERTISE和BLUETOOTH_CONNECT运行时权限：
         * 如果您的应用程序寻找蓝牙设备，例如 BLE 外围设备，请声明 BLUETOOTH_SCAN权限。
         * 如果您的应用程序使当前设备可被其他蓝牙设备发现，请声明该 BLUETOOTH_ADVERTISE 权限。
         * 如果您的应用程序与已配对的蓝牙设备通信，请声明 BLUETOOTH_CONNECT权限。
         * 对于遗留的蓝牙相关权限声明，设置android:maxSdkVersion为30. 此应用兼容性步骤有助于系统仅向您的应用授予安装在运行 Android 12 或更高版本的设备上时所需的蓝牙权限。
         * 如果您的应用使用蓝牙扫描结果来获取物理位置，请声明 ACCESS_FINE_LOCATION权限。否则，您可以强烈断言您的应用不会获取物理位置。
         */
        public static final String[] NEARBY_DEVICES = new String[]{
                Permission.BLUETOOTH_SCAN,
                Permission.BLUETOOTH_ADVERTISE,
                Permission.BLUETOOTH_CONNECT};

        public static final String[] NOTIFICATIONS = new String[]{
                Permission.POST_NOTIFICATIONS};

        /**
         * 如果您的应用以 Android 11 或更高版本为目标平台，并且需要访问以下列表中显示的电话号码 API，则必须请求 READ_PHONE_NUMBERS 权限，而不是 READ_PHONE_STATE 权限。
         * <p>
         * TelephonyManager 类和 TelecomManager 类中的 getLine1Number() 方法。
         * TelephonyManager 类中不受支持的 getMsisdn() 方法。
         * 如果您的应用声明 READ_PHONE_STATE 以调用前面列表中的方法以外的方法，您可以继续在所有 Android 版本中请求 READ_PHONE_STATE。不过，如果您仅对前面列表中的方法使用 READ_PHONE_STATE 权限，请按以下方式更新您的清单文件：
         * <p>
         * 更改 READ_PHONE_STATE 的声明，以使您的应用仅在 Android 10（API 级别 29）及更低版本中使用该权限。
         * 添加 READ_PHONE_NUMBERS 权限。
         * <p>
         * 以下清单声明代码段演示了此过程：
         * <manifest>
         * <!-- Grants the READ_PHONE_STATE permission only on devices that run Android 10 (API level 29) and lower. -->
         * <uses-permission android:name="READ_PHONE_STATE"
         * android:maxSdkVersion="29" />
         * <uses-permission android:name="READ_PHONE_NUMBERS" />
         * </manifest>
         */
        public static final String[] PHONE = new String[]{
                Permission.ACCEPT_HANDOVER,
                Permission.ADD_VOICEMAIL,
                Permission.ANSWER_PHONE_CALLS,
                Permission.CALL_PHONE,
                Permission.READ_PHONE_NUMBERS,
                Permission.READ_PHONE_STATE,
                Permission.USE_SIP};

        public static final String[] SENSORS = new String[]{
                Permission.BODY_SENSORS,
                Permission.BODY_SENSORS_BACKGROUND};

        public static final String[] SMS = new String[]{
                Permission.SEND_SMS,
                Permission.RECEIVE_SMS,
                Permission.READ_SMS,
                Permission.RECEIVE_WAP_PUSH,
                Permission.RECEIVE_MMS};

        /**
         * 以Android10（API 29）及以下为目标平台的应用，请求文件读写权限时WRITE_EXTERNAL_STORAGE生效，并且获取管理所有文件权限；
         * 以Android11（API 30）为目标平台的应用，请求存储权限时WRITE_EXTERNAL_STORAGE不生效，并且只获取访问媒体文件权限，如果需要要写入权限可以使用Android10+的分区存储或请求权限MANAGE_EXTERNAL_STORAGE
         * 以Android13（API 33）为目标平台的应用，系统新增运行时权限READ_MEDIA_IMAGES、READ_MEDIA_VIDEO、READ_MEDIA_AUDIO 替代原有的READ_EXTERNAL_STORAGE权限。如果用户之前向您的应用授予了 READ_EXTERNAL_STORAGE 权限，系统会自动向您的应用授予细化的媒体权限。如果您同时请求 READ_MEDIA_IMAGES 权限和 READ_MEDIA_VIDEO 权限，系统只会显示一个系统权限对话框。
         * 在 Android 14（API 34）设备上与您的应用互动的用户可以授予对其视觉媒体库（照片/视频）的部分访问权限READ_MEDIA_VISUAL_USER_SELECTED。
         */
        public static final String[] STORAGE = new String[]{
                Permission.READ_EXTERNAL_STORAGE,
                Permission.WRITE_EXTERNAL_STORAGE,
                Permission.READ_MEDIA_IMAGES,
                Permission.READ_MEDIA_VIDEO,
                Permission.READ_MEDIA_AUDIO,
                Permission.READ_MEDIA_VISUAL_USER_SELECTED};

        /**
         * 用于所有安卓版本请求文件读写权限，如果是11及以上获取文件所有控制权限（MANAGE_EXTERNAL_STORAGE）
         */
        public static final String[] STORAGE_ALL = new String[]{
                Permission.MANAGE_EXTERNAL_STORAGE,
                Permission.READ_EXTERNAL_STORAGE,
                Permission.WRITE_EXTERNAL_STORAGE,
                Permission.READ_MEDIA_IMAGES,
                Permission.READ_MEDIA_VIDEO,
                Permission.READ_MEDIA_AUDIO,
                Permission.READ_MEDIA_VISUAL_USER_SELECTED};
    }
}
