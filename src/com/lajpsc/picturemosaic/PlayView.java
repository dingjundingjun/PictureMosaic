package com.lajpsc.picturemosaic;


import java.text.MessageFormat;
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
import android.widget.Toast;

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
    private int mColumnCount = Util.PICTURE_COLUMN;
    /**行数*/
    private int mRowCount = Util.PICTURE_ROW;
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
    /**移动的步数*/
    private int mMoveCount = 0;
    /**模式*/
    public int mMode = Util.GAME_LEVEL_EASY;
	public PlayView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setShowBadge(boolean showBadge) {
		this.showBadge = showBadge;
	}
	
	public void setColunmCount(int cc)
	{
		this.mColumnCount = cc;
	}
	
	public void setRowCount(int row)
	{
		this.mRowCount = row;
	}
	
	/*
	 * 隐藏图片标记
	 */
	protected void hideBadge(){
		for (int i = 0; i < mPositionWrap.length; i++) {
			mPieceViews[i].setText(String.valueOf(""));
		}
		setShowBadge(false);
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
		resetEmpayPosition();
		adapter = new LumpAdapter();
		initImageViews();
		setNumColumns(mColumnCount);
		this.setAdapter(adapter);
		hasRendered = true;
	}
	
	/*
	 * 显示图片标记
	 */
	protected void showBadge(){
		for (int i = 0; i < mPositionWrap.length; i++) {
			int imgIdx = mPositionWrap[i];
			mPieceViews[i].setText(String.valueOf(imgIdx));
		}
		 setShowBadge(true);
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
	
	public boolean solvability(int order[], int size){
	    int a;
	    int count = 0;
	    int m = 0;
	    int n = 0;
	    
	    int len = order.length;
//	    size = size || 3;
	    //[0,1,2,3,4,5,7,6,8]
	    for(int i=0; i<len; i++){
	        a = order[i];
	        //if( a == 8){
	        if(a == size*size-1){
	            m = i / size;
	            n = i % size;
	        }
	        for(int j=i+1; j<len; j++){
	            
	            if(order[j]<a){
	                count++;
	            }
	        }
	    }
	    count += m;
	    count += n;
	    return count%2 == 0;
	}
	
	/*
	 * 打乱图片顺序
	 */
	protected void wrapposition() {
		for (int i = 0; i < mPositionWrap.length / 2; i++) {
			int p1 = random.nextInt(mPositionWrap.length);
			int p2 = random.nextInt(mPositionWrap.length);
			if (p1 != p2) {
				wrapPosition(p1, p2);
			}
		}
		if(!solvability(mPositionWrap, mMode))
		{
			wrapposition();
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
	
	/*
	 * 设置 点击事件换图片位置
	 */
	@Override
	public boolean performItemClick(View view, int position, long id) {
		if (emptyPostion < 0 || mPositionWrap[position] == emptyPostion) {
			return true;
		}
		int leftpoint = -1;
		int rightpoint = -1;
		int toppoint = -1;
		int bottompoint = -1;
		
		if (position % mColumnCount != 0) {
			leftpoint = position - 1;
			if (mPositionWrap[leftpoint] == this.emptyPostion) {
				wrapOneStep(leftpoint, position);
				return true;
			}
		}
		if (position / mColumnCount >= 1) {
			toppoint = position - mColumnCount;
			if (mPositionWrap[toppoint] == this.emptyPostion) {
				wrapOneStep(toppoint, position);
				return true;
			}
		}
		if (position % mColumnCount != mColumnCount - 1) {
			rightpoint = position + 1;
			if (mPositionWrap[rightpoint] == this.emptyPostion) {
				wrapOneStep(rightpoint, position);
				return true;
			}
		}
		if (position / mColumnCount < mRowCount - 1) {
			bottompoint = position + mColumnCount;
			if (mPositionWrap[bottompoint] == this.emptyPostion) {
				wrapOneStep(bottompoint, position);
				return true;
			}
		}
		
		return true;
	}
	
	public void setMoveCount(int moveCount) {
		this.mMoveCount = moveCount;
	}
	
	/*
	 * 交换两个位置的索引
	 */
	private void wrapOneStep(int p1, int p2) {
		mMoveCount++;
		int pv1 = mPositionWrap[p1];
		int pv2 = mPositionWrap[p2];
		wrapPosition(p1, p2);
		if (pv2 != emptyPostion) {
			mPieceViews[p1].setBackgroundDrawable(mLumpDrawables[pv2]);
			mPieceViews[p2].setBackgroundDrawable(mEmptyDrawable);
 
		} else {
			mPieceViews[p1].setBackgroundDrawable(mEmptyDrawable);
			mPieceViews[p2].setBackgroundDrawable(mLumpDrawables[pv1]);
 
		}
		if(this.isShowBadge()){
			mPieceViews[p1].setText(String.valueOf(pv2));
			mPieceViews[p2].setText(String.valueOf(pv1));
		}
		
		if (isOriginalPosition()) {
			successPuzzle();
		 }
		

	}
	
	/*
	 * 重新设置emptyPostion
	 */
	protected void resetEmpayPosition(){
		emptyPostion = mPositionWrap.length - 1;
	}
	
	public boolean isScaleScreen() {
		return this.scaleScreen;
	}
	
	public void setScaleScreen(boolean scaleScreen) {
		this.scaleScreen = scaleScreen;
	}
	
	void successPuzzle(){
		mPieceViews[emptyPostion].setBackgroundDrawable(mLumpDrawables[emptyPostion]);
		this.emptyPostion = -1;
		showSuccessToast();
	}
	
	void showSuccessToast() {
		String msg = MessageFormat.format(getContext().getResources().getString(R.string.puzzleSuccessfull), System.getProperty("line.separator"),String.valueOf(getMoveCount()));
		Toast.makeText(getContext(),msg, Toast.LENGTH_LONG).show();
		//Toast.makeText(getContext(), "fdljflds"+System.getProperty("line.separator")+"fjjfldjflds", 10).show();
    }
	
	public int getMoveCount() {
		return this.mMoveCount;
	}
}
