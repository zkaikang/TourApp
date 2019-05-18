package com.example.a11369.tourapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);
        setContentView(R.layout.activity_post);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        TextView title=(TextView)findViewById(R.id.title);
        TextView author=(TextView)findViewById(R.id.author);
        TextView text=(TextView)findViewById(R.id.text);
        TextView address=(TextView)findViewById(R.id.address);
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        title.setText((String)bundle.get("title"));
        author.setText((String)bundle.get("author"));
        text.setText((String)bundle.get("text"));
        address.setText((String)bundle.get("address"));
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


}
