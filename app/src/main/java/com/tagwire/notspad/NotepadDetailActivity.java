package com.tagwire.notspad;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * 记事本详细记录
 */
public class NotepadDetailActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    private int id;
    private EditText contentTv;
    private NotePadDao dao;
    private String text = "";
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad_detail);
        setTextContent();
        setRadioGroup();
        setActionBar();

    }

    /**
     * 初始化数据，控件
     */
    private void setActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void setTextContent() {
        contentTv=(EditText) findViewById(R.id.contentId);
        Intent intent=getIntent();
        id=intent.getIntExtra("idKey",-1);
        dao=new NotePadDao(this);
        Cursor c=dao.queryById(id);

        if(c!=null&&c.moveToFirst()){
            text=c.getString(c.getColumnIndex("_content"));
        }
        contentTv.setText(text);
        findViewById(R.id.scrollViewId)
                .setOnTouchListener(
                        new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                //contentTv.setFocusable(true);
                                if(event.getAction()==MotionEvent.ACTION_DOWN){
                                    contentTv.setFocusableInTouchMode(true);
                                    contentTv.setFocusable(true);
                                    contentTv.setSelection(text.length());
                                }
                                return true;
                            }
                        });
    }
    /**³õÊ¼»¯RadioGroup*/
    private void setRadioGroup(){
        radioGroup=(RadioGroup) findViewById(R.id.radioGroup01);
        radioGroup.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId){
            case R.id.deleteId:
                deleteNoteItem();
                break;
            case R.id.shareId:
                break;
            default: break;
        }
        RadioButton rbtn = (RadioButton) radioGroup.findViewById(checkedId);
        rbtn.setChecked(false);
    }

    /**
     * 删除记录
     */
    private void deleteNoteItem() {
        new AlertDialog.Builder(this)
                .setTitle("提示").setMessage("是否要删除该记录")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whick) {
                        dao.delete(String.valueOf(id));
                        finish();
                    }
                }).setNegativeButton("取消",null).create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notepad_detail,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.updateNoteId){
            String content = contentTv.getText().toString();
            ContentValues values = new ContentValues();
            values.put("_content",content);
            dao.update(values,"_id=?",new String[]{String.valueOf(id)});
            finish();
        }else if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
