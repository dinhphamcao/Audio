package com.example.audio;

import java.io.IOException;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	private MediaRecorder myAudioRecorder;
	private String outputFile = null;
	private Button start, stop, play, stopplay;
	MediaPlayer m = new MediaPlayer();
	NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		start = (Button) findViewById(R.id.button1);
		stop = (Button) findViewById(R.id.button2);
		play = (Button) findViewById(R.id.button3);
		stopplay = (Button) findViewById(R.id.button4);
		stop.setEnabled(false);
		play.setEnabled(true);
		outputFile = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/myrecording.3gp";
		myAudioRecorder = new MediaRecorder();
		myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
		myAudioRecorder.setOutputFile(outputFile);
		
		play=(Button)findViewById(R.id.button3);
		play.setOnClickListener (new OnClickListener() {
			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				
				try {
					
					m.setDataSource(outputFile);
					m.prepare();
					m.start();
					Toast.makeText(getApplicationContext(), "Playing Audio",
							Toast.LENGTH_LONG).show();

					// Making the notification pop up
					mBuilder.setSmallIcon(R.drawable.ic_launcher)
							.setContentTitle("Audio")
							.setContentText("Audio is playing");
					NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
					notificationManager.notify(1, mBuilder.build());
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

	}

	public void start(View view) {
		try {
			myAudioRecorder.prepare();
			myAudioRecorder.start();
		}

		catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		start.setEnabled(false);
		stop.setEnabled(true);
		Toast.makeText(getApplicationContext(), "Recording Started",
				Toast.LENGTH_SHORT).show();

	}

	public void stop(View view) {
		myAudioRecorder.stop();
		myAudioRecorder.release();
		myAudioRecorder = null;
		stop.setEnabled(false);
		play.setEnabled(true);
		Toast.makeText(getApplicationContext(), "Audio recorded successfully",
				Toast.LENGTH_LONG).show();
	}

	

	public void pause(View view) {
		m.release();
		m = null;
		mBuilder.setSmallIcon(R.drawable.ic_launcher).setContentTitle("Audio")
				.setContentText("Audio is stopped");
		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(1, mBuilder.build());
		stopplay.setEnabled(false);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}
