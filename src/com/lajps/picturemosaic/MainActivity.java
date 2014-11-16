package com.lajps.picturemosaic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

public class MainActivity extends Activity
{
	private GridView mGridView;
	private final int MODE_MOSAIC = 0;
	private final int MODE_WALLER = 1;
	private int mode = 0;
	private ShowPicGridAdapter mShowPicGridAdapter;
	public static int PIC_WIDTH;
	public static int PIC_HEIGHT;
	private final String TAG = "MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}
	
	public void init()
	{
		mGridView = (GridView)findViewById(R.id.grid_view);
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		
		PIC_WIDTH = width / 3;
		PIC_HEIGHT = PIC_WIDTH;
		Log.d(TAG, "PIC_WIDTH = " + width + " 11111111111111111111111 === " + height);
		mGridView.setNumColumns(3);
		mGridView.setColumnWidth(PIC_WIDTH);
		mShowPicGridAdapter = new ShowPicGridAdapter(this);
		mGridView.setAdapter(mShowPicGridAdapter);
		mShowPicGridAdapter.notifyDataSetChanged();
		
		mGridView.setOnItemClickListener(new OnItemClickListener() 
		{
			public void onItemClick(AdapterView<?> arg0, View view, int id,
					long arg3) {
				Intent intent = new Intent(MainActivity.this, PlayActivity.class);
				intent.putExtra("id", id);
				startActivity(intent);
			}
		});
	}
}
