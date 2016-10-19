package com.ycxt.pos.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.landicorp.android.eptapi.DeviceService;
import com.landicorp.android.eptapi.card.InsertCpuCardDriver;
import com.landicorp.android.eptapi.exception.ReloginException;
import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.exception.ServiceOccupiedException;
import com.landicorp.android.eptapi.exception.UnsupportMultiProcess;
import com.ycxt.pos.R;
import com.ycxt.pos.contans.Contans;
import com.ycxt.pos.device.ContactICCardSample;
import com.ycxt.pos.device.card.ContactCpuCard;
import com.ycxt.pos.framework.utils.utils.IntentUtils;
import com.ycxt.pos.utils.Changliang;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URLDecoder;

public class QueryResultsActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_goudian;
    private ContactICCardSample contactICSample1;
    private boolean isDeviceServiceLogined = false;
    private ContactCpuCard contactCpuCard=new ContactCpuCard(new InsertCpuCardDriver(),this) {
        @Override
        protected void showErrorMessage(String msg) {

        }

        @Override
        public void showFinalMessage(String msg) {
            QueryResultsActivity.this.onDeviceServiceCrash();
        }

        @Override
        protected void onServiceCrash() {

        }
    };
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:
                    Toast.makeText(QueryResultsActivity.this, "购电成功", Toast.LENGTH_SHORT).show();
                    Changliang.a=2;
                    contactICSample1.searchCpuCard();
                    contactCpuCard.OperationCard();

                    break;
                case 2:
                    Toast.makeText(QueryResultsActivity.this, "购电失败", Toast.LENGTH_SHORT).show();
                    GouDianDialog();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_query_results);
        initview();
    }
    private void initview(){
        btn_goudian= (Button) findViewById(R.id.btn_goudian);
        btn_goudian.setOnClickListener(this);

        contactICSample1 = new ContactICCardSample(this) {

            @Override
            protected void onDeviceServiceCrash() {
                // Handle in 'ContactICCardActivity'
                QueryResultsActivity.this.onDeviceServiceCrash();
            }

            @Override
            protected void displayICInfo(String cardInfo) {
                // Handle in 'ContactICCardActivity'
                QueryResultsActivity.this.displayInfo(cardInfo);
            }
        };
    }

    @Override
    public void onClick(View view) {
         switch (view.getId()){
             case R.id.btn_goudian:
                 new Thread() {
                     @Override
                     public void run() {
                         Log.e("tcp", "82购电请求");
                         Socket socket = null;
                         try {
                             socket = new Socket();
                             Log.e("tcp", "---");
                             socket.connect(new InetSocketAddress("192.168.1.105", Integer.valueOf(9093)), 10000);
                             InputStream in = socket.getInputStream();
                             OutputStream out = socket.getOutputStream();
                             out.write(new String(Contans.pos_pay).toString().getBytes());
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
                 break;
         }
    }

    private void GouDianDialog() {
        //动态加载布局生成View对象
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View longinDialogView = layoutInflater.inflate(R.layout.dialog_goudian, null);

        //获取布局中的控件


        //创建一个AlertDialog对话框
        AlertDialog longinDialog = new AlertDialog.Builder(this)
                .setTitle("购电异常！")
                .setView(longinDialogView)
                .setCancelable(false)
                //加载自定义的对话框式样
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    IntentUtils.openActivity(QueryResultsActivity.this,ContactICCardActivity.class);
                     }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Toast.makeText(QueryResultsActivity.this, "取消", Toast.LENGTH_SHORT).show();
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

    /**
     * If device service crashed, quit application or try to relogin service again.
     */
    public void onDeviceServiceCrash() {
        bindDeviceService();
    }

    /**
     * To gain control of the device service,
     * you need invoke this method before any device operation.
     */
    public void bindDeviceService() {
        try {
            isDeviceServiceLogined = false;
            DeviceService.login(this);
            isDeviceServiceLogined = true;
        } catch (RequestException e) {
            // Rebind after a few milliseconds,
            // If you want this application keep the right of the device service
//			runOnUiThreadDelayed(new Runnable() {
//				@Override
//				public void run() {
//					bindDeviceService();
//				}
//			}, 300);
            e.printStackTrace();
        } catch (ServiceOccupiedException e) {
            e.printStackTrace();
        } catch (ReloginException e) {
            e.printStackTrace();
        } catch (UnsupportMultiProcess e) {
            e.printStackTrace();
        }
    }

    /**
     * All device operation result infomation will be displayed by this method.
     * @param info
     */
    public void displayInfo(String info) {
        EditText infoEditText = (EditText) findViewById(R.id.info_text);
        String text = infoEditText.getText().toString();
        if(text.isEmpty()) {
            infoEditText.setText(info);
        }
        else {
            infoEditText.setText(text + "\n" + info);
        }
        infoEditText.setSelection(infoEditText.length());
    }
}
