package com.lajps.picturemosaic;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class PlayView extends ImageView
{
	/**简单*/
	private int mode = 0;
	private boolean bInit = true;
	public PlayView(Context context, AttributeSet attrs) {
		super(context, attrs);
	
	}

	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
	}
	
	private void initBMP(Canvas canvas)
	{
		if(mode == 0)    //简单版本分3*3
		{
//				for (int x = 0; x < 3; x++) {
//					for (int y = 0; y < 3; y++) {
//						canvas.drawBitmap(littleBmp[map[x][y]], getScreenX(x),
//								getScreenY(y), null);
//					}
//				}
		}
	}
	
	private void drawSimple(Canvas canvas)
	{
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		
	}
}
