package dev.adi.poc.simplemailserver.service;

//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.support.v4.app.NotificationCompat;
//import android.util.Log;
//
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
//import dev.adi.poc.simplemailserver.MainActivity;

//public class MyFirebaseMessagingService extends FirebaseMessagingService {

//    public static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
//
//        Log.d(TAG, "Message received ["+remoteMessage+"]");
//
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1410, intent, PendingIntent.FLAG_ONE_SHOT);
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//            .setContentTitle("Message")
//            .setContentText(remoteMessage.getNotification().getBody())
//            .setAutoCancel(true)
//            .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(1410, notificationBuilder.build());
//    }
//}