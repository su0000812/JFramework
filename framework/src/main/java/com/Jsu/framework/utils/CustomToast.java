package com.Jsu.framework.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.Jsu.framework.R;


/**
 * Created by JSu on 2016/6/8.
 */
public class CustomToast {

	private static Toast mToast;
	private static TextView tv;

	/**
	 * 显示toast消息(解决toast重复点击叠加显示的问题 2013.0522 By 门前大桥下)
	 * 
	 * @param mContext
	 * @param text
	 * @param duration
	 */
	public static void showToast(Context mContext, String text, int duration) {
		// Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
		// mContext = mContext.getApplicationContext();
		if (mToast != null) {
			tv.setText(text);
		} else {
			mToast = new Toast(mContext);
			mToast.setGravity(Gravity.CENTER, 0, 0);
			View view = LayoutInflater.from(mContext).inflate(R.layout.transient_notification, null);
			tv = (TextView) view.findViewById(R.id.message);
			tv.setText(text);
			mToast.setView(view);
		}
		if (duration != 0) {
			mToast.setDuration(duration);
		} else {
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}
	
	public static void showToast(Context mContext, int resId, int duration) {
		showToast(mContext, mContext.getResources().getString(resId), duration);
	}
}
