package com.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.zndroid.CallBack;
import com.zndroid.QRCodeAPI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QRCodeAPI api = new QRCodeAPI();
        api.getDefault().scanQRCode(this, new CallBack() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailed(String reason) {
                Toast.makeText(MainActivity.this, reason, Toast.LENGTH_LONG).show();
            }
        });
    }
}
