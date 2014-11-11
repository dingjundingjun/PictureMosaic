package com.lajps.picturemosaic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ShowPicGridAdapter extends BaseAdapter
{
	public static int[] gPic = {R.drawable.pic_0,R.drawable.pic_1,R.drawable.pic_2,R.drawable.pic_3,R.drawable.pic_4,R.drawable.pic_5};
	private Context mContext;
	public ShowPicGridAdapter(Context mContext)
	{
		super();
		this.mContext = mContext;
	}
	
	@Override
	public int getCount()
	{
		return gPic.length;
	}

	@Override
	public Object getItem(int arg0)
	{
		return null;
	}

	@Override
	public long getItemId(int arg0)
	{
		return arg0;
	}

	@Override
	public View getView(int id, View contentView, ViewGroup arg2)
	{
		if(contentView == null)
		{
			contentView = LinearLayout.inflate(mContext, R.layout.grid_item, null);
		}
		ImageView pic = (ImageView)contentView.findViewById(R.id.pic);
		pic.setImageResource(gPic[id]);
		return contentView;
	}
}
