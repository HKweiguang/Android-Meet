package com.huangk.framework.manager;

import android.content.Context;

import cn.bmob.v3.Bmob;

public class BmobManager {

    private static volatile BmobManager mInstance;

    private BmobManager() {
    }

    public static BmobManager getInstance() {
        if (mInstance == null) {
            synchronized (BmobManager.class) {
                if (mInstance == null) {
                    mInstance = new BmobManager();
                }
            }
        }
        return mInstance;
    }

    private static final String BMOB_ID = "02ce8f107213ef663f2499f3498c8309";

    /**
     * 初始化 Bmob
     */
    public void initBmob(Context context) {
        Bmob.initialize(context, BMOB_ID);
    }

}
