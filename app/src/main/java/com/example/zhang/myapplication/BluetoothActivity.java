package com.example.zhang.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.logging.Handler;

public class BluetoothActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {

    private BluetoothAdapter adapter;
    private ListView lvDevices;
    private List<String> bluetoothDevices = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapter;
    private final UUID MY_UUID = UUID.fromString("db764ac8-4b08-7f25-aafe-59d03c27bae3");
    private final String NAME = "Bluetooth_Socket";
    private BluetoothSocket clientSocket;
    private BluetoothDevice device;
    private OutputStream os;
    private AcceptThread acceptThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        adapter = BluetoothAdapter.getDefaultAdapter();
        lvDevices = (ListView) findViewById(R.id.lvDevices);

        findViewById(R.id.btnOpenBluetooth1).setOnClickListener(this);
        findViewById(R.id.btnOpenBluetooth2).setOnClickListener(this);
        findViewById(R.id.btnShutBluetooth).setOnClickListener(this);
        findViewById(R.id.btnSearchDevices).setOnClickListener(this);
        try {
            acceptThread = new AcceptThread();
            acceptThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnOpenBluetooth1:
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent,1);
                break;
            case R.id.btnOpenBluetooth2:
                adapter.enable();
                break;
            case R.id.btnShutBluetooth:
                adapter.disable();
                break;
            case R.id.btnSearchDevices:
                if (adapter.isDiscovering()) {
                    adapter.cancelDiscovery();
                }
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                this.registerReceiver(receiver,filter);
                filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
                this.registerReceiver(receiver,filter);

                Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
                if (pairedDevices.size() > 0)
                {
                    for (BluetoothDevice device: pairedDevices)
                    {
                        bluetoothDevices.add(device.getName() + ":" + device.getAddress() + "\n");
                    }
                }


                adapter.startDiscovery();
                setTitle("开始扫描");
                break;
        }
    }
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED)
                {
                    bluetoothDevices.add(device.getName() + ":" + device.getAddress() + "\n");
                }
            }
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                setTitle("已搜索完成");
                arrayAdapter = new ArrayAdapter<String>(BluetoothActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1,
                        bluetoothDevices);
                lvDevices.setAdapter(arrayAdapter);
                lvDevices.setOnItemClickListener(BluetoothActivity.this);
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String s = arrayAdapter.getItem(position);
        String address = s.substring(s.indexOf(":") + 1).trim();
        try
        {
            if (adapter.isDiscovering())
            {
                adapter.cancelDiscovery();
            }
            try {
                if (device == null)
                {
                    device = adapter.getRemoteDevice(address);
                }
                if (clientSocket == null)
                {
                    clientSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
                    clientSocket.connect();
                    os = clientSocket.getOutputStream();
                }
            }
            catch (Exception e){
                Toast.makeText(BluetoothActivity.this,"ERROR",Toast.LENGTH_SHORT);
            }
            if (os != null)
            {
                os.write("我正在发送信息哦 ！！哈哈哈".getBytes("utf-8"));
            }
        }
        catch (Exception e){
            Toast.makeText(BluetoothActivity.this,"ERROR",Toast.LENGTH_SHORT);

        }
    }
    private class AcceptThread extends Thread{
        private BluetoothServerSocket serverSocket;
        private BluetoothSocket socket;
        private InputStream is;
        private OutputStream os;
        public AcceptThread() throws IOException {
            serverSocket = adapter.listenUsingInsecureRfcommWithServiceRecord(NAME,MY_UUID);
        }
        public void run()
        {
            try{
                socket = serverSocket.accept();
                is = socket.getInputStream();
                os = socket.getOutputStream();

                while(true)
                {
                    byte[] buffer = new byte[128];
                    int count = is.read(buffer);
                    Message msg = new Message();
                    msg.obj = new String(buffer, 0, count, "utf-8");
                    Toast.makeText(BluetoothActivity.this, String.valueOf(msg.obj), Toast.LENGTH_SHORT);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
