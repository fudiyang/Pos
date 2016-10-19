package com.ycxt.pos.socket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ycxt.pos.R;
import com.ycxt.pos.contans.Contans;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URLDecoder;

/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class Socket_Android extends Activity {

    private TextView mTextView = null;
    private EditText mEditText = null;
    private TextView tx1 = null;

    private Button mbutton = null;
    private Handler handler;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket);
        handler=new Handler();
        mbutton = ( Button ) findViewById(R.id.Button01);
        mEditText = ( EditText ) findViewById( R.id.et_socket);
        mTextView = ( TextView ) findViewById ( R.id.tv_socket);
        tx1 = ( TextView ) findViewById ( R.id.tx);

        mbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SocketConnect(Contans.pos_select);
                 }
            });
    }
    public void SocketConnect(String baowen) {
        new Thread() {
            @Override
            public void run() {
                Log.e("tcp", "腹地杨");
                Socket socket = null;

                try {
                    socket = new Socket();
                    Log.e("tcp", "---");
                    socket.connect(new InetSocketAddress("192.168.1.106", Integer.valueOf(9092)), 10000);
                    InputStream in = socket.getInputStream();
                    OutputStream out = socket.getOutputStream();
                    out.write(new String(Contans.pos_login).toString().getBytes());
                    byte[] a = new byte[1024];
                    in.read(a);
                    String receive = new String(a);
                    String receive1 = URLDecoder.decode(receive, "UTF-8");
                    Log.e("tcp", receive1);
                    // 关闭socket
                    in.close();
                    out.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();
    }
}