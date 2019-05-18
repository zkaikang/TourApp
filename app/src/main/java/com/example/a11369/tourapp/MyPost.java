package com.example.a11369.tourapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyPost extends AppCompatActivity {
    protected void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);
        setContentView(R.layout.activity_mypost);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final List<ItemBean> itemList=this.createItem();
        final ItemAdapter myAdapter=new ItemAdapter(this,this.createItem());
        final ListView listView=(ListView) findViewById(R.id.itemlist);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,String> map=myAdapter.getItem(position);
                Intent intent=new Intent(MyPost.this,PostActivity.class);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
