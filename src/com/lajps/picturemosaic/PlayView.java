package com.lajps.picturemosaic;


import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayView extends GridView
{
	/**简单*/
	private int mode = 0;
	private boolean bInit = true;
	/**单个格子的宽度*/
    private int mPieceWidth;
    /**单个格子的高度*/
    private int mPieceHeight;
    /**列数*/
    private int mColumnCount;
    /**行数*/
    private int mRowCount;
    /***每个格子的drawable*/
    private Drawable mLumpDrawables[];
    private Drawable mEmptyDrawable;
    private int mPositionWrap[];
    /**渲染图片片段的view数组**/
    private TextView[] mPieceViews = null;
    /**用户获取图片随机位置的*/
    private Random random = new Random();
    /**去掉的切片的位置索引*/
    private int emptyPostion = -1;
    private LumpAdapter adapter = null;
    private boolean hasRendered = false;
    /**是否显示标记*/
    private boolean showBadge = false;
    /**是否铺满屏幕*/
    private boolean scaleScreen = true;
    
	public PlayView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	protected void renderPuzzleImage(Bitmap pluzzleimage,int[] positionwrapAsign,String emptyPostionstr) {
		mPieceWidth = (int) Math.floor(pluzzleimage.getWidth() / mColumnCount);
		mPieceHeight = (int) Math.floor(pluzzleimage.getHeight() / mRowCount);
		mLumpDrawables = new Drawable[mColumnCount * mRowCount];
		for (int i = 0; i < mLumpDrawables.length; i++) {
			int py = i / mColumnCount;
			int px = i % mRowCount;
			mLumpDrawables[i] = new BitmapDrawable(Bitmap.createBitmap(pluzzleimage, px * mPieceWidth, py
					* mPieceHeight, mPieceWidth, mPieceHeight));
		}
		mEmptyDrawable = new BitmapDrawable(Bitmap.createBitmap(mPieceWidth, mPieceHeight, Bitmap.Config.ARGB_8888));
		this.mPositionWrap = new int[mColumnCount * mRowCount];
		for (int i = 0; i < this.mPositionWrap.length; i++) {
			this.mPositionWrap[i] = i;
		}
		mPieceViews = new TextView[mColumnCount * mRowCount];
		initLumpViews();
		if(positionwrapAsign == null){
			wrapposition();
		}else{
			for (int i = 0; i < this.mPositionWrap.length; i++) {
				this.mPositionWrap[i] = positionwrapAsign[i];
			}
		}
		if(emptyPostionstr == null){
			emptyPostion = random.nextInt(mPositionWrap.length);
		}else{
			emptyPostion = Integer.parseInt(emptyPostionstr);
		}
		
		initImageViews();
		adapter = new LumpAdapter();
		setNumColumns(mColumnCount);
		this.setAdapter(adapter);
		hasRendered = true;
	}
	
	public boolean isShowBadge() {
		return this.showBadge;
	}
	
	/*
	 * 设置grid空间中的图片
	 */
	protected void initImageViews() {
		int imgIdx = -1;
		for (int i = 0; i < mPositionWrap.length; i++) {
			imgIdx = mPositionWrap[i];
			if(this.isShowBadge())
			 mPieceViews[i].setText(String.valueOf(imgIdx));
			else{
				 mPieceViews[i].setText(String.valueOf(""));
			}
			if (imgIdx != emptyPostion) {
 
				mPieceViews[i].setBackgroundDrawable(mLumpDrawables[imgIdx]);
			 } else {
				 
				mPieceViews[i].setBackgroundDrawable(mEmptyDrawable);
				
			}
		}

	}
	
	/*
	 * 初始化要渲染图片切片的view
	 */
	private void initLumpViews() {
		for (int i = 0; i < mLumpDrawables.length; i++) {
		 
			TextView v = new TextView(getContext());
			v.setPadding(2, 2, 2, 2);
			v.setBackgroundColor(0xffffff);
			if(scaleScreen){
				v.setWidth(mPieceWidth);
				v.setHeight(mPieceHeight);
			}
		
			v.setGravity(Gravity.TOP|Gravity.RIGHT);
			//v.setTextColor(R.color.badgeColor);
			v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
 
			mPieceViews[i] = v;
		}
	}
	
	/*
	 * 打乱图片顺序
	 */
	protected void wrapposition() {
		for (int i = 0; i < 10; i++) {
			int p1 = random.nextInt(mPositionWrap.length);
			int p2 = random.nextInt(mPositionWrap.length);
			if (p1 != p2) {
				wrapPosition(p1, p2);
			}
		}
		if (isOriginalPosition()) {
			wrapposition();
		}
	}
	
	/*
	 * 是否是原始的拼图切片索引，如果是则说明拼图成功
	 */
	private boolean isOriginalPosition() {
		boolean rs = true;
		for (int i = 0; rs && i < mPositionWrap.length; i++) {
			if (mPositionWrap[i] != i) {
				rs = false;
			}
		}
		return rs;
	}
	
	/*
	 * 交换两个切片的位置
	 */
	private void wrapPosition(int p1, int p2) {
		int pv1 = mPositionWrap[p1];
		int pv2 = mPositionWrap[p2];
		mPositionWrap[p2] = pv1;
		mPositionWrap[p1] = pv2;
	}
	
	/*
	 * 渲染拼图切块的适配器
	 */
	class LumpAdapter extends BaseAdapter {

		public int getCount() {

			return mColumnCount * mRowCount;
		}

		public Object getItem(int position) {

			return position;
		}

		public long getItemId(int position) {

			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			 
			return mPieceViews[position];
		}

	}
}
