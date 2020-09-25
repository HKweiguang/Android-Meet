package com.huangk.framework;

import android.content.Context;

import com.huangk.framework.manager.BmobManager;
import com.huangk.framework.utils.SpUtils;

public class FrameWork {

    private volatile static FrameWork mFrameWork;

    private FrameWork() {}

    public static FrameWork getInstance() {
        if (mFrameWork == null) {
            synchronized (FrameWork.class) {
                if (mFrameWork == null) {
                    mFrameWork = new FrameWork();
                }
            }
        }

        return mFrameWork;
    }

    /**
     * 初始化框架
     */
    public void initFrameWork(Context context) {
        SpUtils.getInstance().initSp(context);
        BmobManager.getInstance().initBmob(context);
    }
}
