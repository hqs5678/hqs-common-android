# hqs-common
一些Android常用的工具类, 以及其他一些开源的代码.

### 添加到项目

#### gradle

```

dependencies {
    compile 'com.hqs.common:hqs-common:1.1.15'
}

```

### 使用说明

#### 1. AppCallbackProcessor.java
>  全局的回调, 可以部分替换startActivityForResult, 广播, 同步, 界面之间传值等. 使用WeakReference避免循环引用, 造成内存泄露.

##### 类方法
- AppCallbackProcessor.addCallback(call)
- AppCallbackProcessor.addCallback(call, id)
- AppCallbackProcessor.call(params)
- AppCallbackProcessor.call(params, id)
- AppCallbackProcessor.removeCallback(id)
- AppCallbackProcessor.removeAllCallbacks()
##### AppCallback


```
public interface AppCallback {
    void call(Map<String, Object> params);
}
```
###### 说明: call函数中的param, 可以在回调是把参数以键值对的方式传递

##### 使用示例


```
public class Main1Activity extends AppCompatActivity {


    private TextView tv;
    private AppCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        callback = new AppCallbackProcessor.AppCallback() {
            @Override
            public void call(Map<String, Object> params) {
                tv.setText(params.get("title").toString());
            }
        );
        tv = (TextView) findViewById(R.id.text);
    }

    public void buttonClick(View view){

        // 设置回调
        AppCallbackProcessor.addCallback(callback);

        // 注意: 由于使用WeakReference,
        // 如果直接用匿名内部类的方式调用, 如:
        // AppCallbackProcessor.addCallback(new  AppCallbackProcessor.AppCallback() {
        //    @Override
        //    public void call(Map<String, Object> params) {
        //        tv.setText(params.get("title").toString());
        //    }
        // });
        // callback可能会被销毁, 造成在回调时(AppCallbackProcessor.call(params))没有任何反应,
        // 原因是callback已被销毁,
        // 解决: 将callback设为为activity的一个字段.

        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
```
##### 在Main2Activity中回调, 例如:
```
// 在Main2Activity中回调
public void buttonClick(View view){

    Map<String, Object>params = new HashMap<>();
    params.put("title", "hello");

    // 回调
    AppCallbackProcessor.call(params);

    finish();
}


```

#### 2. ActivityUtil.java
> 设置全屏显示activity, 隐藏actionBar
##### 类方法
- ActivityUtil.setActivityFullScreen(activity)
- ActivityUtil.hideActionBar(activity)

#### 3. AppUtil.java
> 获取手机型号等
##### 类方法
- AppUtil.getAppName(context) // 获取应用程序的名称
- AppUtil.getVersionName(context) // 获取应用程序版本名称信息
- AppUtil.getDeviceId(context) // 获取设备的唯一标识，deviceId
- AppUtil.getPhoneBrand() // 获取手机品牌
- AppUtil.getPhoneModel() // 获取手机型号
- AppUtil.getBuildLevel() // 获取手机系统API
- AppUtil.getBuildVersion() // 获取手机版本
- AppUtil.isAppInstalled(context, packageName) // 判断是否安装

#### 其他类
- ColorUtil
- DateUtil
- DensityUtil
- Log
- MD5Util
- NetUtil
- ScreenUtil
- SDCardUtil
- SharedPreferenceUtil
- StatusBarUtil
- ValidteUtil
- ViewUtil
- 自定义TabBarView,简单实现Tab页的主界面
- 自定NavigationBar
- TimerButton(倒计时功能的按钮)

#### 使用方法

> 本人实在太懒, 请感兴趣的同学点进去直接查看源码, 使用方法简单方便
