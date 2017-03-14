package com.knjin.notspad.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.tagwire.notspad.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class EnlargeActivity extends AppCompatActivity {
    private GridView gridView;
    private MyAdapter adapter = null;
    private List<Integer> lists = new ArrayList<Integer>();

    public static void startThisActivity(Activity activity) {
        activity.startActivity(new Intent(activity, EnlargeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarge);
        for (int i = 0; i < 20; i++) {
            lists.add(R.drawable.app_notes);
        }
        this.gridView = (GridView) findViewById(R.id.gv_enlarge);
        adapter = new MyAdapter(this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyAdapter ad = (MyAdapter) parent.getAdapter();
                ad.setNotifyDataChange(position);
            }
        });
    }

    private class MyAdapter extends BaseAdapter {
        private int selectPic = -1;
        LayoutInflater inflater;
        Context context;

        public MyAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public void setNotifyDataChange(int id) {
            selectPic = id;
            super.notifyDataSetChanged();
        }

        @SuppressLint("NewApi")
        @Override
        public View getView(int position, View view, ViewGroup arg2) {
            view = inflater.inflate(R.layout.item_gridview, null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_item_gridview);
//            ImageView img = new ImageView(EnlargeActivity.this);
            iv.setBackgroundResource(lists.get(position));
            if (selectPic == position) {
                ScaleAnimation animation = new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setFillAfter(true);
                animation.setDuration(200);
                iv.startAnimation(animation);

            } else {
                // the rest
                iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }

            return view;
        }
    }

}
