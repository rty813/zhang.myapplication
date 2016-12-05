package com.example.zhang.myapplication;

import android.os.AsyncTask;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class AsyncTaskActivity extends AppCompatActivity {
    private TextView text;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        text = (TextView) findViewById(R.id.tvAsyncTask);
        text.setMovementMethod(ScrollingMovementMethod.getInstance());
        editText = (EditText) findViewById(R.id.editText);
        findViewById(R.id.btnRead).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReadURL( editText.getText().toString());
            }
        });
    }
    public void ReadURL(String url) {
        new AsyncTask<String, Float,String>() {
            @Override
            protected String doInBackground(String... arg0) {
                try {
                    String urlString = arg0[0];

                    URL url = new URL("http://202.117.255.187:8080/opac/openlink.php");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream(),"utf-8");
                    BufferedWriter bw = new BufferedWriter(osw);
                    bw.write("strSearchType=title&strText=" + urlString);
                    bw.flush();

                    long total = connection.getContentLength();
                    InputStream iStream = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(iStream);
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    StringBuilder builder = new StringBuilder();
                    while ((line = br.readLine()) != null){
                        builder.append(line);
                        publishProgress((float)builder.toString().length()/total);
                    }
                    br.close();
                    isr.close();     //????????????????????
                    iStream.close();
                    return builder.toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                text.setText(result);
                super.onPostExecute(result);
            }

            @Override
            protected void onProgressUpdate(Float... values) {
                System.err.println(values[0]);
                super.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled(String s) {
                super.onCancelled(s);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }

            @Override
            protected void onPreExecute() {
                Toast.makeText(AsyncTaskActivity.this,"开始读取",Toast.LENGTH_LONG).show();
                super.onPreExecute();
            }
        }.execute(url);
    }
}
