
package com.reactlibrary;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import static android.content.Context.NOTIFICATION_SERVICE;

public class RNReactNativeSimpleNotificationModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private static final String TAG = "RNReactNativeSimpleNotificationModule";

    private static final String SMALL_ICON = "smallIcon";
    private static final String LARGE_ICON = "largeIcon";
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private int largeIconResId;
    private int smallIconResId;
    private String title;
    private String content;

    public RNReactNativeSimpleNotificationModule(ReactApplicationContext reactContext) {
      super(reactContext);
      this.reactContext = reactContext;
    }

    @Override
    public String getName() {
      return "RNSimpleNotification";
    }

    @ReactMethod
    public void _init(ReadableMap options) {
        Resources res = reactContext.getResources();
        String packageName = reactContext.getPackageName();
        if (options.hasKey(SMALL_ICON)) {
            if (options.getString(SMALL_ICON).equals("")) {
                smallIconResId = res.getIdentifier("ic_launcher", "mipmap", packageName);
            } else {
                smallIconResId = res.getIdentifier(options.getString(SMALL_ICON), "mipmap", packageName);
            }
        }
        if (options.hasKey(LARGE_ICON)) {
            if (options.getString(LARGE_ICON).equals("")) {
                largeIconResId = res.getIdentifier("ic_launcher", "mipmap", packageName);
            } else {
                largeIconResId = res.getIdentifier(options.getString(LARGE_ICON), "mipmap", packageName);
            }
        }
        if (options.hasKey(TITLE)) {
            if (options.getString(TITLE).equals("")) {
                title = "标题";
            } else {
                title = options.getString(TITLE);
            }
        }
        if (options.hasKey(CONTENT)) {
            if (options.getString(CONTENT).equals("")) {
                content = "自定义内容";
            } else {
                content = options.getString(CONTENT);
            }
        }

    }

    @ReactMethod
    public void sendNotification(){
        try{
            NotificationManager mNotificationManager = (NotificationManager) reactContext.getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(reactContext);
            mBuilder.setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setSmallIcon(smallIconResId)
                    .setDefaults(NotificationCompat.DEFAULT_ALL);
            Intent resultIntent = new Intent(reactContext, reactContext.getCurrentActivity().getClass());
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(reactContext);
            stackBuilder.addParentStack(reactContext.getCurrentActivity().getClass());
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            Notification notification = new NotificationCompat.BigTextStyle(mBuilder).bigText(content).build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            mNotificationManager.notify(1, mBuilder.build());
        }catch(Exception e){
            throw new JSApplicationIllegalArgumentException(
                    "error "+e.getMessage());
        }
    }
}