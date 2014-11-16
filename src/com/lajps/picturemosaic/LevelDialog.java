package com.lajps.picturemosaic;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

public class LevelDialog extends Dialog implements android.view.View.OnClickListener
{
	private onChooseListener mOnChooseListener;
	public void setChooseListener(onChooseListener oc)
	{
		mOnChooseListener = oc;
	}
	
	public LevelDialog(Context context) {
		super(context);
		init();
	}
	
	public void init()
	{
		this.setContentView(R.layout.level_dialog);
		this.findViewById(R.id.level_easy).setOnClickListener(this);
		this.findViewById(R.id.level_orig).setOnClickListener(this);
		this.findViewById(R.id.level_hard).setOnClickListener(this);
	}

	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.level_easy:
		{
			mOnChooseListener.choose(Util.GAME_LEVEL_EASY);
			ok();
			break;
		}
		case R.id.level_orig:
		{
			mOnChooseListener.choose(Util.GAME_LEVEL_ORIGINAL);
			ok();
			break;
		}
		case R.id.level_hard:
		{
			mOnChooseListener.choose(Util.GAME_LEVEL_HARD);
			ok();
			break;
		}
		}
		
	}

	public void ok()
	{
		this.dismiss();
	}
	
	interface onChooseListener
	{
		public void choose(int mode);
	}
}
