package com.example.smtrick.electionappuser.Views.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smtrick.electionappuser.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity implements AnimationListener {

	// Set Duration of the Splash Screen
	long Delay = 4000;
	TextView imageView1;
	Animation animBounce;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splashscreen);

		imageView1 = (TextView) findViewById(R.id.image);

		animBounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);

//		animBounce.setAnimationListener(this);

		imageView1.setVisibility(View.VISIBLE);
		imageView1.startAnimation(animBounce);

		// Create a Timer
		Timer RunSplash = new Timer();

		// Task to do when the timer ends
		TimerTask ShowSplash = new TimerTask() {
			@Override
			public void run() {


				// Close SplashScreenActivity.class
				finish();

				// Start MainActivity.class
				Intent myIntent = new Intent(SplashScreen.this, LoginActivity.class);
				startActivity(myIntent);
				overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

			}
		};

		// Start the timer
		RunSplash.schedule(ShowSplash, Delay);

	}

	public void onAnimationEnd(Animation animation) {
		// Take any action after completing the animation

		// check for zoom in animation
		if (animation == animBounce) {
		}

	}

	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

}
