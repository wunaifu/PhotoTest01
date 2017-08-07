package com.mian.phototest01;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView1;
//    private Bitmap mBitmap ;
    private String urlImage = "https://p1.ssl.qhimg.com/t0151320b1d0fc50be8.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        mImageView1 = (ImageView) findViewById(R.id.img_head_icon);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //线程中不能更新图片
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            //网络中获取图片
                            Bitmap bitmap = null;
                            URL pictureUrl = new URL("https://p1.ssl.qhimg.com/t0151320b1d0fc50be8.png");
                            InputStream in = pictureUrl.openStream();
                            bitmap = BitmapFactory.decodeStream(in);
                            //线程中不能更新图片,回到主线程中更新图片
                            Message message = new Message();
                            message.what = 0;
                            message.obj = bitmap;
                            handler.sendMessage(message);

                            in.close();
                        } catch (MalformedURLException e)
                        {
                            e.printStackTrace();
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //部署图片
            mImageView1.setImageBitmap((Bitmap) msg.obj);
        }
    };

}
