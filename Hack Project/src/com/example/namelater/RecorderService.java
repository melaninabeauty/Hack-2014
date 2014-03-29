package com.example.namelater;

import java.io.File;
import java.io.IOException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;

//Google Drive imports
/*import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;*/

public class RecorderService extends Service {

	private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
	private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
	
	private MediaRecorder recorder = null;
	private int output_format = MediaRecorder.OutputFormat.MPEG_4;
	
	private static String filePath;
	
	@Override
	public void onStart(Intent intent, int startid)
	{
		startRecording();
	}
	
	@Override
	public void onDestroy()
	{
		stopRecording();
	}
	
	public void startRecording()
	{
		recorder = new MediaRecorder();

		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(output_format);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		
		filePath = getFilename();
		recorder.setOutputFile(filePath);

		try {
			recorder.prepare();
			recorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stopRecording()
	{
		//saveFileToDrive();
		if (null != recorder) {
			recorder.stop();
			recorder.reset();
			recorder.release();

			recorder = null;
		}
	}
	
	// Used to assign proper filename
	private String getFilename()
    {
		String filepath = Environment.getExternalStorageDirectory().getPath();
		File file = new File(filepath, AUDIO_RECORDER_FOLDER);
		
		if (!file.exists()) {
			file.mkdirs();
		}

		return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + AUDIO_RECORDER_FILE_EXT_MP4);
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*private void saveFileToDrive()
    {
    	Thread t = new Thread(new Runnable() {
    		@Override
    		public void run() {
    			try {
    				// File's binary content
    				java.io.File fileContent = new java.io.File(fileUri.getPath());
    				FileContent mediaContent = new FileContent("audio/mp4", fileContent);
    				
    				// File's metadata.
    				File body = new File();
    				body.setTitle(fileContent.getName());
    				body.setMimeType("audio/mp4");
    				
    				File file = service.files().insert(body, mediaContent).execute();
    				if (file != null)
    				{
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
    	}	*/
}
