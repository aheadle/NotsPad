package com.tagwire.notspad;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.tagwire.notspad.NotePadDao;
import com.tagwire.notspad.NotepadAddActivity;
import com.tagwire.notspad.NotepadDetailActivity;
import com.tagwire.notspad.R;
import com.tagwire.notspad.model.Weather;
import com.tagwire.notspad.util.GetJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private NotePadDao dao;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;
    private Spinner mSpinner;
    public static final String JSON_DATA = "...";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new NotePadDao(this);
        adapter = new SimpleCursorAdapter(this, R.layout.list_item_2, cursor, new String[]{"_content", "_created"},
                new int[]{R.id.textView1, R.id.textView2}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
//      registerForContextMenu(getListView());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,NotepadDetailActivity.class);
                intent.putExtra("idKey",(int)id);
                startActivity(intent);
            }
        });
        /**
         * 自定义spinner
         */
        mSpinner = (Spinner) findViewById(R.id.spinner);
        //数据源
        String[] mItems = getResources().getStringArray(R.array.spinnername);
        //绑定数据源
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner,mItems);
        //绑定
        mSpinner.setAdapter(spinnerAdapter);

        //想要测试一下gson
        getJsonMsg();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reqeury();
    }

    private void reqeury() {
        Log.d("TAG","onResume");
        cursor = dao.query();
        adapter.changeCursor(cursor);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cursor!=null){
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.noteAddId){
            Intent intent = new Intent(this,NotepadAddActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.notepad_content,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo acm = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.noteDeleteId:
                dao.delete(String.valueOf(acm.id));
                reqeury();
                break;

            case R.id.noteDetailId:
                Intent intent = new Intent(this,NotepadDetailActivity.class);
                intent.putExtra("idKey",(int)acm.id);
                startActivity(intent);
                break;
        }
        return super.onContextItemSelected(item);
    }


    public void getJsonMsg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String weaJson = GetJson.request(Const.httpUrl,Const.httpArg);
                try {
                    JSONObject obj = new JSONObject(weaJson);
                    JSONArray weatherObj = obj.getJSONArray("HeWeather data service 3.0");
                    Weather weather = new Gson().fromJson(weatherObj.toString(), Weather.class);
                    String tell = weather.getAqi().getCity().toString();
                    Toast.makeText(MainActivity.this,tell,Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }
}
