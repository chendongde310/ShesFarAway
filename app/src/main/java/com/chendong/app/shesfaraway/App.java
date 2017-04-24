package com.chendong.app.shesfaraway;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Administrator on 2017/4/25.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"CKJC3BzQPh799Ei4wLsedPYa-gzGzoHsz","t22e6HsGiN7FEk7Ky2LbOagr");
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVOSCloud.setDebugLogEnabled(true);
    }


}
