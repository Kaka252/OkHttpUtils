package com.kanzhun.okhttp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.kanzhun.okhttp.bean.NetMusic;

import java.io.File;

import kz.ally.okhttp.ApiRequestCall;
import kz.ally.okhttp.OkHttpSdk;
import kz.ally.okhttp.callback.BitmapCallback;
import kz.ally.okhttp.callback.FileCallback;
import kz.ally.okhttp.callback.GsonCallback;
import kz.ally.okhttp.callback.StringCallback;
import kz.ally.okhttp.config.Params;
import kz.ally.okhttp.method.GetRequestBuilder;
import okhttp3.Call;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private ImageView ivImage;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_download).setOnClickListener(this);
        ivImage = (ImageView) findViewById(R.id.iv_image);
        pb = (ProgressBar) findViewById(R.id.progress_bar);
        pb.setProgress(100);
        get();
        downloadFile();
    }

    private void get() {
        String url = "https://api.douban.com/v2/music/search";
        Params params = new Params();
        params.put("q", "银魂");
        params.put("start", 0);
        params.put("count", 1);
        OkHttpSdk.getInstance().get(url, params, new GsonCallback<NetMusic>() {

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(NetMusic music) {
                Log.d(TAG, music.toString());
            }
        });
    }

    /**
     * 下载文件
     */
    private void downloadFile() {
        String url = "https://www.zhipin.com/d/v2/?type=ckand&pkn=intro";
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = "bosszhipin.apk";
        OkHttpSdk.getInstance().get(url, new FileCallback(dir, fileName) {

            @Override
            protected void inProgress(float progress, long total) {
                pb.setProgress((int) (progress * 100));
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(File resp) {
//                Uri uri = Uri.fromFile(resp);
//                if (uri != null) {
//                    ivImage.setImageURI(uri);
//                }
            }
        });
    }

    private void getBitmap() {
        String url = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1487925706&di=d5eafb6a2bf83796ffb88a91322af3f4&src=http://i0.hdslb.com/bfs/face/b7246b976ee6225da7258dc604683af258d69709.jpg";
        OkHttpSdk.getInstance().get(url, new BitmapCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Bitmap resp) {
                ivImage.setImageBitmap(resp);
            }
        });
    }

    private void getBatch() {
        String url = "http://api.weizhipin.com/api/batch/batchRun";
        String batchKey = "batch_method_feed";

        GetRequestBuilder profile = new GetRequestBuilder();
        profile.url("geek/getBossProfile");
        profile.addParam("bossId", 1823);

        GetRequestBuilder list = new GetRequestBuilder();
        list.url("geek/getBossProfileJobList");
        list.addParam("bossId", 1823);
        list.addParam("page", 1);

        ApiRequestCall call = OkHttpSdk.getInstance()
                .batch(url, batchKey)
                .addRequest(profile)
                .addRequest(list)
                .build();
        call.async(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String resp) {
                Log.d(TAG, resp);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_download:
                downloadFile();
                break;
            default:
                break;
        }
    }
}
