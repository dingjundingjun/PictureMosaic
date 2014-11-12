package com.lajps.picturemosaic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class PlayActivity extends Activity
{
	private int mID;
	private PlayView mPlayView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		mID = intent.getIntExtra("id", -1);
		setContentView(R.layout.play_layout);
		init();
	}
	
	public void init()
	{
		mPlayView = (PlayView)findViewById(R.id.playview);
		mPlayView.setBackgroundResource(Util.gPic[mID]);
	}
}	
