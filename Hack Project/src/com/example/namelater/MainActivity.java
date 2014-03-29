package com.example.namelater;

import java.io.File;
import java.io.IOException;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.view.Menu;
import android.view.Window;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;
import android.media.MediaRecorder;

//@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class MainActivity extends Activity {

	private static final String lastRunVersion = "Version";
	
	//private final static int INTERVAL = 1000 * 30; // 30 seconds
	//Handler Handler;
	
	/*Runnable HandlerTask = new Runnable()
    {
         @Override 
         public void run() {
        	 //method
              Handler.postDelayed(HandlerTask, INTERVAL);
         }
    };*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home);
        
        //initialize notification manager
        NotificationManager notificationManager = (NotificationManager) 
          getSystemService(NOTIFICATION_SERVICE);
        
        
		// Load the intent
		Intent intent = new Intent(this, MainActivity.class);
		PendingIntent myIntent = PendingIntent.getActivity(this, 0, intent, 0);

		// The notification icon in notification bar
		Notification notify = new Notification.Builder(this)
				.setContentTitle("TRUVoice").setContentText("")
				.setSmallIcon(R.drawable.capture).setContentIntent(myIntent)
				.setAutoCancel(true)
				.addAction(R.drawable.shape, " ", myIntent)
				.addAction(R.drawable.shape, " ", myIntent)
				.addAction(R.drawable.shape, " ", myIntent).build();
    
		notificationManager.notify(0, notify); 
		
		
        // Check for first run of the program
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        // If this is the first time the app is running
        // Allow the user to register Google Drive email
        if (sp.getFloat(lastRunVersion, 0) == 0)
        {
			// Store the latest run version
			SharedPreferences.Editor edit = sp.edit();
			edit.putFloat(lastRunVersion, (float) 1.1);
			edit.commit();
			
			// Initial instructions to user at start up
			Toast.makeText(this,
					"Don't feel victimized as if there's nothing you can do!",
					Toast.LENGTH_LONG).show();
			Toast.makeText(this,
					"Use this app to record what happens!",
					Toast.LENGTH_LONG).show();
			Toast.makeText(this,
					"TRUVoice",
					Toast.LENGTH_LONG).show();
		}
        // Else, start recording audio
        else
        {
        	setContentView(R.layout.home);
        	startService(new Intent(this, RecorderService.class));
        	//startRepeatingTask();
        }
    }

   
    
}

