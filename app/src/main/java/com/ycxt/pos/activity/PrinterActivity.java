package com.ycxt.pos.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.ycxt.pos.R;
import com.ycxt.pos.device.comm.PrinterSample;

public class PrinterActivity extends BaseActivity {

    private PrinterSample printerSample;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);
        printerSample = new PrinterSample(this) {

            @Override
            protected void onDeviceServiceCrash() {
                // Handle in 'PrinterActivity'
                PrinterActivity.this.onDeviceServiceCrash();
            }

            @Override
            protected void displayPrinterInfo(String info) {
                // Handle in 'PrinterActivity'
                PrinterActivity.this.displayInfo(info);
            }
        };

        findViewById(R.id.start_print).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                displayInfo(" ------------ 开始打印 ------------ ");
                printerSample.startPrint();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindDeviceService();
    }

    // Sometimes you need to release the right of using device before other application 'onStart'.
    @Override
    protected void onPause() {
        super.onPause();

        unbindDeviceService();
    }



    /**
     * If device service crashed, quit application or try to relogin service again.
     */
    public void onDeviceServiceCrash() {
        bindDeviceService();
    }

    /**
     * All device operation result infomation will be displayed by this method.
     *
     * @param info
     */
    public void displayInfo(String info) {
        EditText infoEditText = (EditText) findViewById(R.id.info_text);
        String text = infoEditText.getText().toString();
        if (text.isEmpty()) {
            infoEditText.setText(info);
        } else {
            infoEditText.setText(text + "\n" + info);
        }
        infoEditText.setSelection(infoEditText.length());
    }
}
