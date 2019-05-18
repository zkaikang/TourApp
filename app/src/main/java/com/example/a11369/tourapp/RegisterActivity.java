package com.example.a11369.tourapp;

import android.app.DownloadManager;
import android.content.Intent;
import android.icu.text.RelativeDateTimeFormatter;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.textclassifier.TextClassification;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    TextView message;
    TextView backLogin;
    @Override
    protected void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);
        setContentView(R.layout.activity_register);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        backLogin=(TextView)findViewById(R.id.backLogin);
        backLogin.setOnClickListener(this);
        message=(TextView)findViewById(R.id.message);
        Button register=(Button)findViewById(R.id.register);
        register.setOnClickListener(this);

    }
    @Override
    public void onClick(View view){
        int id=view.getId();
        switch(id){
            case R.id.register:
                this.registerUser();
                break;
            case R.id.backLogin:
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
        }
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
    public void createMessage(boolean trueOrNot){
        TextView message=(TextView)findViewById(R.id.message);
        if(trueOrNot){
            message.setText("注册成功");
        }
        else{
            message.setText("注册失败");
        }
    }
    final Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what==1){
                message.setText("注册成功！");
            }
            else if(msg.what==2){
                message.setText("注册失败");
            }
        }
    };
    public void registerUser(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                    HttpURLConnection connection=null;
                    URL url=null;
                    EditText account=(EditText) findViewById(R.id.account);
                    EditText password=(EditText) findViewById(R.id.password);
                    String name=account.getText().toString();
                    String pass=password.getText().toString();
                    System.out.println(name+pass);
                    System.out.println(1);
                    try{
                        //url=new URL("http://10.132.0.234:8080/register");
                        url=new URL("http://192.168.43.54:8080/register");
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
                        json.put("userAccount",name);
                        json.put("userPassword",pass);
                        String jsonstr=json.toString();
                        OutputStream out=connection.getOutputStream();
                        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(out));
                        bw.write(jsonstr);
                        bw.flush();
                        System.out.println(3);
                        out.close();
                        bw.close();
                        if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
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

                            if(responseStr.equals("registerS")){
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
                        System.out.println(e.getMessage());
                    }finally {
                        connection.disconnect();
                    }

                }
            }){

            }.start();
    }

}
