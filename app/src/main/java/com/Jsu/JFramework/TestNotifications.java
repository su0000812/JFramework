package com.Jsu.JFramework;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.NotificationCompat;

/**
 * Created by JSu on 2016/6/24.
 */

public class TestNotifications {

    private static final int id = 1008610086;

    public void showNotifications(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Test Title");
        builder.setContentText("Test Notifications");
        builder.setPriority(Notification.PRIORITY_HIGH).setVibrate(new long[0]);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("邮件标题: ");
        for (int i = 0; i < 5; i++) {
            inboxStyle.addLine("邮件内容" + i);
        }
        builder.setStyle(inboxStyle);

        RemoteInput remoteInput = new RemoteInput.Builder("Reply").setLabel("LABEL_REPLY").build();
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationCompat.Action replyAction = new android.support.v4.app.NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher, "reply", pendingIntent).addRemoteInput(remoteInput).build();

        builder.addAction(replyAction);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());
    }
}
