package com.example.namelater;

import java.io.IOException;
import java.sql.Time;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.view.Menu;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.media.MediaRecorder;

// Google Drive imports
/*import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
*/

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;
    
    private static final String lastRunVersion = "Version";
    
    private MediaRecorder mRecorder = null;
	
   	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
				.addAction(R.drawable.capture, "Call", myIntent)
				.addAction(R.drawable.capture, "More", myIntent)
				.addAction(R.drawable.capture, "And more", myIntent).build();
    
		notificationManager.notify(0, notify); 
        
        
        
        //Time now = new Time(Time.getCurrentTimezone());
    	//now.setToNow();
    	
    	String stamp = "moo";//now.getMonth();
    	
    	mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/Audio_" + stamp + ".3gp";
        
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
					"Don't feel victimized with nothing you can do!",
					Toast.LENGTH_LONG).show();
			Toast.makeText(this,
					"Sign in initially and use this app to record what happens!",
					Toast.LENGTH_LONG).show();
		}
        // Else, start recording audio
        else
        {
        	setContentView(R.layout.black);
        	
    		
    		/*
        	mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setOutputFile(mFileName);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                mRecorder.prepare();
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }
*/
           // mRecorder.start();
        }
     }
    
    /*private void saveFileToDrive()
    {
    	Thread t = new Thread(new Runnable() {
    		@Override
    		public void run() {
    			try {
    				// File's binary content
    				java.io.File fileContent = new java.io.File(fileUri.getPath());
    				FileContent mediaContent = new FileContent("image/jpeg", fileContent);
    				
    				// File's metadata.
    				File body = new File();
    				body.setTitle(fileContent.getName());
    				body.setMimeType("vid/mp4");
    				
    				File file = service.files().insert(body, mediaContent).execute();
    				if (file != null)
    				{
    					showToast("Photo uploaded: " + file.getTitle());
    					startCameraIntent();
    					}
    				}
    			catch (UserRecoverableAuthIOException e) {
    				startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
    				}
    			catch (IOException e) {
    				e.printStackTrace();
    				}
    			}
    		});
    	t.start();
    	}*/
   
   
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
