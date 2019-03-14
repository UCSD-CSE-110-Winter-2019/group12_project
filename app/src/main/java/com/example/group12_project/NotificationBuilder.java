package com.example.group12_project;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

public class NotificationBuilder {
    private Context activity;
    private String title, content;
    private int id;
    NotificationCompat.Builder builder;

    public NotificationBuilder(Context activity, String textTitle, String textContent, int id) {
        this.activity = activity;
        this.title = textTitle;
        this.content = textContent;
        this.id = id;
    }

    public void setUp(){
         builder = new NotificationCompat.Builder(this.activity)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(this.title)
                .setContentText(this.content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    public void show(){
        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(this.id, builder.build());
    }
}
