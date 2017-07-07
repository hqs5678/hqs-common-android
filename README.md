# hqs-common
一些Android常用的工具类, 以及其他一些开源的代码.
 
### 添加到项目

#### gradle

```

dependencies {
    compile 'com.hqs.common:hqs-common:1.1.13'
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




