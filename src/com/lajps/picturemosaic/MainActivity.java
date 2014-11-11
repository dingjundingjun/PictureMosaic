package com.lajps.picturemosaic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;

public class MainActivity extends Activity implements OnClickListener
{
	private Button mBtnMode;
	private GridView mGridView;
	private final int MODE_MOSAIC = 0;
	private final int MODE_WALLER = 1;
	private int mode = 0;
	private ShowPicGridAdapter mShowPicGridAdapter;
	public static int PIC_WIDTH;
	public static int PIC_HEIGHT;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}
	
	public void init()
	{
		mBtnMode = (Button)findViewById(R.id.btn_mode);
		mBtnMode.setOnClickListener(this);
		
		mGridView = (GridView)findViewById(R.id.grid_view);
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
//		mGridView.setColumnWidth(width / 3);
		mGridView.setNumColumns(3);
		mShowPicGridAdapter = new ShowPicGridAdapter(this);
		mGridView.setAdapter(mShowPicGridAdapter);
		mShowPicGridAdapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View view)
	{
		switch(view.getId())
		{
			case R.id.btn_mode:
			{
				mode = (mode == MODE_MOSAIC ? 1:0);
				break;
			}
		}
	}
}
