package com.example.a11369.tourapp;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Information extends AppCompatActivity implements View.OnClickListener{
    public Intent intent;
    public String userAccount;
    Intent getIntent;

    @Override
    protected void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);
        setContentView(R.layout.activity_infomation);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getIntent=this.getIntent();
        userAccount=getIntent.getStringExtra("userAccount");
        TextView mypost=(TextView)findViewById(R.id.myPost);
        TextView myfoot=(TextView)findViewById(R.id.myFoot);
        TextView changeAccount=(TextView)findViewById(R.id.changeAccount);
        TextView changePassword=(TextView)findViewById(R.id.changePassword);
        Button setting=(Button) findViewById(R.id.setting);
        TextView exitAccount=(TextView)findViewById(R.id.exitAccount);
        mypost.setOnClickListener(this);
        myfoot.setOnClickListener(this);
        changeAccount.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        setting.setOnClickListener(this);
        exitAccount.setOnClickListener(this);
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
    @Override
    public void onClick(View view){
        int id=view.getId();
        switch(id){
            case R.id.myPost:
                intent=new Intent(Information.this,MyPost.class);
                startActivity(intent);
                break;
        }
    }
}
