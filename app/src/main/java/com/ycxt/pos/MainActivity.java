package com.ycxt.pos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ycxt.pos.activity.ContactICCardActivity;
import com.ycxt.pos.activity.SettingActivity;
import com.ycxt.pos.contans.Contans;
import com.ycxt.pos.framework.utils.utils.IntentUtils;
import com.ycxt.pos.login.UserInfo;
import com.ycxt.pos.socket.Socket_Android;
import com.ycxt.pos.utils.Callback;
import com.ycxt.pos.utils.Invoker;
import com.ycxt.pos.utils.Util;
import com.ycxt.pos.view.DragLayout;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URLDecoder;

public class MainActivity extends Activity implements View.OnClickListener{

    private DragLayout dl;
    private ImageView img_touxiang, img_password;
    private TextView tv_logo, tv_setting,name_jiancheng,gongsiname,dianhua,wangluo,wangdian,miyao,movehao;
    private Socket_Android socket_android;
    private EditText et_touxiang, et_password, edit_input_pwd, edit_sure_pwd;
    private CheckBox checkBox;
    private Button btn_login;
    private String username,password,input_pwd,company_jiancheng,company_name,phone,network,netpoint,pwd,zhongduanhao;


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    IntentUtils.openActivity(MainActivity.this, ContactICCardActivity.class);
                    break;
                case 2:
                    Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Util.initImageLoader(this);
        initDragLayout();
        initView();
        init();
    }

    private void initDragLayout() {
        dl = (DragLayout) findViewById(R.id.dl);
        dl.setDragListener(new DragLayout.DragListener() {
            @Override
            public void onOpen() {

            }

            @Override
            public void onClose() {

            }

            @Override
            public void onDrag(float percent) {

            }
        });
    }

    private void initView() {
        socket_android=new Socket_Android();
        img_touxiang = (ImageView) findViewById(R.id.img_touxiang);
        img_password = (ImageView) findViewById(R.id.img_password);
        tv_logo = (TextView) findViewById(R.id.tv_logo);
        name_jiancheng = (TextView) findViewById(R.id.company_jiancheng);
        gongsiname = (TextView) findViewById(R.id.company_name);
        wangdian = (TextView) findViewById(R.id.netpoint_name);
        movehao = (TextView) findViewById(R.id.movekey_name);
        miyao = (TextView) findViewById(R.id.key_name);
        dianhua = (TextView) findViewById(R.id.phone_name);
        wangluo = (TextView) findViewById(R.id.network_name);
        tv_setting = (TextView) findViewById(R.id.tv_setting);
        et_touxiang = (EditText) findViewById(R.id.et_touxiang);
        et_password = (EditText) findViewById(R.id.et_password);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        tv_setting.setOnClickListener(this);

    }

    private void init(){
        //初始化数据
        SharedPreferences sharedPreferences=getSharedPreferences("config",0);
        //取出数据，如果取出的数据时空时，只需把getString("","")第二个参数设置成空字符串就行了，不用在判断
        String name=sharedPreferences.getString("name","");
        String password=sharedPreferences.getString("password","");
        //获取勾选的状态
        boolean checkbox=sharedPreferences.getBoolean("checkbox",false);
         et_touxiang.setText(name);
         et_password.setText(password);
         checkBox.setChecked(checkbox);
        Log.e("msg","diyibu");
        if(SettingActivity.sharedPreferences!=null){
            Log.e("msg","dierbu");
            wangdian.setText(SettingActivity.sharedPreferences.getString("net_point", ""));
            movehao.setText(SettingActivity.sharedPreferences.getString("terminal_no", ""));
            wangluo.setText(SettingActivity.sharedPreferences.getString("ipdizi", ""));
            dianhua.setText(SettingActivity.sharedPreferences.getString("dianhua", ""));
            company_name= SettingActivity.sharedPreferences.getString("gongsiname", "");
            company_jiancheng=company_name.substring(2,4)+"电力";
            gongsiname.setText(SettingActivity.sharedPreferences.getString("gongsiname", ""));
            name_jiancheng.setText(company_jiancheng);
            Log.e("msg","fffffffffffffff"+company_jiancheng);
        }

    }
    public void login() {
        //获取用户名
         username = et_touxiang.getText().toString();
         password=et_password.getText().toString();
        //文本判断是否为空，新的API:TextUtils.isEmty()
        if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
            Toast.makeText(MainActivity.this, "用户名和密码不能为空", Toast.LENGTH_LONG).show();
        } else {

            if (checkBox.isChecked()) {
                //把密码和用户名存起来
                //getSharedPreferences(name,model);,name 会生成一个xml文件，model ：模式，可读可写等模式
                SharedPreferences sp = getSharedPreferences("config", 0);
                SharedPreferences.Editor editor = sp.edit();
                //把数据进行保存
                editor.putString("name", username);
                //记住勾选的状态
                editor.putBoolean("checkbox", checkBox.isChecked());
                //提交数据
                editor.commit();
            } else {
                Toast.makeText(MainActivity.this, "未勾选", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadImage();
        onCreate(null);
    }

    private void loadImage() {
        new Invoker(new Callback() {
            @Override
            public boolean onRun() {

                return false;
            }

            @Override
            public void onBefore() {

            }

            @Override
            public void onAfter(boolean b) {

            }
        }).start();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_login:
                login();
                if (username.equals("ycxt") && password.equals("123")){
                        new Thread() {
                            @Override
                            public void run() {
                                Log.e("tcp", "88登录请求");
                                Socket socket = null;
                                try {
                                    socket = new Socket();
                                    Log.e("tcp", "---");
                                    socket.connect(new InetSocketAddress("192.168.1.105", Integer.valueOf(9093)), 10000);
                                    InputStream in = socket.getInputStream();
                                    OutputStream out = socket.getOutputStream();
                                    out.write(new String(Contans.pos_login).toString().getBytes());
                                    byte[] a = new byte[1024];
                                    in.read(a);
                                    String receive = new String(a);
                                    String receive1 = URLDecoder.decode(receive, "UTF-8");
                                    if(receive1.indexOf("|00|")!=-1){
                                        handler.sendEmptyMessage(1);
                                    }if(receive1.indexOf("|00|")==-1) {
                                        handler.sendEmptyMessage(2);
                                    }
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
                break;
            case R.id.tv_setting:
                Toast.makeText(MainActivity.this, "开始设置", Toast.LENGTH_SHORT).show();
                LoginDialog();
                break;
         }
    }

    private void LoginDialog() {
       //动态加载布局生成View对象
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View longinDialogView = layoutInflater.inflate(R.layout.dialog_setpassword, null);

        //获取布局中的控件
        edit_input_pwd = (EditText) longinDialogView.findViewById(R.id.edit_input_pwd);

        //创建一个AlertDialog对话框
        AlertDialog longinDialog = new AlertDialog.Builder(this)
                .setTitle("设置密码")
                .setView(longinDialogView)
                .setCancelable(false)
                //加载自定义的对话框式样
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        input_pwd=edit_input_pwd.getText().toString();

                        Log.e("msg","first_pwd"+input_pwd);

                        if(input_pwd.isEmpty()&&input_pwd.equals("")){
                            Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                            try {
                                Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                field.set(dialogInterface, false);//true表示要关闭,false不关闭
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if(input_pwd.equals("ycxt")){
                            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            dialogInterface.dismiss();
                            IntentUtils.openActivity(MainActivity.this, SettingActivity.class);
                        } else  if(!input_pwd.equals("ycxt")) {
                            Toast.makeText(MainActivity.this, "设置失败,请重新输入", Toast.LENGTH_SHORT).show();
                            try {
                            Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                                    field.setAccessible(true);
                                   field.set(dialogInterface, false);//true表示要关闭,false不关闭
                                     } catch (Exception e) {
                                    e.printStackTrace();
                                      }
                            }
                      }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                        try {
                            Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            field.set(dialogInterface, true);//true表示要关闭,false不关闭
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .create();

        longinDialog.show();
    }


}
