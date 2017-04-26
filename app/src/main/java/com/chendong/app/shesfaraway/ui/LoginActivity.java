package com.chendong.app.shesfaraway.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.baidu.speech.VoiceRecognitionService;
import com.chendong.app.shesfaraway.R;
import com.chendong.app.shesfaraway.bean.VoiceResults;
import com.hanks.htextview.scale.ScaleTextView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者：陈东
 * Github.com/chendongde310
 * 日期：2017/4/25 - 1:26
 * 语音登陆
 */
public class LoginActivity extends AppCompatActivity implements RecognitionListener {

    private SpeechRecognizer speechRecognizer;
    private ScaleTextView text;
    private CircleImageView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.login = (CircleImageView) findViewById(R.id.login);
        this.text = (ScaleTextView) findViewById(R.id.text);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 2000);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this, new ComponentName(this, VoiceRecognitionService.class));
        speechRecognizer.setRecognitionListener(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speechRecognizer.startListening(new Intent());
                text.animateText("请悄悄告诉我你的答案");
                login.setFillColor(getResources().getColor(R.color.colorPrimary));
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        speechRecognizer.stopListening();
                    }
                });
            }
        });

    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        Logger.d("准备就绪");

    }

    @Override
    public void onBeginningOfSpeech() {
        Logger.d("开始说话");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Logger.d("音量变化");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Logger.d("获取原始语音" + buffer.toString());
    }

    @Override
    public void onEndOfSpeech() {
        Logger.d("说话结束");
        speechRecognizer.stopListening();
    }

    @Override
    public void onError(int error) {
        waiting(2000);
        Logger.d("识别出错" + error);
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> nbest = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        Logger.d("识别成功：" + Arrays.toString(nbest.toArray(new String[nbest.size()])));
        String json_res = results.getString("origin_result");

        try {
            VoiceResults voiceResults = JSON.parseObject(json_res, VoiceResults.class);
            Verification(voiceResults.toContentString());

        } catch (Exception e) {
            waiting(2000);
            Logger.e("错误的json数据\n" + json_res);
        }


    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Logger.d("识别临时结果");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Logger.d("识别事件返回");
    }

    AVObject avData;

    /**
     * 验证口令是否正确
     */
    private void Verification(final List<String> strings) {
        login.setVisibility(View.GONE);
        text.animateText(avData.get("content").toString());
        waiting(2000);
        avData.put("answer", strings);
        avData.saveInBackground();
    }


    private void waiting(long time) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                LoginActivity.this.finish();
            }
        }, time);

    }


    private void getData() {
        AVQuery<AVObject> query = new AVQuery<>("Constant");
        query.whereContains("Type", "口令");
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if(e==null){
                    avData = avObject;
                    text.animateText(avData.get("question").toString());
                    login.setVisibility(View.VISIBLE);
                }else {
                    text.animateText("系统错误，请联系玩家二狗");
                   Logger.d(e.getMessage()+e.getCode());
                }

            }
        });
    }


}




