package com.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zndroid.CallBack;
import com.zndroid.QRCodeAPI;

import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.utils.QRUtils;
import cn.bertsir.zbar.view.ScanLineView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_show);
        imageView = findViewById(R.id.im_show);
    }

    private void setText(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(msg);
            }
        });
    }

    public void btn_scan(View view) {
        setText("扫描");

        QRCodeAPI api = new QRCodeAPI();
        api.getDefault().scanQRCode(this, new CallBack() {
            @Override
            public void onSuccess(final String result) {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                setText("扫描结果：" + result);
            }

            @Override
            public void onFailed(String reason) {
                Toast.makeText(MainActivity.this, reason, Toast.LENGTH_LONG).show();
                setText("扫描结果：" + reason);
            }
        });
    }

    public void btn_gen_QR(View view) {
        String temp = "{\"content\":\"hello QR\"}";

        QRCodeAPI api = new QRCodeAPI();
        imageView.setImageBitmap(api.createQRCode(temp));
        setText(temp);
    }

    public void btn_gen_bar(View view) {
        String temp = "1234567890";

        QRCodeAPI api = new QRCodeAPI();
        imageView.setImageBitmap(api.createBarCodeWithText(this,
                temp, 600, 160));
        setText(temp);
    }

    public void btn_custom_scan(View view) {
        QRCodeAPI api = new QRCodeAPI();
        QrConfig config = new QrConfig();
        config = new QrConfig.Builder()
                .setDesText("请对准需要识别的二维码balabala")//扫描框下文字
                .setShowDes(true)//是否显示扫描框下面文字
                .setShowLight(true)//显示手电筒按钮
                .setShowTitle(true)//显示Title
                .setShowAlbum(true)//显示从相册选择按钮
                .setNeedCrop(false)//是否从相册选择后裁剪图片
                .setCornerColor(Color.parseColor("#37A0FF"))//设置扫描框颜色
                .setLineColor(Color.parseColor("#37A0FF"))//设置扫描线颜色
                .setLineSpeed(QrConfig.LINE_MEDIUM)//设置扫描线速度
                .setScanType(QrConfig.TYPE_ALL)//设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
                .setScanViewType(QrConfig.SCANVIEW_TYPE_QRCODE)//设置扫描框类型（二维码还是条形码，默认为二维码）
                .setCustombarcodeformat(QrConfig.BARCODE_PDF417)//此项只有在扫码类型为TYPE_CUSTOM时才有效
                .setPlaySound(true)//是否扫描成功后bi~的声音
//                .setDingPath()//设置提示音(不设置为默认的Ding~)
                .setIsOnlyCenter(false)//是否只识别框中内容(默认为全屏识别)
                .setTitleText("自定义扫一扫")//设置Tilte文字
                .setTitleBackgroudColor(Color.parseColor("#5d5d5d"))//设置状态栏颜色
                .setTitleTextColor(Color.RED)//设置Title文字颜色
                .setShowZoom(true)//是否开始滑块的缩放
                .setAutoZoom(false)//是否开启自动缩放(实验性功能，不建议使用)
                .setFingerZoom(true)//是否开始双指缩放
                .setDoubleEngine(true)//是否开启双引擎识别(仅对识别二维码有效，并且开启后只识别框内功能将失效)
                .setScreenOrientation(QrConfig.SCREEN_PORTRAIT)//设置屏幕方式
                .setOpenAlbumText("选择要识别的图片")//打开相册的文字
                .setLooperScan(false)//是否连续扫描二维码
                .setLooperWaitTime(5 * 1000)//连续扫描间隔时间
                .setScanLineStyle(ScanLineView.style_gridding)//扫描线样式
                .setAutoLight(false)//自动灯光
                .setShowVibrator(false)//是否震动提醒
                .create();
        api.setConfig(config);

        api.scanQRCode(this, new CallBack() {
            @Override
            public void onSuccess(final String result) {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                setText("扫描结果：" + result);
            }

            @Override
            public void onFailed(String reason) {
                Toast.makeText(MainActivity.this, reason, Toast.LENGTH_LONG).show();
                setText("扫描结果：" + reason);
            }
        });
    }
}
