package com.example.zhang.myapplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by zhang on 2016/11/29.
 */

public class ChatSocket extends Thread {
    private Socket socket;
    public ChatSocket(Socket S){
        this.socket = S;
    }

    @Override
    public void run() {
        int count = 0;
        System.out.println("开始运行ChatSocket线程");
        try {
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            while (count <= 10) {
                System.out.println("hello " + count + "\n");
                bw.write("hello " + count + "\n");
                bw.flush();
                count++;
                sleep(500);
            }
            bw.close();
            osw.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
