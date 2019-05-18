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
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    TextView message;
    Button login;
    Button register;
    EditText account;
    EditText password;
    @Override
    protected void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);
        setContentView(R.layout.activity_login);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        message=(TextView)findViewById(R.id.message);
        login=(Button)findViewById(R.id.login);
        register=(Button)findViewById(R.id.register);
        account=(EditText)findViewById(R.id.account);
        password=(EditText)findViewById(R.id.password);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
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
            case R.id.login:
                this.loginTo();
                break;
            case R.id.register:
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
    final Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what==1){
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("userAccount",account.getText().toString());
                startActivity(intent);
            }
            else if(msg.what==2){
                message.setText("登陆错误，请重新输入账号密码");
                account.setText("");
                password.setText("");
            }
        }
    };
    public void loginTo(){
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
                    url=new URL("http://192.168.43.54:8080/login");
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

                        if(responseStr.equals("loginS")){
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
