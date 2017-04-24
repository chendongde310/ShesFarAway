package com.chendong.app.shesfaraway.swipe;

import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;

/**
 *
 * 作者：陈东
 * Github.com/chendongde310
 * 日期：2017/4/25 - 2:24
 *
 */

public abstract class SwipeVoiceListener implements RecognitionListener {
    SpeechRecognizer speechRecognizer;
    public SwipeVoiceListener(SpeechRecognizer speechRecognizer) {
        this.speechRecognizer= speechRecognizer;
    }


}
