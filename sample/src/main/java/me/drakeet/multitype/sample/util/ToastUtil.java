package me.drakeet.multitype.sample.util;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class ToastUtil {


    public static final int SHOW_TOAST = 0;

    private static Handler baseHandler = new ToastHandler();

    private static Toast mToast;

    public static void showToast(Context context, String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast != null)
                mToast.cancel();
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    public static void showToast(Context context, int resId) {
        showToast(context, context.getString(resId));
    }

    public static void showToast(Context context, int resId, int gravity) {
        Toast toast = Toast.makeText(context, context.getResources().getString(resId), Toast.LENGTH_SHORT);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

    public static void showToastInThread(Context context, int resId) {
        Message msg = baseHandler.obtainMessage(SHOW_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString("TEXT", context.getResources().getString(resId));
        msg.setData(bundle);
        msg.obj = context;
        baseHandler.sendMessage(msg);
    }

    public static void showToastInThread(Context context, String text) {
        Message msg = baseHandler.obtainMessage(SHOW_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString("TEXT", text);
        msg.setData(bundle);
        msg.obj = context;
        baseHandler.sendMessage(msg);
    }


    public static void showCustomToast(Context context, int resId, int textViewId, String toastText, int duration) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(resId, null);
        TextView toastTextView = (TextView) layout.findViewById(textViewId);
        toastTextView.setText(toastText);
        Toast toast = new Toast(context.getApplicationContext());
        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setView(layout);
        toast.show();
    }

    public static void cancleToast() {
        if (mToast != null)
            mToast.cancel();
    }

    static class ToastHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_TOAST:
                    showToast((Context) msg.obj, msg.getData().getString("TEXT"));
                    break;
                default:
                    break;
            }
        }
    }
}
