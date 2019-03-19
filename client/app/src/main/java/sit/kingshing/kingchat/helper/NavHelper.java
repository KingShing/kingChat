package sit.kingshing.kingchat.helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;


/**
 * 解决fragment调度和复用问题
 */
public class NavHelper<T> {
    private SparseArray<Tab<T>> tabs = new android.util.SparseArray();
    private final Context context;
    private final FragmentManager fragmentManager;
    private final int containerId;
    private final OnTabChangedListener<T> listener;

    private Tab<T> currentTab;

    public NavHelper<T> add(int menuId, Tab<T> tab) {
        tabs.put(menuId, tab);
        return this;
    }

    public Tab<T> getCurrentTab() {
        return currentTab;
    }

    public NavHelper(Context context, FragmentManager fragmentManager, int containerId, OnTabChangedListener<T> mListener) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
        this.listener = mListener;
    }

    public boolean performClickMenu(int menuId) {
        Tab<T> tab = tabs.get(menuId);
        if (tab != null) {
            doSelected(tab);
            return true;
        }
        return false;
    }

    private void doSelected(Tab<T> tab) {
        Tab<T> oldTab = null;
        if (currentTab != null) {
            oldTab = currentTab;
            if (tab == oldTab) {
                notifyTabReselect(tab);
                return;
            }
        }

        currentTab = tab;
        doTabChanged(currentTab, oldTab);
    }

    private void doTabChanged(Tab<T> newTab, Tab<T> oldTab) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (oldTab != null) {
            if (oldTab.fragment != null) {
                ft.detach(oldTab.fragment);
            }
        }

        if (newTab != null) {
            if (newTab.fragment == null) {
                Fragment fragment = Fragment.instantiate(context, newTab.clx.getName(), null);
                newTab.fragment = fragment;
                ft.add(containerId, fragment, newTab.clx.getName());
            } else {
                ft.attach(newTab.fragment);
            }
        }
        //提交事务
        ft.commit();

        //通知回调
        notifyTabSelect(newTab,oldTab);

    }

    private void notifyTabReselect(Tab<T> tab) {
        //TDO 二次点击所做的操作
    }


    /**
     * 回调监听器
     * @param newTab
     * @param oldTab
     */
    private void notifyTabSelect(Tab<T> newTab,Tab<T> oldTab) {
        if (listener!=null) {
            listener.onTabChanged(newTab,oldTab);
        }
    }


    public static class Tab<T> {
        public Class<? extends Fragment> clx;
        public T extra;

        public Tab(Class<? extends Fragment> clx, T extra) {
            this.clx = clx;
            this.extra = extra;
        }

        Fragment fragment;
    }

    public interface OnTabChangedListener<T> {
        void onTabChanged(Tab<T> newTab, Tab<T> oldTab);
    }
}
