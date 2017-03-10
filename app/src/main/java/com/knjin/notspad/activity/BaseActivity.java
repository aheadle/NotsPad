package com.knjin.notspad.activity;

import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.tagwire.notspad.R;

public class BaseActivity extends AppCompatActivity {
    private LinearLayout rootLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        //经过测试代码直接声明透明状态栏更有效
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|localLayoutParams.flags);
        }
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar!=null){
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setContentView(View.inflate(this,layoutResID,null));
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        if(rootLayout==null){
            rootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.MATCH_PARENT));
        }
        initToolbar();
    }
}
