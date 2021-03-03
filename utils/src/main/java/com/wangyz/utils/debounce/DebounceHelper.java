package com.wangyz.utils.debounce;

import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

public class DebounceHelper {

    /**
     * 默认冻结时间
     */
    private static final long FROZEN_TIME = 500L;

    /**
     * 最小冻结时间
     * Kotlin或者设置了代理，导致onClick调用两次,从而shouldClick返回false。
     * 通过判定在 MIN_FROZEN_TIME 时间内发生两次onClick，即认为是系统回调，返回true
     */
    private static final long MIN_FROZEN_TIME = 10L;

    /**
     * View点击时的Tag
     */
    private static final int CLICK_TAG = 1 << 25;

    /**
     * 执行点击的View的HashMap
     */
    private static final Map<View, WeakView> WEAK_VIEW_MAP = new WeakHashMap<>();

    /**
     * 是否应该执行点击事件
     *
     * @param target 目标View
     * @return 是否应该执行点击事件
     */
    public static boolean shouldClick(View target) {

        if (target == null) {
            return false;
        }

        boolean flag = hasIgnoreFlag(target);

        System.out.println("shouldClick invoke,view:" + target + ",ignoreFlag:" + flag);

        if (flag) {
            return true;
        }

        long now = System.currentTimeMillis();

        WeakView weakView = WEAK_VIEW_MAP.get(target);

        if (weakView == null) {
            weakView = new WeakView(target);
            weakView.setClickTime(now);
            WEAK_VIEW_MAP.put(target, weakView);
            return true;
        }

        if (now <= weakView.getClickTime() + MIN_FROZEN_TIME) {
            weakView.setClickTime(now);
            return true;
        }

        if (now > weakView.getClickTime() + FROZEN_TIME) {
            weakView.setClickTime(now);
            return true;
        }

        weakView.setClickTime(now);

        return false;
    }

    /**
     * View需要双击事件，设置忽略Flag
     *
     * @param view
     */
    public static void setIgnoreFlag(View view) {
        view.setTag(CLICK_TAG, true);
    }

    private static boolean hasIgnoreFlag(View view) {
        try {
            Object flag = view.getTag(CLICK_TAG);
            if (flag != null) {
                return (boolean) flag;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 子View需要双击事件，设置忽略Flag
     *
     * @param view
     */
    public static void setChildIgnoreFlag(View view) {
        if (view == null) {
            return;
        }
        if (view instanceof ViewGroup) {
            int count = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < count; i++) {
                ((ViewGroup) view).getChildAt(i).setTag(CLICK_TAG, true);
            }
        }
    }

    private static class WeakView extends WeakReference<View> {

        private long clickTime;

        public WeakView(View referent) {
            super(referent);
        }

        public long getClickTime() {
            return clickTime;
        }

        public void setClickTime(long clickTime) {
            this.clickTime = clickTime;
        }
    }
}
