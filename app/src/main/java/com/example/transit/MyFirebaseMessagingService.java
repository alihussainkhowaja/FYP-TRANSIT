package com.example.transit;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        getFirebaseMessage(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }
    public void getFirebaseMessage (String title, String msg)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyFirebasechannel")
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(10, builder.build());
    }
}
