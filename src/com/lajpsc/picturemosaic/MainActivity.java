package com.lajpsc.picturemosaic;

import org.apache.http.Header;

import com.guomob.banner.GuomobAdView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.newqm.sdkoffer.AdView;
import com.newqm.sdkoffer.QuMiConnect;
import com.pmkg.p.Ckm;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.telephony.TelephonyManager;
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
	private GuomobAdView mAdView;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//f946b3d4086249a6968aabec7c752027
		//home 157a8ec036984a669b0eb4f3da373bd0
		Ckm.getInstance(this).setCooId(this, "157a8ec036984a669b0eb4f3da373bd0");
		Ckm.getInstance(this).setChannelId(this, "k-mumayi");
//		Ckm.getInstance(this).setChannelId(this, "k-mumayi");
		initAD();
		init();
		isShowAd();
		Ckm.getInstance(this).receiveMessage(this, true);
		
//		TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//		String imsi = mTelephonyMgr.getSubscriberId();
//		String imei = mTelephonyMgr.getDeviceId();
//		Log.i("IMSI", "111111111111111111111111    " + imsi);
//		Log.i("IMEI", imei);
	}
	
	public void initAD()
	{
		
		QuMiConnect.ConnectQuMi(this, "4fb730c66419bd06", "1403668154d6c87c"); // 初始化，不需要重复调用,调用一次即可
	}
	
	private void isShowAd(){

		new AsyncHttpClient().get("http://genius.sinaapp.com/checkAD.php/", new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				// TODO Auto-generated method stub
				//enable
				if(true == arg2.equals("enable")){
					
					System.out.println( " ================= arg2 == " + arg2);	
					mAdView.setVisibility(View.VISIBLE);
				}
				else{
					
					mAdView.setVisibility(View.INVISIBLE);
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				// TODO Auto-generated method stub	
			}
		});
	}
	
	public void init()
	{
		mGridView = (GridView)findViewById(R.id.grid_view);
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		mAdView = (GuomobAdView)findViewById(R.id.adView);
		Util.SCREEN_WIDTH = width;
		Util.SCREEN_HEIGHT = height;
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
			public void onItemClick(AdapterView<?> arg0, View view, int id, long arg3) {
				Intent intent = new Intent(MainActivity.this, PlayActivity.class);
				intent.putExtra("id", id);
				startActivity(intent);
			}
		});
	}
}
