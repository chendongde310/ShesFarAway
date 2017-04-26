package com.chendong.app.shesfaraway.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVPush;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SendCallback;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chendong.app.shesfaraway.R;
import com.chendong.app.shesfaraway.bean.EventMode;
import com.chendong.app.shesfaraway.swipe.MyQuickAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.chad.library.adapter.base.BaseQuickAdapter.SCALEIN;

public class HomeActivity extends AppCompatActivity {
    RecyclerView mListView;
    List<EventMode> datas = new ArrayList<>();
    LinearLayout mPutWin;//推送事件窗口
    EditText mTitle;
    EditText mContent;
    ImageView mPut;//发布
    ImageView mBack;//回退
    FloatingActionButton fab;
    MyQuickAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mPutWin = (LinearLayout) findViewById(R.id.put_win);
        mTitle = (EditText) findViewById(R.id.title);
        mContent = (EditText) findViewById(R.id.content);
        mPut = (ImageView) findViewById(R.id.put);
        mBack = (ImageView) findViewById(R.id.back);
        mListView = (RecyclerView) findViewById(R.id.list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPutWin.setVisibility(View.VISIBLE);
                mPutWin.setAnimation(AnimationUtils.makeInAnimation(HomeActivity.this, true));
                fab.setVisibility(View.GONE);
            }
        });
        mListView.setLayoutManager(new LinearLayoutManager(this));

        getDataList();
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPutWin.setVisibility(View.GONE);
                mPutWin.setAnimation(AnimationUtils.makeOutAnimation(HomeActivity.this, true));
                fab.setVisibility(View.VISIBLE);
                fab.setAnimation(AnimationUtils.makeInChildBottomAnimation(HomeActivity.this));
            }
        });
        mPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putEvent();
            }
        });
    }

    private void getDataList() {
        datas.clear();
        AVQuery<AVObject> query = new AVQuery<>("Event");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    for (AVObject data : list) {
                        datas.add(new EventMode(data));
                    }
                    Collections.reverse(datas);
                    adapter = new MyQuickAdapter(R.layout.view_event_list, datas);
                    adapter.openLoadAnimation(SCALEIN);
                    mListView.setAdapter(adapter);
                    adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Toast.makeText(HomeActivity.this, "都说是测试版咯，这里功能还没做~", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    /**
     * 发布任务
     */
    private void putEvent() {
        EventMode mode = new EventMode();
        mode.setTitle(mTitle.getText().toString());
        mode.setContent(mContent.getText().toString());
        mode.setTime(new Date());
        mode.setColor(getRandColorCode());
        AVPush push = new AVPush();
        JSONObject object = new JSONObject();
        object.put("alert", "收到新任务");
        object.put("content", mode);
        push.setPushToAndroid(true);
        push.setData(object);
        push.sendInBackground(new SendCallback() {
            @Override
            public void done(AVException e) {
                String str = "";
                if (e != null) {
                    str = "推送失败，请重试";
                    Logger.d(e.toString());
                } else {
                    createEvent();
                    str = "推送任务 至 二狗（成功）";
                }
                Snackbar.make(fab, str, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    /**
     * 创建任务
     */
    private void createEvent() {
        AVObject avObject = new AVObject("Event");
        avObject.put("title", mTitle.getText().toString());
        avObject.put("content", mContent.getText().toString());
        avObject.put("time", new Date());
        avObject.put("color", getRandColorCode());
        avObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    mPutWin.setVisibility(View.GONE);
                    mPutWin.setAnimation(AnimationUtils.makeOutAnimation(HomeActivity.this, true));
                    fab.setVisibility(View.VISIBLE);
                    fab.setAnimation(AnimationUtils.makeInChildBottomAnimation(HomeActivity.this));
                    getDataList();
                }
            }
        });
    }





    /**
     * 获取十六进制的颜色代码.例如  "#6E36B4" , For HTML ,
     * @return String
     */
    public String getRandColorCode(){
        String r,g,b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length()==1 ? "0" + r : r ;
        g = g.length()==1 ? "0" + g : g ;
        b = b.length()==1 ? "0" + b : b ;

        return "#"+r+g+b;
    }

}
