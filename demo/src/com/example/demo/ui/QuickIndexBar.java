package com.example.demo.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * 快速索引栏
 * @author poplar
 *
 */
public class QuickIndexBar extends View {
	
	private static final String[] LETTERS = new String[]{
		"A", "B", "C", "D", "E", "F",
		"G", "H", "I", "J", "K", "L",
		"M", "N", "O", "P", "Q", "R",
		"S", "T", "U", "V", "W", "X",
		"Y", "Z"};
	private int cellWidth;
	private float cellHeight;
	private int mHeight;
	private Paint mPaint;
	
	public QuickIndexBar(Context context) {
		this(context, null);
	}

	public QuickIndexBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public QuickIndexBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.WHITE);
		mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics()));
		mPaint.setTypeface(Typeface.DEFAULT_BOLD);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		for (int i = 0; i < LETTERS.length; i++) {
			String letter = LETTERS[i];
			float textWidth = mPaint.measureText(letter);
			int x = (int) (cellWidth / 2f - textWidth / 2f);
			
			Rect bounds = new Rect();
			// 获取文本所在矩形
			mPaint.getTextBounds(letter, 0, letter.length(), bounds);
			
			// cell高度一半 + 文本高度一半 + 之前字母所占区域
			int y = (int) (cellHeight / 2f + bounds.height() / 2f + cellHeight * i);
			
			mPaint.setColor(touchIndex == i ? Color.GRAY : Color.WHITE);
			
			canvas.drawText(letter, x, y, mPaint);
		}
		
	}
	public interface OnLetterUpdateListener{
		void onLetterUpdate(String letter);
	}
	private OnLetterUpdateListener onLetterUpdateListener;
	public OnLetterUpdateListener getOnLetterUpdateListener() {
		return onLetterUpdateListener;
	}

	public void setOnLetterUpdateListener(
			OnLetterUpdateListener onLetterUpdateListener) {
		this.onLetterUpdateListener = onLetterUpdateListener;
	}

	int touchIndex = -1;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		switch (MotionEventCompat.getActionMasked(event)) {
			case MotionEvent.ACTION_DOWN:
				// 获取当前触摸到的字母索引
				int index = (int) (event.getY() / cellHeight);
				if(touchIndex != index){
					// 如果发生了变化
					if(index >= 0 && index < LETTERS.length){
						// 值是有效值
						String l = LETTERS[index];
						Log.d("TAG", "letter: " + l);
						if(onLetterUpdateListener != null){
							onLetterUpdateListener.onLetterUpdate(l);
						}
						touchIndex = index;
					}
				}
				
				break;
			case MotionEvent.ACTION_MOVE:
				// 获取当前触摸到的字母索引
				int i = (int) (event.getY() / cellHeight);

				if(touchIndex != i){
					// 如果发生了变化
					if(i >= 0 && i < LETTERS.length){
						String l = LETTERS[i];
						Log.d("TAG", "letter: " + l);
						if(onLetterUpdateListener != null){
							onLetterUpdateListener.onLetterUpdate(l);
						}
						touchIndex = i;
					}
				}
				
				break;
			case MotionEvent.ACTION_UP:
				touchIndex = -1;
				
				break;
			default:
				break;
		}
		invalidate();
		
		return true;
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		mHeight = getMeasuredHeight();
		
		cellWidth = getMeasuredWidth();
		cellHeight = getMeasuredHeight() * 1.0f / LETTERS.length;
		
	}
	

}
