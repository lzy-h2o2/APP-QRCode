package com.zndroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import cn.bertsir.zbar.Qr.ScanResult;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;
import cn.bertsir.zbar.utils.QRUtils;
import cn.bertsir.zbar.view.ScanLineView;

public class QRCodeAPI extends API<QRCodeAPI> {
    private QrConfig config;

    @Override
    public QRCodeAPI getDefault() {
        initConfig();
        return this;
    }

    @Override
    String getTag() {
        return "QRCodeAPI";
    }

    private void initConfig() {
        config = new QrConfig.Builder()
                .setDesText("请对准需要识别的二维码")//扫描框下文字
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
                .setTitleText("扫一扫")//设置Tilte文字
                .setTitleBackgroudColor(Color.parseColor("#131958"))//设置状态栏颜色
                .setTitleTextColor(Color.WHITE)//设置Title文字颜色
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
    }

    public void setConfig(QrConfig config) {
        this.config = config;
    }

    /**
     * 扫描二维码
     */
    public void scanQRCode(Activity activity, final CallBack callback) {
        showLog("scanQRCode");

        QrManager.getInstance().init(config).startScan(activity, new QrManager.OnScanResultCallback() {
            @Override
            public void onScanSuccess(ScanResult result) {
                if (null != result) {
                    callback.onSuccess(result.content);
                    showLog("scanQRCode = " + result.type + " : " + result.content);
                }
                else
                    callback.onFailed("scan no result");
            }
        });
    }

    /**
     * 生成二维码
     *
     * @return String base64格式的图片资源
     * */
    public String createQRCodeString(String origin) {
        if (TextUtils.isEmpty(origin)) {
            showError("params is null or empty");
            return null;
        }
        showLog("createQRCode = " + origin);

        Bitmap bitmap = QRUtils.getInstance().createQRCode(origin);

        String base64 = QRUtils.bitmapToBase64(bitmap);
        showLog(base64);
        return "data:image/png;base64," + base64;
    }

    /**
     * 生成二维码
     * 仅限于Android原生调用
     * @return Bitmap
     * */
    public Bitmap createQRCode(String origin) {
        if (TextUtils.isEmpty(origin)) {
            showError("params is null or empty");
            return null;
        }
        showLog("createQRCode = " + origin);

        return QRUtils.getInstance().createQRCode(origin);
    }

    /**
     * 生成条形码
     * @return String base64格式的图片资源
     */
    public String createBarCodeString(Context context, String origin, int width, int height) {
        if (TextUtils.isEmpty(origin)) {
            showError("params is null or empty");
            return null;
        }
        showLog("createBarCode = " + origin);

        if (width == 0) width = 400;
        if (height == 0) height = 200;

        Bitmap bitmap = QRUtils.getInstance().createBarCodeWithText(context, origin, width, height);

        String base64 = QRUtils.bitmapToBase64(bitmap);
        showLog(base64);
        return "data:image/png;base64," + base64;
    }

    /**
     * 生成条形码
     * @return String base64格式的图片资源
     */
    public Bitmap createBarCode(Context context, String origin, int width, int height) {
        if (TextUtils.isEmpty(origin)) {
            showError("params is null or empty");
            return null;
        }
        showLog("createBarCode = " + origin);

        if (width == 0) width = 400;
        if (height == 0) height = 200;

        return QRUtils.getInstance().createBarCodeWithText(context, origin, width, height);
    }

    /**
     * bitmap转base64
     *
     * @param @param  bitmap
     * @param @return 设定文件
     * @return String    返回类型
     * @throws
     * @Title: bitmapToBase64
     */
    @SuppressLint("NewApi")
    public static String bitmapToBase64(Bitmap bitmap) {

        // 要返回的字符串
        String reslut = null;

        ByteArrayOutputStream baos = null;

        try {

            if (bitmap != null) {

                baos = new ByteArrayOutputStream();
                /**
                 * 压缩只对保存有效果bitmap还是原来的大小
                 */
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);

                baos.flush();
                baos.close();
                // 转换为字节数组
                byte[] byteArray = baos.toByteArray();

                // 转换为字符串
                reslut = Base64.encodeToString(byteArray, Base64.DEFAULT);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return reslut;

    }

    /**
     * base64转bitmap
     *
     * @param @param  base64String
     * @param @return 设定文件
     * @return Bitmap    返回类型
     * @throws
     * @Title: base64ToBitmap
     */
    public static Bitmap base64ToBitmap(String base64String) {

        byte[] decode = Base64.decode(base64String, Base64.DEFAULT);

        Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);

        return bitmap;
    }
}
