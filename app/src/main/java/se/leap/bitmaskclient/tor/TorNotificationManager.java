package se.leap.bitmaskclient.tor;
/**
 * Copyright (c) 2021 LEAP Encryption Access Project and contributers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import se.leap.bitmaskclient.R;
import se.leap.bitmaskclient.base.models.Provider;
import se.leap.bitmaskclient.base.models.ProviderObservable;

public class TorNotificationManager {
    public   final static int TOR_SERVICE_NOTIFICATION_ID = 10;
    static final String NOTIFICATION_CHANNEL_NEWSTATUS_ID = "bitmask_tor_service_news";


    public TorNotificationManager() {}


    public static Notification buildTorForegroundNotification(Context context) {
        NotificationManager notificationManager = initNotificationManager(context);
        if (notificationManager == null) {
            return null;
        }
        NotificationCompat.Builder notificationBuilder = initNotificationBuilderDefaults(context);
        return notificationBuilder
                .setSmallIcon(R.drawable.ic_bridge_36)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(context.getString(R.string.tor_started)).build();
    }

    public void buildTorNotification(Context context, String state) {
        NotificationManager notificationManager = initNotificationManager(context);
        if (notificationManager == null) {
            return;
        }
        NotificationCompat.Builder notificationBuilder = initNotificationBuilderDefaults(context);
        notificationBuilder
                .setSmallIcon(R.drawable.ic_bridge_36)
                .setWhen(System.currentTimeMillis())
                .setTicker(state)
                .setContentTitle(state);
        notificationManager.notify(TOR_SERVICE_NOTIFICATION_ID, notificationBuilder.build());
    }


    private static NotificationManager initNotificationManager(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager);
        }
        return notificationManager;
    }

    @TargetApi(26)
    private static void createNotificationChannel(NotificationManager notificationManager) {
        CharSequence name =  "Bitmask Tor Service";
        String description = "Informs about usage of bridges to configure Bitmask.";
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_NEWSTATUS_ID,
                name,
                NotificationManager.IMPORTANCE_LOW);
        channel.setSound(null, null);
        channel.setDescription(description);
        notificationManager.createNotificationChannel(channel);
    }

    private static NotificationCompat.Builder initNotificationBuilderDefaults(Context context) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_NEWSTATUS_ID);
        notificationBuilder.
                setDefaults(Notification.DEFAULT_ALL).
                setAutoCancel(true);
        return notificationBuilder;
    }

    public void cancelNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            return;
        }
        notificationManager.cancel(TOR_SERVICE_NOTIFICATION_ID);
    }
}
