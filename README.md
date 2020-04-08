# APP-QRCode
a common QR code lib for android of zndroid's libs
整理以下开源库得到，多谢支持

![](http://tu.bertsir.com/images/2019/06/27/Cool-Sky_meitu_1.jpg)

zbar扫描快，zxing可以生成和识别本地，So,我就把他们结合在了一起，这样二维码识别就更便捷了（包含主要功能，二维码识别生成，条形码识别生成）


## 预览
![try8b-prgwd.gif](https://upload-images.jianshu.io/upload_images/3029020-49812952c5a9ab5b.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/360)
![fdvtz-jgjsx.gif](https://upload-images.jianshu.io/upload_images/3029020-467d1968c57fcbfa.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/360)
![WechatIMG25.png](https://upload-images.jianshu.io/upload_images/3029020-e04bb39b1aae23dd.jpeg?imageMogr2/auto-orient/strip%7CimageView2/2/w/360)

## 引入
#### 方式一（需要修改布局的）：
GitHub下载库，使用File -> new -> Import Module方式

#### 方式二（不需要修改布局）：
最新版本（推荐）：
<pre>
 implementation 'cn.bertsir.zbarLibary:zbarlibary:latest.release'
</pre>
指定版本：
<pre>
implementation 'cn.bertsir.zbarLibary:zbarlibary:1.4.2'
</pre>
注意：如果不需要尝鲜后续功能，并且保持现有稳定，建议使用指定版本号
#### 方式三:

同以上两种，只是使用</b>lib_QRCode</b>库


#### 关于包的大小问题
为了确保全平台的兼容，默认库中携带了arm64-v8a，armeabi，armeabi-v7a，mips，mips64，x86，x86_64，的so文件，可能会导致安装包体积大，和其他第三方SDK冲突的问题，可以使用以下代码解决大小和冲突
<pre>
android {
    ......
    defaultConfig {
        ......
		......
		......
        ndk {
            abiFilters "armeabi-v7a"  // 指定要ndk需要兼容的架构(这样其他依赖包里mips,x86,armeabi,arm-v8之类的so会被过滤掉)
        }
    }
}
</pre>


### 使用方法
## 1.识别二维码（条形码）
<pre>

        QrConfig qrConfig = new QrConfig.Builder()
                .setDesText("(识别二维码)")//扫描框下文字
                .setShowDes(false)//是否显示扫描框下面文字
                .setShowLight(true)//显示手电筒按钮
                .setShowTitle(true)//显示Title
                .setShowAlbum(true)//显示从相册选择按钮
                .setCornerColor(Color.WHITE)//设置扫描框颜色
                .setLineColor(Color.WHITE)//设置扫描线颜色
                .setLineSpeed(QrConfig.LINE_MEDIUM)//设置扫描线速度
                .setScanType(QrConfig.TYPE_QRCODE)//设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
                .setScanViewType(QrConfig.SCANVIEW_TYPE_QRCODE)//设置扫描框类型（二维码还是条形码，默认为二维码）
                .setCustombarcodeformat(QrConfig.BARCODE_I25)//此项只有在扫码类型为TYPE_CUSTOM时才有效
                .setPlaySound(true)//是否扫描成功后bi~的声音
                .setNeedCrop(true)//从相册选择二维码之后再次截取二维码
                .setDingPath(R.raw.test)//设置提示音(不设置为默认的Ding~)
                .setIsOnlyCenter(true)//是否只识别框中内容(默认为全屏识别)
                .setTitleText("扫描二维码")//设置Tilte文字
                .setTitleBackgroudColor(Color.BLUE)//设置状态栏颜色
                .setTitleTextColor(Color.BLACK)//设置Title文字颜色
                .setShowZoom(false)//是否手动调整焦距
                .setAutoZoom(false)//是否自动调整焦距
                .setFingerZoom(false)//是否开始双指缩放
                .setScreenOrientation(QrConfig.SCREEN_PORTRAIT)//设置屏幕方向
                .setDoubleEngine(false)//是否开启双引擎识别(仅对识别二维码有效，并且开启后只识别框内功能将失效)
                .setOpenAlbumText("选择要识别的图片")//打开相册的文字
                .setLooperScan(false)//是否连续扫描二维码
                .setLooperWaitTime(5*1000)//连续扫描间隔时间
                .setScanLineStyle(ScanLineView.style_radar)//扫描动画样式
                .setAutoLight(false)//自动灯光
                .setShowVibrator(false)//是否震动提醒
                .create();
   QrManager.getInstance().init(qrConfig).startScan(MainActivity.this, new QrManager.OnScanResultCallback() {
            @Override
            public void onScanSuccess(ScanResult result) {
                Log.e(TAG, "onScanSuccess: "+result );
                Toast.makeText(getApplicationContext(), "内容："+result.getContent()
                                +"  类型："+result.getType(), Toast.LENGTH_SHORT).show();
            }
        });
</pre>
OK,就这么简单！

##### 如果扫描界面不符合你的需求，来吧QRActivity的布局文件你随便改，保证改起来比别的库简单！

## 2.生成码
###  2.1生成二维码 
<pre>
Bitmap qrCode = QRUtils.getInstance().createQRCode("www.qq.com");
</pre>

####  2.1.1生成二维码并添加Logo
<pre>
Bitmap qrCode = QRUtils.getInstance().createQRCodeAddLogo(et_qr_content.getText().toString(),BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
</pre>
###  2.2生成条形码
<pre>
QRUtils.TextViewConfig textViewConfig = new QRUtils.TextViewConfig();
textViewConfig.setSize(10);
 Bitmap barCodeWithText = QRUtils.getInstance().createBarCodeWithText(getApplicationContext(), content, 300, 100, textViewConfig);
</pre>

## 3.识别本地
### 3.1 识别本地二维码
<pre>
//可以传图片路径，Bitmap,ImageView 是不是很人性化
String s = QRUtils.getInstance().decodeQRcode(iv_qr);
</pre>

### 3.2 识别本地条形码
<pre>
//可以传图片路径，Bitmap,ImageView 是不是很人性化
String s = QRUtils.getInstance().decodeBarcode(iv_qr);
</pre>

## 4.参数描述
| name | format | description |
| ------------- |:-------------:| :-------------:|
| setDesText | String | 设置扫描框下方描述文字 |
| setShowDes | Boolean | 设置是否显示扫描框下方描述文字 |
| setShowLight | Boolean | 是否开启手电筒功能 |
| setShowAlbum | Boolean | 是否开启从相册选择功能 |
| setShowTitle | Boolean | 是否显示Title |
| setTitleText | String | 设置Title文字 |
| setTitleBackgroudColor | int | 设置Title背景色 |
| setTitleTextColor | int | 设置Title文字颜色 |
| setCornerColor | int | 设置扫描框颜色 |
| setLineColor | int | 设置扫描线颜色 |
| setLineSpeed | int | 设置扫描线速度</br>QrConfig.LINE_FAST(快速)</br>QrConfig.LINE_MEDIUM(中速）<br>QrConfig.LINE_SLOW(慢速) <br>也可以自定义时间(单位毫秒)|
| setScanType | int | 设置扫描类型</br>QrConfig.TYPE_QRCODE(二维码)</br>QrConfig.TYPE_BARCODE(条形码)</br>QrConfig.TYPE_ALL(全部类型)</br>QrConfig.TYPE_CUSTOM(指定类型) |
| setScanViewType | int | 设置扫描框类型</br>QrConfig.SCANVIEW_TYPE_QRCODE(二维码)</br>QrConfig.SCANVIEW_TYPE_BARCODE(条形码) |
| setCustombarcodeformat| int| 设置指定扫码类型（举例：QrConfig.BARCODE_EAN13）,此项只有在ScanType设置为自定义时才生效 |
| setIsOnlyCenter| Boolean | 设置是否只识别扫描框中的内容（默认为全屏扫描） |
| setPlaySound | Boolean | 设置扫描成功后是否有提示音 |
| setDingPath | int| 自定义提示音（举例：R.raw.test，不设置为默认的) |
| setNeedCrop | Boolean | 从相册选择二维码之后再次手动框选二维码(默认为true) |
| setShowZoom | Boolean | 是否开启手动调整焦距(默认为false) |
| setAutoZoom | Boolean | 是否开启自动调整焦距(默认为false) |
| setFingerZoom | Boolean | 是否开启双指调整焦距(默认为false) |
| setScreenOrientation | int | 设置屏幕方向</br>QrConfig.SCREEN_PORTRAIT(纵向)</br>QrConfig.SCREEN_LANDSCAPE(横向）<br>QrConfig.SCREEN_SENSOR(传感器方向) |
| setDoubleEngine | Boolean | 是否开启双识别引擎(默认为false) |
| setLooperScan | Boolean | 是否开启连续扫描(默认为false) |
| setOpenAlbumText | String | 设置打开相册的文字 |
| setLooperWaitTime | int | 设置连续扫描间隔时间，单位毫秒（默认为0） |
| setScanLineStyle | int | 设置扫描动画样式</br>ScanLineView.style_radar(雷达)</br>ScanLineView.style_gridding(网格）<br>ScanLineView.style_hybrid(网格+雷达) <br>ScanLineView.style_line(线条)（默认为雷达） |
| setAutoLight | Boolean | 是否开启自动灯光(默认为false)|
| setShowVibrator | Boolean | 是否开启震动提醒(默认为false)|
| setBackImageRes | int | 设置title返回图标，不设置为默认|
| setLightImageRes | int | 设置闪光灯图标，不设置为默认|
| setAblumImageRes | int | 设置相册图标，不设置为默认|

## 5.混淆
<pre>
-keep class cn.bertsir.zbar.Qr.** { *; }
</pre>

## 6.依赖引用
<pre>
compile 'com.google.zxing:core:3.3.0'//zxing
compile 'com.soundcloud.android:android-crop:1.0.1@aar'//图片裁切
</pre>


#### 二维码也就这些需求吧，这么简单就可以搞定了，识别速度是zxing的很多倍！方便了你的话可不可以给个Start，如遇BUG请Issues


### DEMO

#### [DEMO下载](https://github.com/bertsir/zBarLibary/releases/download/1.4.2/app-debug.apk "DEMO下载")

## License
<pre>
MIT License

Copyright (c) 2018 bertsir

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

</pre>
