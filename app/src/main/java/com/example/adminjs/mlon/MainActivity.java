package com.example.adminjs.mlon;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import okhttp3.Callback;
import okhttp3.Request;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private List<MyBean.DataBean.Ad1Bean> ad1;
    private Banner banner;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        banner = (Banner) findViewById(R.id.banner);
        getData();
        banner.setImageLoader(new ImageLoaderBanner());
        list = new ArrayList<>();


    }

    public void getData(){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url("http://m.yunifang.com/yunifang/mobile/home")
                .build();
        client.newCall(request).enqueue(new Callback() {


            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                final String str=response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson=new Gson();
                        MyBean bean=gson.fromJson(str,MyBean.class);
                        ad1 = bean.getData().getAd1();
                        for (int i=0;i<ad1.size();i++){
                            list.add(ad1.get(i).getImage());
                        }
                        //设置集合
                        banner.setImages(list);
                        //banner执行完方法之后调用
                        banner.start();
                    }
                });

            }
        });
    }
    public class ImageLoaderBanner extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
//Glide设置图片的简单用法
            Glide.with(context).load(path).into(imageView);
        }
    }

}