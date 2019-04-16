package sit.kingshing.common.airpanel;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Method;


public final class Util {
    public static boolean DEBUG = false;

    public static void log(String format, Object... args) {
        if (DEBUG)
            Log.d("AirPanel", String.format(format, args));
    }

    public static void showKeyboard(final View view) {
        view.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) view.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, 0);
    }

    public static void hideKeyboard(final View view) {
        InputMethodManager imm =
                (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    static int getDefaultPanelHeight(Context context, AirAttribute attribute) {
        int defaultHeight = (int) ((attribute.panelMaxHeight + attribute.panelMinHeight) / 2.0f + 0.5f);
        defaultHeight = PanelSharedPreferences.get(context, defaultHeight);
        return defaultHeight;
    }

    static void updateLocalPanelHeight(Context context, int height) {
        PanelSharedPreferences.save(context, height);
    }

    private static class PanelSharedPreferences {
        private final static String FILE_NAME = "AirPanel.sp";
        private final static String KEY_PANEL_HEIGHT = "panelHeight";
        private volatile static SharedPreferences SP;

        public static boolean save(final Context context, final int keyboardHeight) {
            return sp(context).edit()
                    .putInt(KEY_PANEL_HEIGHT, keyboardHeight)
                    .commit();
        }

        private static SharedPreferences sp(final Context context) {
            if (SP == null) {
                synchronized (PanelSharedPreferences.class) {
                    if (SP == null) {
                        SP = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
                    }
                }
            }
            return SP;
        }

        public static int get(final Context context, final int defaultHeight) {
            return sp(context).getInt(KEY_PANEL_HEIGHT, defaultHeight);
        }
    }

    public static int getVirtualBarHeight(Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            //vh = dm.heightPixels - display.getHeight();
            Point point = new Point();
            display.getSize(point);
            vh = dm.heightPixels - point.y;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }


    public static boolean phoneHasNav(Activity activity) {
        boolean flag = false;

        if (Build.VERSION.SDK_INT < 14) {
            flag = false;
        } else {
            View content = activity.getWindow().getDecorView().findViewById(android.R.id.content);
            if (content != null) {
                WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                Point point = new Point();
                display.getRealSize(point);

                if (isScreenPortrait(activity)) {
                    int bottom = content.getBottom();// 页面的底部
                    if (bottom != point.y) {
                        flag = true;
                    }
                } else {
                    int right = content.getRight();
                    if (right != point.y) {
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }


    /**
     * 获取屏幕是否是竖屏
     *
     * @return
     */
    @SuppressLint("SwitchIntDef")
    public static boolean isScreenPortrait(Activity activity) {
        int or = activity.getRequestedOrientation();
        switch (or) {
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:// 横屏
            case ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE:
                return false;
            case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:// 竖屏
            case ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT:
                return true;
            default:
                return true;
        }
    }


    @TargetApi(28)
    public static int getNotchParams(Activity activity) {
        final View decorView = activity.getWindow().getDecorView();
        DisplayCutout displayCutout = decorView.getRootWindowInsets().getDisplayCutout();
        if (displayCutout == null) {
            return 0;
        }
        return displayCutout.getSafeInsetTop();
    }

    /**
     * 获取华为刘海高度
     *
     * @param context
     * @return
     */
    public static int[] getNotchSize(Context context) {
        int[] ret = new int[]{0, 0};
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            ret = (int[]) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Log.e("haha", "getNotchSize ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("haha", "getNotchSize NoSuchMethodException");
        } catch (Exception e) {
            Log.e("haha", "getNotchSize Exception");
        } finally {
            return ret;
        }
    }

}