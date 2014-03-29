package com.example.namelater;

import java.io.IOException;
import java.sql.Time;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
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
	
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //To create a notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("My notification")
                .setContentText("App Running");
               
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
       TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
       // stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
       // mNotificationManager.notify(mId, mBuilder.build());
        
       
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
        	
        	Intent startMain = new Intent(Intent.ACTION_MAIN);
    		startMain.addCategory(Intent.CATEGORY_HOME);
    		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		startActivity(startMain);
    		
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

            mRecorder.start();
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
