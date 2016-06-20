package com.Jsu.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by JSu on 2016/6/8.
 */

public class ViewUtils {

    /**
     * 获取屏幕的长宽属性
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);

        return dm;
    }

    /**
     * 显示Toast信息
     *
     * @param context
     * @param msgResId 要显示的文本资源ID
     */
    public static void toast(Context context, int msgResId) {
        CustomToast.showToast(context, msgResId, Toast.LENGTH_SHORT);
    }

    public static void toast(Context context, int msgResId, int duration) {
        CustomToast.showToast(context, msgResId, duration);
    }

    /**
     * 显示Toast信息
     *
     * @param context
     * @param msg 要显示的文本
     */
    public static void toast(Context context, CharSequence msg) {
        CustomToast.showToast(context, msg + "", 0);
    }

    /**
     *
     * @param context
     * @param msg
     * @param duration
     */
    public static void toast(Context context, CharSequence msg, int duration) {
        CustomToast.showToast(context, msg + "", duration);
    }

    /**
     * 将px转为dip
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static final int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip转为px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static final int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     */
    public static void hideSoftInput(Activity context) {
        try {
            ((InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     */
    public static void hideSoftInput(Activity context, EditText edt) {
        try {
            ((InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(edt.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断软键盘状态
     *
     * @param context
     */
    public static boolean isSoftInputShowed(Activity context) {
        int softInputMode = context.getWindow().getAttributes().softInputMode;
        return softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED;
    }

    /**
     * 显示软键盘,控件ID可以是EditText,TextView
     *
     * @param context
     * @param edt
     */
    public static void showSoftInput(Activity context, EditText edt) {
        ((InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE)).showSoftInput(edt, 0);
    }

    public static void showByAlpha(View view) {
//        ViewHelper.setAlpha(view, 0f);
//        view.setVisibility(View.VISIBLE);
//        ViewPropertyAnimator.animate(view).alpha(1f).setDuration(300).setListener(null);
    }

    public static void hideByAlpha(final View view) {
//        ViewPropertyAnimator.animate(view).alpha(0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                view.setVisibility(View.GONE);
//            }
//        });
    }

    /**
     * 获取SDK版本
     *
     * @return
     */
    public static int getSDKVerSion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 给某个视图集中的子视图添加标签 给adapter调用
     *
     * @param convertView
     * @param ids
     */
    public static void storeToTag(View convertView, int... ids) {
        for (int id : ids) {
            convertView.setTag(id, convertView.findViewById(id));
        }
    }
}
