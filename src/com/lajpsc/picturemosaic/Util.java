package com.lajpsc.picturemosaic;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

public class Util 
{
	public static int[] gPic = { R.drawable.pic_0, R.drawable.pic_1,
		R.drawable.pic_2, R.drawable.pic_3, R.drawable.pic_4,
		R.drawable.pic_5, R.drawable.pic_6,R.drawable.pic_7,R.drawable.pic_8,
		R.drawable.pic_9,R.drawable.pic_10,R.drawable.pic_11,R.drawable.pic_12,
		R.drawable.pic_13,R.drawable.pic_14};
	
	/**拼图的行和列数*/
	public static int PICTURE_COLUMN = 3;
	public static int PICTURE_ROW = 3;
	/**游戏级别*/
	public static int GAME_LEVEL_EASY = 3;
	public static int GAME_LEVEL_ORIGINAL = 4;
	public static int GAME_LEVEL_HARD = 5;
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	public static BitmapDrawable getClipBitmapDrawable(Context context,int id)
	{
		BitmapDrawable drawable = null;
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),id);
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		int dx = (width - height)/2;
		Bitmap bmpTemp = bmp.createBitmap(bmp, dx, 0, height, height, null, false);
		//bmp.recycle();
		//Bitmap bmpReturn = bmpTemp.createScaledBitmap(bmpTemp, MainActivity.PIC_WIDTH, MainActivity.PIC_HEIGHT, true);
		return new BitmapDrawable(bmpTemp);
	}
	
	public static BitmapDrawable getimage(Context context,int id) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id,newOpts);//此时返回bm为空
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = MainActivity.PIC_WIDTH;//这里设置高度为800f
		float ww = MainActivity.PIC_HEIGHT;//这里设置宽度为480f
		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;//be=1表示不缩放
		if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;//设置缩放比例
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeResource(context.getResources(), id,newOpts);
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int dx = 0;
		int tempWidth = 0;
		Bitmap bmpTemp;
		if(width - height >= 0)
		{
			dx = (width - height)/2;
			tempWidth = height;
			bmpTemp = bitmap.createBitmap(bitmap, dx, 0, tempWidth, tempWidth, null, false);
		}
		else
		{
			dx = (height - width)/2;
			tempWidth = width;
			bmpTemp = bitmap.createBitmap(bitmap, 0, dx, tempWidth, tempWidth, null, false);
		}
		return new BitmapDrawable(bmpTemp);//压缩好比例大小后再进行质量压缩
	}
	
	public static List<BitmapDrawable> getAllDrawable(Context context,int[] pic)
	{
		List<BitmapDrawable> list = new ArrayList<BitmapDrawable>();
		for(int i = 0 ; i < pic.length ; i++)
		{
			list.add(getimage(context, pic[i]));
//			list.add(new BitmapDrawable(BitmapFactory.decodeResource(context.getResources(), pic[i])));
		}
		return list;
	}
}
