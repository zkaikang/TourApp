package com.example.a11369.tourapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CreatePost extends AppCompatActivity implements View.OnClickListener {
    EditText title;
    EditText content;
    Button  submit;
    String userAccount;
    Intent getIntent;
    TextView message;
    @Override
    protected void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);
        setContentView(R.layout.activity_createpost);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        userAccount="2580";
        getIntent=this.getIntent();
        userAccount=getIntent.getStringExtra("userAccount");
        title=(EditText)findViewById(R.id.address);
        content=(EditText)findViewById(R.id.edit);
        submit=(Button)findViewById(R.id.submit);
        message=(TextView)findViewById(R.id.message);
        submit.setOnClickListener(this);
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
    public void onClick(View view){
        int id=view.getId();
        switch(id){
            case R.id.submit:
                //this.crePost();
                Intent intent=new Intent(CreatePost.this,MainActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("title",title.getText().toString());
                bundle.putString("content",content.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
                break;

        }
    }
    final Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what==1){
                message.setText("发帖成功");
            }
            else if(msg.what==2){
                message.setText("发帖失败");

            }
        }
    };
    public void crePost(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                URL url=null;
                title=(EditText)findViewById(R.id.address);
                content=(EditText)findViewById(R.id.edit);
                String posTitle=title.getText().toString();
                String postCon=content.getText().toString();
                System.out.println(posTitle+postCon);
                System.out.println(1);
                try{
                    url=new URL("http://192.168.1.100:8080/post");
                    connection=(HttpURLConnection)url.openConnection();
                    System.out.println(2);
                    connection.setConnectTimeout(5000);
                    System.out.println(2);
                    connection.setDoInput(true);
                    System.out.println(3);
                    connection.setDoOutput(true);
                    System.out.println(2);
                    connection.setRequestMethod("POST");
                    System.out.println(4);
                    connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
                    System.out.println(2);
                    connection.connect();
                    System.out.println(5);
                    JSONObject json=new JSONObject();
                    json.put("title",posTitle);
                    json.put("content",postCon);
                    json.put("userAccount",userAccount);
                    String jsonstr=json.toString();
                    OutputStream out=connection.getOutputStream();
                    BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(out));
                    bw.write(jsonstr);
                    bw.flush();
                    System.out.println(3);
                    out.close();
                    bw.close();
                    if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                        System.out.print(6);
                        InputStream in=connection.getInputStream();
                        BufferedReader br=new BufferedReader(new InputStreamReader(in));
                        String str=null;
                        StringBuffer buffer=new StringBuffer();
                        while((str=br.readLine())!=null){
                            buffer.append(str);
                        }
                        in.close();
                        br.close();
                        //JSONObject rjson=new JSONObject(buffer.toString());
                        //Log.d("response: ","rjson: "+rjson);
                        //String responseStr=rjson.getString("message");
                        String responseStr=buffer.toString();
                        System.out.println(responseStr);
                        Message message=new Message();

                        if(responseStr.equals("insertS")){
                            message.what=1;
                            handler.sendMessage(message);
                        }
                        else{
                            message.what=2;
                            handler.sendMessage(message);
                        }

                    }
                }
                catch (Exception e){
                    Message message=new Message();
                    message.what=2;
                    handler.sendMessage(message);
                    System.out.println(e.getMessage());
                }finally {
                    connection.disconnect();
                }

            }
        }){

        }.start();
    }

}
