package com.knjin.notspad.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.tagwire.notspad.R;

/**
 * Created by Jing on 17/3/13.
 */

public class BaiduSampleActivity extends Activity implements SpeechSynthesizerListener{
    SpeechSynthesizer mSpeechSynthesizer;

    public static void startThisActivity(Activity activity){
        activity.startActivity(new Intent(activity, BaiduSampleActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //获取TTS 实例
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        //设置APP上下文
        mSpeechSynthesizer.setContext(this);
        //设置tts监视器
        mSpeechSynthesizer.setSpeechSynthesizerListener(this);

    }

    @Override
    public void onSynthesizeStart(String s) {

    }

    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {

    }

    @Override
    public void onSynthesizeFinish(String s) {

    }

    @Override
    public void onSpeechStart(String s) {

    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {

    }

    @Override
    public void onSpeechFinish(String s) {

    }

    @Override
    public void onError(String s, SpeechError speechError) {

    }
}
