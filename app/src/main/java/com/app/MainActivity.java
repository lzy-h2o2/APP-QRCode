package com.app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zndroid.CallBack;
import com.zndroid.QRCodeAPI;

import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.utils.QRUtils;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tv_show);
        imageView = findViewById(R.id.im_show);
    }

    public void btn_scan(View view) {
        QRCodeAPI api = new QRCodeAPI();
        api.scanQRCode(this, new CallBack() {
            @Override
            public void onSuccess(final String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("扫描结果" + result);
                    }
                });
            }

            @Override
            public void onFailed(String reason) {
                Toast.makeText(MainActivity.this, reason, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void btn_gen_QR(View view) {
        imageView.setImageBitmap(new QRCodeAPI().getDefault().createQRCode("hello QR"));
        textView.setText("hello QR");
    }

    public void btn_gen_bar(View view) {
        imageView.setImageBitmap(new QRCodeAPI().createBarCodeWithText(this, "1234567890", 600,300));
        textView.setText("1234567890");
    }

    public void btn_custom_scan(View view) {
        QRCodeAPI api = new QRCodeAPI();
        QrConfig config = new QrConfig.Builder()
                .setTitleText("自定义扫一扫")
                .setTitleBackgroudColor(Color.RED)
                .setTitleTextColor(Color.WHITE)
                .create();
        api.getDefault().setConfig(config);

        api.scanQRCode(this, new CallBack() {
            @Override
            public void onSuccess(final String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("扫描结果" + result);
                    }
                });
            }

            @Override
            public void onFailed(String reason) {
                Toast.makeText(MainActivity.this, reason, Toast.LENGTH_LONG).show();
            }
        });
    }
}
