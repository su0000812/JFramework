package com.Jsu.framework.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.Jsu.framework.R;

/**
 * Created by JSu on 2016/6/27.
 */

public class NotifiUtils {

    public static final int REPLY_INTENT_ID = 0;
    public static final int ARCHIVE_INTENT_ID = 1;

    public static final int REMOTE_INPUT_ID = 1247;

    public static final String LABEL_REPLY = "Reply";
    public static final String LABEL_ARCHIVE = "Archive";
    public static final String REPLY_ACTION = "com.hitherejoe.notifi.util.ACTION_MESSAGE_REPLY";
    public static final String KEY_PRESSED_ACTION = "KEY_PRESSED_ACTION";
    public static final String KEY_TEXT_REPLY = "KEY_TEXT_REPLY";
    private static final String KEY_NOTIFICATION_GROUP = "KEY_NOTIFICATION_GROUP";

    public void showStandarHeadsUpNotification(Context context, Class startContext) {
        Intent intent = new Intent()
                .addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                .setAction(REPLY_ACTION)
                .putExtra(KEY_PRESSED_ACTION, LABEL_ARCHIVE);
        PendingIntent archiveIntent = PendingIntent.getActivity(context, ARCHIVE_INTENT_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(android.R.drawable.sym_def_app_icon,
                LABEL_REPLY, archiveIntent).build();

        NotificationCompat.Action archiveAction = new NotificationCompat.Action.Builder(android.R.drawable.sym_def_app_icon,
                LABEL_ARCHIVE, archiveIntent).build();

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("for test")
                .setContentText("content for test")
                .setLargeIcon(largeIcon)
                .setColor(ContextCompat.getColor(context, android.R.color.holo_orange_light))
                .setAutoCancel(true);
        notificationBuilder.setPriority(Notification.PRIORITY_HIGH).setVibrate(new long[0]);
        notificationBuilder.addAction(replyAction);
        notificationBuilder.addAction(archiveAction);

        Intent push = new Intent();
        push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        push.setClass(context, startContext);

        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0, push, PendingIntent.FLAG_CANCEL_CURRENT);
        notificationBuilder.setFullScreenIntent(fullScreenPendingIntent, true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, notificationBuilder.build());
    }
}
