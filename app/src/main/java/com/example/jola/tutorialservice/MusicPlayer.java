package com.example.jola.tutorialservice;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

/**
 * Created by Jola on 9/29/2015.
 */
public class MusicPlayer extends Service {

    /*
     calls Context.startService() -> system will retrieve the service -> onCreate() -> onStartCommand(Intent, int, int)
     The service will at this point continue running until Context.stopService() or stopSelf() is called.
     */

    private int startID;
    private MediaPlayer musicPlayer;
    private NotificationManager notificationManager;

    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.
    private int NOTIFICATION_ID = R.string.local_service_started;

    @Override
    //Called by the system when the service is first created.
    public void onCreate() {
        super.onCreate();

        musicPlayer = MediaPlayer.create(getApplicationContext(), R.raw.gra);

        if(musicPlayer.isPlaying()){
            //Sets the player to be looping or non-looping.
            musicPlayer.setLooping(false);
            //Register a callback to be invoked when the end of a media source has been reached during playback.
            musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //stopSelf(startID);
                    stopSelf();
                }
            });
        }

       Intent notyficationIntent = new Intent(this, MainActivity.class);
        // The PendingIntent to launch our activity if the user selects this notification
       PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notyficationIntent, 0);
        // Set the info for the views that show in the notification panel.
        Notification notification = new Notification.Builder(
            getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Music player")
                .setWhen(System.currentTimeMillis())
                .setContentText("Music player")
                .setContentText("Click to open application")
                .setContentIntent(pendingIntent).build();

        //it's in foreground so won't be killed by system
       // startForeground(NOTIFICATION_ID, notification);

        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {
        /*
        Called by the system every time a client explicitly starts the service
        by calling startService(Intent), providing the arguments it supplied
        and a unique integer token representing the start request.
         */

        if(!musicPlayer.isPlaying()){
            startID = startid;

            if(musicPlayer.isPlaying()){
                //back to begin of song
                musicPlayer.seekTo(0);
            }else{
                musicPlayer.start();
            }
        }
        //START_STICKY is used for services that are explicitly started and stopped as needed
        //START_NOT_STICKY or START_REDELIVER_INTENT are used for services that should only remain running while processing any commands sent to them
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (musicPlayer.isPlaying()) {
            musicPlayer.stop();
        }
        musicPlayer.release();
    }

    @Override
    //public abstract IBinder onBind (Intent intent)
    public IBinder onBind(Intent intent) {
        //Return the communication channel to the service. May return null if clients can not bind to the service.
        // The returned IBinder is usually for a complex interface that has been described using aidl.

        //Return an IBinder through which clients can call on to the service.
        return null;
    }


}
