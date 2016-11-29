package com.example.zhang.myapplication;

import android.widget.Toast;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by zhang on 2016/11/29.
 */

public class ServerListener extends Thread {
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            while(true){
                Socket socket = serverSocket.accept();
                //Toast.makeText(SocketService,"有客户端连接到了本机的12345端口",Toast.LENGTH_LONG);
                new ChatSocket(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
