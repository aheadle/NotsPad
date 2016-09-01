package com.aheadle.notspad.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.aheadle.notspad.util.HintAnimEditText;
import com.tagwire.notspad.R;
import com.aheadle.notspad.adapter.SearchUserAdapter;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {
    ListAdapter adapter;
    ListView searchUserLV;
    HintAnimEditText hintAnimEditText;//搜索hint效果编辑栏
    List<CharSequence> data;

    Handler handler = new Handler();
    int mPos = 0;
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getSupportActionBar().setTitle("记事本");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<String> searchUserList = new ArrayList<String>();
        searchUserList.add("张三");
        searchUserList.add("李四");
        searchUserList.add("王五");

        searchUserLV = (ListView) findViewById(R.id.search_user_list);
        adapter = new SearchUserAdapter(this,searchUserList);
        searchUserLV.setAdapter(adapter);
        searchUserLV.setVisibility(View.VISIBLE);
        setListViewHeightBasedOnChildren(searchUserLV);

        //搜索框的动画
        data = new ArrayList<>();
        data.add("张三");
        data.add("李四");
        data.add("王五");
        data.add("赵武");
        data.add("钱六");
        hintAnimEditText = (HintAnimEditText) findViewById(R.id.edittxt);
        hintAnimEditText.setText("搜索");
        //设置初始显示
        mRunnable = new Runnable(){

            @Override
            public void run() {
                hintAnimEditText.changeHintWithAnim(data.get(mPos));
                mPos++;
                if (mPos >= data.size()) {
                    mPos = 0;
                }
                handler.postDelayed(this,3000);
            }
        };
        handler.postDelayed(mRunnable , 3000);
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        Adapter listAdapter = listView.getAdapter();
        if(listAdapter == null){
            return;
        }
        int totalHeight = 0;
        int viewCount = listAdapter.getCount();
        for (int i = 0;i < viewCount;i++){
            View listItem = listAdapter.getView(i,null,listView);
            listItem.measure(0,0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount()-1)) + 10;//加10是为了适配自定义背景

        listView.setLayoutParams(params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(mRunnable);
    }
}
