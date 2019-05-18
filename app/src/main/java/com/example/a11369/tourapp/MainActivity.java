package com.example.a11369.tourapp;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTextMessage;
    Intent intent;
    Intent getIntent;
    String userAccount;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:
                    intent=new Intent(MainActivity.this,CreatePost.class);
                    intent.putExtra("userAccount",userAccount);
                    startActivity(intent);
                    return true;
                case R.id.navigation_notifications:
                    intent=new Intent(MainActivity.this,Information.class);
                    intent.putExtra("userAccount",userAccount);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };
    @Override
    public void onClick(View view){
        int id=view.getId();
        switch(id){
            case R.id.itemlist:
                Intent intent=new Intent(MainActivity.this,CreatePost.class);
                startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getIntent=this.getIntent();
        userAccount=getIntent.getStringExtra("userAccount");

        Bundle bundle=getIntent.getExtras();
        String title1=(String)bundle.get("title");
        String content1=(String)bundle.get("content");
        String text="愉快的一天从吃热干面开始，在早市中肆意地购买各种各样的没事，尽情体会武汉早市的热闹。";
        ItemBean item=new ItemBean(title1,"飞翔的猪",content1,"武汉");
        final List<ItemBean> itemList=this.createItem();
        itemList.add(item) ;
        final ItemAdapter myAdapter=new ItemAdapter(this,this.createItem());
        final ListView listView=(ListView) findViewById(R.id.itemlist);
        listView.setAdapter(myAdapter);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,String> map=myAdapter.getItem(position);
                Intent intent=new Intent(MainActivity.this,PostActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("title",map.get("title"));
                bundle.putString("address",map.get("address"));
                bundle.putString("text",map.get("text"));
                bundle.putString("author",map.get("author"));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    public List<ItemBean> createItem(){
        String text="愉快的一天从吃热干面开始，在早市中肆意地购买各种各样的没事，尽情体会武汉早市的热闹。";
        ItemBean item=new ItemBean("武汉一日游","飞翔的猪",text,"武汉");
        String text2="愉快的一天从吃热干面开始，在早市中肆意地购买各种各样的没事，尽情体会武汉早市的热闹。";
        ItemBean item2=new ItemBean("长沙一日游","飞翔的猪",text,"长沙");
        List<ItemBean> itemList=new ArrayList<>();
        itemList.add(item);
        itemList.add(item2);
        return itemList;
    }

}
