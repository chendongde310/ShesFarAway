package com.chendong.app.shesfaraway;

import android.app.Application;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.chendong.app.shesfaraway.ui.HomeActivity;
import com.orhanobut.logger.Logger;

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
        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            public void done(AVException e) {
                if (e == null) {
                    // 保存成功
                    String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
                    // 关联  installationId 到用户表等操作……
                } else {
                    // 保存失败，输出错误信息
                    Logger.d(e.toString());
                }
            }
        });
        // 设置默认打开的 Activity
        PushService.setDefaultPushCallback(this, HomeActivity.class);





    }


}
