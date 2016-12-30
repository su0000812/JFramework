package com.Jsu.framework.utils;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.Jsu.framework.R;

/**
 * 手势识别测试Activity
 *
 *
 */
public class TestActivity extends FragmentActivity implements OnTouchListener, OnGestureListener {

	/** 手势识别 **/
	private GestureDetector mGestureDetector;

	/** ActionBar **/
//	private ActionBar actionbar;

	/** TextView **/
	private TextView mTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_activity);

		/* ActionBar */
//		actionbar = getSupportActionBar();
//		actionbar.setTitle("TestActivity");

		/* Find View */
		mTv = (TextView) findViewById(R.id.testTextView);
		mTv.setOnTouchListener(this);
		mTv.setFocusable(true);
		mTv.setClickable(true);
		mTv.setLongClickable(true);

		/* 手势识别 */
		mGestureDetector = new GestureDetector(this, this);
		mGestureDetector.setIsLongpressEnabled(true);
	}

	/**
	 * 在onTouch()方法中，我们调用GestureDetector的onTouchEvent()方法，
	 * 将捕捉到的MotionEvent交给GestureDetector 来分析是否有合适的callback函数来处理用户的手势
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		return mGestureDetector.onTouchEvent(event);
	}

	/**
	 * 用户轻触触摸屏，由1个MotionEvent ACTION_DOWN触发
	 */
	@Override
	public boolean onDown(MotionEvent e) {
		Log.e("MyGesture", "onDown");
		// ViewUtil.toast(this, "onDown");
		return true;
	}

	/**
	 * 用户轻触触摸屏，尚未松开或拖动，由一个1个MotionEvent ACTION_DOWN触发
	 * 注意和onDown()的区别，强调的是没有松开或者拖动的状态
	 */
	@Override
	public void onShowPress(MotionEvent e) {
		Log.e("MyGesture", "onShowPress");
		ViewUtils.toast(this, "onShowPress");
	}

	/**
	 * 用户（轻触触摸屏后）松开，由一个1个MotionEvent ACTION_UP触发
	 */
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		Log.i("MyGesture", "onSingleTapUp");
		ViewUtils.toast(this, "onSingleTapUp");
		return true;
	}

	/**
	 * 用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE, 1个ACTION_UP触发
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

		// 参数解释：
		// e1：第1个ACTION_DOWN MotionEvent
		// e2：最后一个ACTION_MOVE MotionEvent
		// velocityX：X轴上的移动速度，像素/秒
		// velocityY：Y轴上的移动速度，像素/秒

		// 触发条件 ：
		// X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒

		final int FLING_MIN_DISTANCE = 100, FLING_MIN_VELOCITY = 200;
		if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
			Log.i("MyGesture", "Fling left");
			ViewUtils.toast(this, "Fling left");
		} else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityY) > FLING_MIN_VELOCITY) {
			Log.i("MyGesture", "Fling right");
			ViewUtils.toast(this, "Fling right");
		}

		Log.i("MyGesture", "onFling");
		ViewUtils.toast(this, "onFling");
		return true;
	}

	/**
	 * 用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发
	 */
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		// Log.i("MyGesture", "onScroll");

		// 参数解释：
		// e1：第1个ACTION_DOWN MotionEvent
		// e2：最后一个ACTION_MOVE MotionEvent
		// distanceX：X轴上的移动速度，像素/秒
		// distanceY：Y轴上的移动速度，像素/秒

		Log.i("MyGesture", "MotionEvent ACTION_DOWN：" + e1.getX());
		Log.i("MyGesture", "MotionEvent ACTION_MOVE：" + e2.getX());
		ViewUtils.toast(this, "MotionEvent ACTION_MOVE：" + e2.getX());
		return true;

	}

	/**
	 * 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
	 */
	@Override
	public void onLongPress(MotionEvent e) {
		Log.i("MyGesture", "onLongPress");
		ViewUtils.toast(this, "onLongPress");
	}
}
