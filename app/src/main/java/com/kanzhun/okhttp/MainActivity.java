package com.kanzhun.okhttp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.kanzhun.okhttp.common.GetMusicListRequest;
import com.kanzhun.okhttp.common.GetMusicListResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kz.ally.okhttp.OkHttpSdk;
import kz.ally.okhttp.callback.BitmapCallback;
import kz.ally.okhttp.callback.FileCallback;
import kz.ally.okhttp.callback.GsonCallback;
import kz.ally.okhttp.callback.StringCallback;
import kz.ally.okhttp.method.GetRequestBuilder;
import okhttp3.Call;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private ImageView ivImage;
    private ProgressBar pb;
    private Button btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDownload = (Button) findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(this);
        ivImage = (ImageView) findViewById(R.id.iv_image);
        pb = (ProgressBar) findViewById(R.id.progress_bar);
        pb.setProgress(100);

        get();
    }

    private void get() {
        GetMusicListRequest request = new GetMusicListRequest(new GsonCallback<GetMusicListResponse>() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(GetMusicListResponse resp) {
                if (resp != null) {
                    Log.d(TAG, resp.toString());
                }
            }
        });
        request.q = "银魂";
        request.start = 0;
        request.count = 1;
        OkHttpSdk.getInstance().executeRequest(request);




//        String url = "https://api.douban.com/v2/music/search";
//        Params params = new Params();
//        params.put("q", "银魂");
//        params.put("start", 0);
//        params.put("count", 1);
//        OkHttpSdk.getInstance().get(url, params, new GsonCallback<NetMusic>() {
//
//            @Override
//            public void onError(Call call, Exception e) {
//
//            }
//
//            @Override
//            public void onResponse(NetMusic music) {
//                Log.d(TAG, music.toString());
//            }
//        });
    }

    /**
     * 下载文件
     */
    private void downloadFile() {
        String url = "https://www.zhipin.com/d/v2/?type=ckand&pkn=intro";
        String fileName = "bosszhipin.apk";
        OkHttpSdk.getInstance().get(url, new FileCallback(fileName) {

            @Override
            protected void inProgress(float progress, long total) {
                btnDownload.setText("正在下载中...");
                pb.setProgress((int) (progress * 100));

            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(File resp) {
                btnDownload.setText("下载完成");
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

        List<GetRequestBuilder> builderList = new ArrayList<>();
        builderList.add(profile);
        builderList.add(list);
        OkHttpSdk.getInstance().batch(url, batchKey, builderList, new StringCallback() {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpSdk.getInstance().cancelDefaultTag();
    }
}
