package com.example.zhang.myapplication;

import android.os.AsyncTask;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        findViewById(R.id.btnHttpGet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etWord = (EditText) findViewById(R.id.etTrans);
                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String  doInBackground(String... strings) {
                        try {
                            URL url = new URL(strings[0]);
                            URLConnection connection = url.openConnection();
                            InputStream is = connection.getInputStream();
                            InputStreamReader isr = new InputStreamReader(is,"utf-8");
                            BufferedReader br = new BufferedReader(isr);
                            String line;
                            StringBuilder builder = new StringBuilder();
                            while ((line = br.readLine()) != null){
                                System.out.println(line);
                                builder.append(line);
                            }
                            br.close();
                            isr.close();
                            is.close();
                            return builder.toString();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        TextView text = (TextView) findViewById(R.id.tvHttpGet);
                        text.setMovementMethod(ScrollingMovementMethod.getInstance());
                        text.setText(s);
                        super.onPostExecute(s);
                    }
                }.execute("http://fanyi.youdao.com/openapi.do?keyfrom=testHttpGet2asdfs&key=1733045879&type=data&doctype=xml&version=1.1&q="
                        + etWord.getText().toString()+" ");
            }
        });
    }
}
