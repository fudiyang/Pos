package com.ycxt.pos.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ycxt.pos.MainActivity;
import com.ycxt.pos.R;

import java.lang.reflect.Field;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView Company_name,netpoint,citycode,TerminalNo,tcp_ip,port,
            password,phone,tv_sureset,tishicontent,sureset;
    private EditText edit_input_content;
    private  String net_point,city_code,terminal_no,ipdizi,duankou,mima,dianhua,gongsiname,
             net_point1,city_code1,terminal_no1,ipdizi1,mima1,duankou1,dianhua1,gongsiname1;
    private Spinner spinner;
    public  static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_setting);
        initview();
    }
    private void initview(){
        Company_name = (TextView) findViewById(R.id.Company_name);
        netpoint = (TextView) findViewById(R.id.netpoint);
        citycode = (TextView) findViewById(R.id.citycode);
        TerminalNo = (TextView) findViewById(R.id.TerminalNo);
        tcp_ip = (TextView) findViewById(R.id.tcp_ip);
        port = (TextView) findViewById(R.id.port);
        password = (TextView) findViewById(R.id.password);
        phone = (TextView) findViewById(R.id.phone);
        tv_sureset = (TextView) findViewById(R.id.tv_sureset);
        Company_name.setOnClickListener(this);
        netpoint.setOnClickListener(this);
        citycode.setOnClickListener(this);
        TerminalNo.setOnClickListener(this);
        tcp_ip.setOnClickListener(this);
        port.setOnClickListener(this);
        password.setOnClickListener(this);
        phone.setOnClickListener(this);
        tv_sureset.setOnClickListener(this);
        //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
         sharedPreferences= getSharedPreferences("test",
                Activity.MODE_PRIVATE);
     // 使用getString方法获得value，注意第2个参数是value的默认值
         net_point1 =sharedPreferences.getString("net_point", "");
         city_code1 =sharedPreferences.getString("city_code", "");
         terminal_no1 =sharedPreferences.getString("terminal_no", "");
         ipdizi1 =sharedPreferences.getString("ipdizi", "");
         mima1 =sharedPreferences.getString("mima", "");
         duankou1 =sharedPreferences.getString("duankou", "");
         dianhua1 =sharedPreferences.getString("dianhua", "");
         gongsiname1 =sharedPreferences.getString("gongsiname", "");
        SharedPreferences setting = getSharedPreferences("first", 0);
        Boolean user_first = setting.getBoolean("FIRST",true);
        if(user_first){//第一次
            setting.edit().putBoolean("FIRST", false).commit();

        }else{
            Company_name.setText(gongsiname1);
            netpoint.setText(net_point1);
            citycode.setText(city_code1);
            TerminalNo.setText(terminal_no1);
            tcp_ip.setText(ipdizi1);
            port.setText(duankou1);
            password.setText(mima1);
            phone.setText(dianhua1);
        }

    }

    private void savedata(String str,String str1 ){
        //实例化SharedPreferences对象（第一步）
         sharedPreferences= getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //用putString的方法保存数据
        editor.putString(str, str1);
   //提交当前数据
        editor.commit();
    }

    @Override
    public void onClick(View view) {
         switch (view.getId()){
             case R.id.netpoint:
                 SetingDialog("请设置区域网点",netpoint,"net_point");
                    break;
             case R.id.citycode:
                 CityCodeDialog("请设置城市码");
                 break;
             case R.id.TerminalNo:
                 SetingDialog("请设置终端号",TerminalNo,"terminal_no");
                break;
             case R.id.tcp_ip:
                 SetingDialog("请设置网络地址",tcp_ip,"ipdizi");
                break;
             case R.id.port:
                 SetingDialog("请设置网络端口",port,"duankou");
                 break;
             case R.id.password:
                 SetingDialog("请输入原密码",password,"mima");
                break;
             case R.id.phone:
                 SetingDialog("请设置电话号码",phone,"dianhua");
                break;
             case R.id.tv_sureset:
                 Sure_SetDialog("确定配置信息");
                 break;
         }
    }
    private void SetingDialog(String title, final TextView view , final String string1) {
        //动态加载布局生成View对象
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View longinDialogView = layoutInflater.inflate(R.layout.dialog_seting, null);

        //获取布局中的控件
        edit_input_content = (EditText) longinDialogView.findViewById(R.id.edit_input_content);
        //创建一个AlertDialog对话框
        AlertDialog longinDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(longinDialogView)
                .setCancelable(false)
                //加载自定义的对话框式样
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       String content=edit_input_content.getText().toString();
                        Log.e("msg",content);
                        savedata(string1,content);
                        if(content.isEmpty()&&content.equals("")){
                            Toast.makeText(SettingActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                            try {
                                Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                field.set(dialogInterface, false);//true表示要关闭,false不关闭
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else {
                             view.setText(content);
                             view.setTextColor(Color.RED);
                             dialogInterface.dismiss();
                         }
                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
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

    private void Sure_SetDialog(String title) {
        //动态加载布局生成View对象
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View longinDialogView = layoutInflater.inflate(R.layout.dialog_sureseting, null);

        //获取布局中的控件
        sureset = (TextView) longinDialogView.findViewById(R.id.sureset);
        tishicontent = (TextView) longinDialogView.findViewById(R.id.tishicontent);
        tishicontent.setText("确定保存修改信息吗");
        tishicontent.setTextColor(Color.RED);

        //创建一个AlertDialog对话框
        AlertDialog longinDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(longinDialogView)
                .setCancelable(false)
                //加载自定义的对话框式样
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       Toast.makeText(SettingActivity.this,"设置成功",Toast.LENGTH_SHORT).show();

                       finish();
                            try {
                                Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                field.set(dialogInterface, true);//true表示要关闭,false不关闭
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
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

    private void CityCodeDialog(String title) {
        //动态加载布局生成View对象
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View longinDialogView = layoutInflater.inflate(R.layout.dialog_citycode, null);

        //获取布局中的控件
        spinner = (Spinner) longinDialogView.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] languages = getResources().getStringArray(R.array.company);
                String[] citycodes = getResources().getStringArray(R.array.citycode);
                Toast.makeText(SettingActivity.this, "你点击的是:"+languages[pos], Toast.LENGTH_SHORT).show();
                Company_name.setText(languages[pos]);
                Company_name.setTextColor(Color.RED);
                citycode.setText(citycodes[pos]);
                citycode.setTextColor(Color.RED);
                city_code=citycodes[pos];
                gongsiname=languages[pos];
                savedata("city_code",city_code);
                savedata("gongsiname",gongsiname);
                Log.e("msg",city_code);
                Log.e("msg",gongsiname);
              }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        //创建一个AlertDialog对话框
        AlertDialog longinDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(longinDialogView)
                .setCancelable(false)
                //加载自定义的对话框式样
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                                Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                field.set(dialogInterface, true);//true表示要关闭,false不关闭
                                dialogInterface.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
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
