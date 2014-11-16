package com.lajps.picturemosaic;

import com.lajps.picturemosaic.LevelDialog.onChooseListener;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class PlayActivity extends Activity
{
	private int mID;
	private PlayView mPlayView;
	private Bitmap mBitmapForGame;
	private Menu mMenu;
	private ViewGroup mContainer;
	private View mPlayLayout;
	private ImageView mOriginalView;
	private boolean bShowBadge = false;
	private boolean mScaleScreen = false;
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
		mPlayLayout = (View)findViewById(R.id.play_layout);
		mOriginalView = (ImageView) findViewById(R.id.originalPicView);
		
		mBitmapForGame = BitmapFactory.decodeResource(this.getResources(),Util.gPic[mID]);
		mPlayView.mMode = Util.GAME_LEVEL_EASY;
		mPlayView.renderPuzzleImage(mBitmapForGame,null,"8");
		mOriginalView.setBackgroundResource(Util.gPic[mID]);
		
		mContainer = (ViewGroup) findViewById(R.id.puzzleContainer);
		mContainer
				.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
	}
	
	/*
	 * 创建menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = this.getMenuInflater();
		inflater.inflate(R.menu.puzzlemenu, menu);
		this.mMenu = menu;
		if (mPlayView.isShowBadge()) {
			menu.findItem(R.id.hideBadge).setVisible(true);
			menu.findItem(R.id.displayBadge).setVisible(false);
		}
		if (mPlayView.isScaleScreen()) {
//			menu.findItem(R.id.scaleFit).setVisible(true);
//			menu.findItem(R.id.scaleScreen).setVisible(false);
		}
		return true;
	}
	
	/*
	 * 重新开始游戏
	 */
	private void restartGame() {
		mPlayView.resetEmpayPosition();
		mPlayView.wrapposition();
		mPlayView.initImageViews();
		mPlayView.setMoveCount(0);

	}
	
	private final class DisplayNextView implements Animation.AnimationListener {
		private final boolean showOriginal;

		private DisplayNextView(boolean showOriginal) {
			this.showOriginal = showOriginal;
		}

		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
			mContainer.post(new SwapViews(showOriginal));
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}
	
	private final class SwapViews implements Runnable {
		private final boolean showOriginal;

		public SwapViews(boolean showOriginal) {
			this.showOriginal = showOriginal;
		}

		public void run() {
			final float centerX = mContainer.getWidth() / 2.0f;
			final float centerY = mContainer.getHeight() / 2.0f;
			Rotate3dAnimation rotation;

			if (showOriginal) {
				mPlayLayout.setVisibility(View.GONE);
				mOriginalView.setVisibility(View.VISIBLE);
				mOriginalView.requestFocus();

				rotation = new Rotate3dAnimation(90, 0, centerX, centerY,
						310.0f, false);
			} else {
				mOriginalView.setVisibility(View.GONE);
				mPlayLayout.setVisibility(View.VISIBLE);
				mPlayView.requestFocus();

				rotation = new Rotate3dAnimation(90, 0, centerX, centerY,
						310.0f, false);
			}

			rotation.setDuration(500);
			rotation.setFillAfter(true);
			rotation.setInterpolator(new DecelerateInterpolator());

			mContainer.startAnimation(rotation);
		}
	}
	
	private void applyRotation(boolean showOriginal, float start, float end) {
		// Find the center of the container
		final float centerX = mContainer.getWidth() / 2.0f;
		final float centerY = mContainer.getHeight() / 2.0f;

		// Create a new 3D rotation with the supplied parameter
		// The animation listener is used to trigger the next animation
		final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end,
				centerX, centerY, 310.0f, true);
		rotation.setDuration(500);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(new DisplayNextView(showOriginal));

		mContainer.startAnimation(rotation);
	}
	
	/*
	 * 显示拼图view
	 */
	private void showPuzzleView(){
		applyRotation(false, 180, 90);
		mMenu.findItem(R.id.displayPuzzleView).setVisible(false);
		mMenu.findItem(R.id.displayOriginalPic).setVisible(true);
	}
	
	public boolean isShowBadge() {
		return this.bShowBadge;
	}

	public void setShowBadge(boolean showBadge) {
		this.bShowBadge = showBadge;
	}

	
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		int itemId = item.getItemId();
		switch (itemId) {
		case R.id.reStrartGame: {
			restartGame();
			break;
		}
		case R.id.displayOriginalPic: {
			applyRotation(true, 0, 90);
			item.setVisible(false);
			mMenu.findItem(R.id.displayPuzzleView).setVisible(true);
			break;
		}
		case R.id.displayPuzzleView: {
			showPuzzleView();
			break;
		}
		case R.id.displayBadge: {
			mPlayView.showBadge();
			item.setVisible(false);
			mMenu.findItem(R.id.hideBadge).setVisible(true);
			this.setShowBadge(true);
			break;
		}
		case R.id.hideBadge: {
			mPlayView.hideBadge();
			item.setVisible(false);
			mMenu.findItem(R.id.displayBadge).setVisible(true);
			this.setShowBadge(false);
			break;
		}
//		case R.id.scaleFit:{
////			scaleImgFit();
//			break;
//		}
//		case R.id.scaleScreen:{
////			scaleImgScreen();
//			break;
//		}
//		case R.id.changePic:{
//			//showSelectImgDialog();
//			break;
//		}
		case R.id.changeLevel:{
			LevelDialog dialog = new LevelDialog(this);
			dialog.setChooseListener(new onChooseListener() {
				
				public void choose(int mode) {
					changeLevel(mode);
				}
			});
			dialog.show();
			break;
		}
		}

		return true;
	}
	
	public void changeLevel(int level)
	{
		int colunm = 3;
		int row = 3;
		if(level == Util.GAME_LEVEL_EASY)
		{
			colunm = level;
			row = level;
		}
		else if(level == Util.GAME_LEVEL_ORIGINAL)
		{
			colunm = level;
			row = level;
		}
		else if(level == Util.GAME_LEVEL_HARD)
		{
			colunm = level;
			row = level;
		}
		mPlayView.mMode = level;
		mPlayView.setColunmCount(colunm);
		mPlayView.setRowCount(row);
		renderNewImage();
	}
	
	/*
	 * 渲染新的图片
	 */
	private void renderNewImage(){
		 mPlayView.setMoveCount(0);
		 initPuzzleView();
	}
	
	/*
	 * 初始化拼图的view
	 */
	private void initPuzzleView() {
		mPlayView.renderPuzzleImage(mBitmapForGame,null,"8");
		
	}

	
