package com.example.zhang.myapplication;

import android.app.Service;
import android.content.Intent;
import android.net.SSLCertificateSocketFactory;
import android.os.IBinder;
import android.os.Looper;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketService extends Service {
    public SocketService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private ServerSocket serverSocket;
    private Socket service;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(SocketService.this,"开始监听12345端口",Toast.LENGTH_LONG).show();
//        new ServerListener().start();
        new Thread()
        {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(12345);
                    while (true) {
                        service = serverSocket.accept();
                        Looper.prepare();
                        Toast.makeText(SocketService.this,"有客户端连接到了本机的12345端口",Toast.LENGTH_LONG).show();
                        new ChatSocket(service).start();
                        System.out.println("已经新建了ChatSocket线程");
                        Looper.loop();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        try {
            service.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
