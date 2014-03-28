package com.example.namelater;

import javax.management.Notification;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Notification iconInTaskBar2 = new Notification(R.Drawable.)

		Notification iconInTaskBar = new Notification(R.drawable.your_app_icon,
				R.string.nameLater, System.currentTimeMillis());
		
		iconInTaskBar.flags |= Notification.FLAG_NO_CLEAR
				| Notification.FLAG_ONGOING_EVENT;
		
		NotificationManager notifier = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		
		notifier.notify(1, iconInTaskBar);
	}

	
	
	//to stop icon in notification bar
	((NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(1);
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
