package com.knjin.notspad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tagwire.notspad.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Jing on 16/5/30.
 */
public class SearchUserAdapter extends BaseAdapter{
    private Context context;
    private List<String> userList;
    //用于记录每个RadioButton的状态，并保证只可选一个
    HashMap<String,Boolean> states = new HashMap<String, Boolean>();

    public SearchUserAdapter(Context context, List<String> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.search_user_item,null);
            viewHolder = new ViewHolder();
            viewHolder.background = (LinearLayout) convertView.findViewById(R.id.search_user_list_item);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.search_user_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final RadioButton radio = (RadioButton) convertView.findViewById(R.id.radio_btn);
        viewHolder.radioButton = radio;
        viewHolder.userName.setText(userList.get(position));
        //根据item位置分配不同的背景
        if(userList.size() > 0)
        {
            if(userList.size() == 1)
            {
                viewHolder.background.setBackgroundResource(android.R.drawable.radiobutton_on_background);
            }
            else{
                if(position == 0){
                    viewHolder.background.setBackgroundResource(android.R.drawable.radiobutton_off_background);
                }
                else if(position == userList.size()-1){
                    viewHolder.background.setBackgroundResource(android.R.drawable.radiobutton_on_background);
                }
                else{
                    viewHolder.background.setBackgroundResource(android.R.drawable.radiobutton_off_background);
                }
            }
        }
        //当RadioButton被选中时，将其状态记录进States中，并更新其他RadioButton的状态使它们不被选中
        viewHolder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重复，确保最多只有一项被选中
                for (String key:states.keySet()){
                    states.put(key,false);
                }
                states.put(String.valueOf(position),radio.isChecked());
                SearchUserAdapter.this.notifyDataSetChanged();
            }
        });
        boolean res = false;
        if(states.get(String.valueOf(position)) == null || states.get(String.valueOf(position)) == false){
            res = false;
            states.put(String.valueOf(position),false);
        }else {
            res = true;
        }
        viewHolder.radioButton.setChecked(res);
        return convertView;
    }

    static class ViewHolder{
        LinearLayout background;
        TextView userName;
        RadioButton radioButton;
    }
}
