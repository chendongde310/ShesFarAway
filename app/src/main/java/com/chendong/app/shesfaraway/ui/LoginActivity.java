package com.chendong.app.shesfaraway.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.baidu.speech.VoiceRecognitionService;
import com.chendong.app.shesfaraway.R;
import com.chendong.app.shesfaraway.bean.VoiceResults;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * 作者：陈东
 * Github.com/chendongde310
 * 日期：2017/4/25 - 1:26
 * 语音登陆
 */
public class LoginActivity extends AppCompatActivity implements RecognitionListener {

    private SpeechRecognizer speechRecognizer;
    private android.widget.TextView text;
    private android.widget.Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.login = (Button) findViewById(R.id.login);
        this.text = (TextView) findViewById(R.id.text);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this, new ComponentName(this, VoiceRecognitionService.class));
        speechRecognizer.setRecognitionListener(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speechRecognizer.startListening(new Intent());
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
        Logger.d("识别出错" + error);
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> nbest = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        Logger.d("识别成功：" + Arrays.toString(nbest.toArray(new String[nbest.size()])));
        String json_res = results.getString("origin_result");

        try {
            VoiceResults voiceResults = JSON.parseObject(json_res,VoiceResults.class);
            Verification(voiceResults.toContentString());
            text.setText(voiceResults.toContentString().get(0));
        } catch (Exception e) {
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

    /**
     * 验证口令是否正确
     */
    private void Verification(final List<String> strings){
        AVQuery<AVObject> query = new AVQuery<>("Constant");
        query.whereContains("Type", "口令");
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                for (String s: strings) {
                    if(s.equals(avObject.get("Content"))){
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }
                }

            }
        });

    }

}
