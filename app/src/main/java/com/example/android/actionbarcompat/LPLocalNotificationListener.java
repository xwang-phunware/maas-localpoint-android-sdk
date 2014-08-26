package com.example.android.actionbarcompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.digby.localpoint.sdk.core.ILPLocalNotification;
import com.digby.localpoint.sdk.core.ILPLocalNotificationListener;

public class LPLocalNotificationListener implements ILPLocalNotificationListener {

    private Context mContext;

    public LPLocalNotificationListener(Context mContext) {
        this.mContext = mContext;
    }
    @Override
    public boolean onOverrideLocalNotification(final ILPLocalNotification localNotification) {
        if ("GEOFENCE_EXIT".equals(localNotification.getCampaignType())) {
            // Suppress this local notification
            return true;
        } else if ("GEOFENCE_ENTRY".equals(localNotification.getCampaignType())) {
            // Customize this local notification and want SDK fire it. 
            // Customize local notification
            localNotification.setNotificationTitle(localNotification.getNotificationTitle()+ " - updated");
            localNotification.setNotificationMessage(localNotification.getNotificationMessage() + " - updated");

            // Customize campaign message
            localNotification.setCampaignTitle("Campaign - " + localNotification.getTitle());
            localNotification.setCampaignBody("Campaign - " + localNotification.getBody());

            // Returns false, make SDK to fire this local notification
            return false;
        } else if ("STORE_ANNOUNCEMENT".equals(localNotification.getCampaignType())) {
            // Use NotificationManager to fire it yourself
            int notifyID = 1;
            Intent intent = new Intent(mContext, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, notifyID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this.mContext);
            builder.setAutoCancel(true);
            builder.setContentTitle(localNotification.getNotificationTitle());
            builder.setContentText(localNotification.getNotificationMessage());
            builder.setContentIntent(pendingIntent);
            builder.setNumber(1);
            builder.setSmallIcon(R.drawable.ic_launcher);

            NotificationManager mNotifyMgr = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotifyMgr.notify(notifyID, builder.build());

            // Returns true, prevent SDK fire this local notification
            return true;
        }

        return false;
    }
}