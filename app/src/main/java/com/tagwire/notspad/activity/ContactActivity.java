package com.tagwire.notspad.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.tagwire.notspad.R;
import com.tagwire.notspad.adapter.SearchUserAdapter;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {
    ListAdapter adapter;
    ListView searchUserLV;
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
}
