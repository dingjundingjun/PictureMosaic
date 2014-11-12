package com.lajps.picturemosaic;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ShowPicGridAdapter extends BaseAdapter
{
	
	private Context mContext;
	private List<BitmapDrawable> mLiBitmapDrawables;
	public ShowPicGridAdapter(Context mContext)
	{
		super();
		this.mContext = mContext;
		mLiBitmapDrawables = Util.getAllDrawable(mContext, Util.gPic);
	}
	
	public int getCount()
	{
		return Util.gPic.length;
	}

	public Object getItem(int arg0)
	{
		return null;
	}

	public long getItemId(int arg0)
	{
		return arg0;
	}

	public View getView(int id, View contentView, ViewGroup arg2)
	{
		if(contentView == null)
		{
			contentView = LinearLayout.inflate(mContext, R.layout.grid_item, null);
			AbsListView.LayoutParams param = new AbsListView.LayoutParams(
	                MainActivity.PIC_WIDTH - 2,
	                MainActivity.PIC_HEIGHT);
			contentView.setLayoutParams(param);
		}
		ImageView pic = (ImageView)contentView.findViewById(R.id.pic);
		pic.setImageDrawable(mLiBitmapDrawables.get(id));
		return contentView;
	}
}