//	private void initScalScreenMenu(boolean isScreen){
//		 mMenu.findItem(R.id.scaleFit).setVisible(isScreen);
//		 mMenu.findItem(R.id.scaleScreen).setVisible(!isScreen);
//	}
	
	public boolean isScaleScreen() {
		return this.mScaleScreen;
	}

	public void setScaleScreen(boolean scaleScreen) {
		this.mScaleScreen = scaleScreen;
	}
	
	/*
     * 将图片渲染为合适大小
     */
	private void scaleImgFit() {
//		 initScalScreenMenu(false);
//	     this.setScaleScreen(false);
//		 if(puzzleViewFit.isHasRendered()){
//			 puzzleViewFit.resetPosition(puzzleViewScreen.getPositionwrap());
//			 puzzleViewFit.setEmptyPostion(puzzleViewScreen.getEmptyPostion());
//			 puzzleViewFit.initImageViews();
//			
//		 }else{
//			 puzzleViewFit.renderPuzzleImage(this.isScaleScreen()?imgBitmapForScreen:imgBitmapForFit,puzzleViewScreen.getPositionwrap(),String.valueOf(puzzleViewScreen.getEmptyPostion()));
//		 }
//		 puzzleViewFit.setMoveCount(puzzleViewScreen.getMoveCount());
//		 
//		 setPuzzleView();
//		
//		 initBadeAfterChagne();
	}
}	
