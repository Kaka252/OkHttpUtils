package com.kanzhun.okhttp;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kanzhun.okhttp.common.DownloadApkRequest;
import com.kanzhun.okhttp.common.GetMusicListRequest;
import com.kanzhun.okhttp.common.GetMusicListResponse;

import java.io.File;

import kz.ally.okhttp.OkHttpSdk;
import kz.ally.okhttp.callback.FileCallback;
import kz.ally.okhttp.callback.ObjCallback;
import kz.ally.okhttp.error.ErrorReason;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ImageView ivImage;
    private ProgressBar pb;
    private Button btnDownload;
    private TextView tvDownloadPercentage;

    public GetMusicListRequest getMusicListRequest;
    public DownloadApkRequest downloadApkRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDownload = (Button) findViewById(R.id.btn_download);
        tvDownloadPercentage = (TextView) findViewById(R.id.tv_download_percentage);
        ivImage = (ImageView) findViewById(R.id.iv_image);
        pb = (ProgressBar) findViewById(R.id.progress_bar);
        pb.setMax(100);
        pb.setProgress(0);
        tvDownloadPercentage.setText("0.00%");
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile();
            }
        });
        get();
    }

    private void get() {
        getMusicListRequest = new GetMusicListRequest(new ObjCallback<GetMusicListResponse>() {

            @Override
            public void onFailed(ErrorReason reason) {
                Toast.makeText(getApplicationContext(), reason.getReason(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(GetMusicListResponse resp) {
                if (resp != null) {
                    Log.d(TAG, resp.toString());
                }
            }
        });
        getMusicListRequest.q = "银魂";
        getMusicListRequest.start = 0;
        getMusicListRequest.count = 1;
        getMusicListRequest.setTag(this);
        OkHttpSdk.getInstance().executeRequest(getMusicListRequest);
    }

    private void downloadFile() {
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
        String name = "高德地图.apk";
        downloadApkRequest = new DownloadApkRequest(new FileCallback(dir, name) {

            @Override
            public void onStart() {
                Log.d(TAG, "下载开始，等待中...");
            }

            @Override
            protected void inProgress(double progress, long total) {
                int p = (int) (progress * 100);
                pb.setProgress(p);
                tvDownloadPercentage.setText(progress * 100 + "%");
            }

            @Override
            public void onFailed(ErrorReason reason) {
                Toast.makeText(getApplicationContext(), reason.getReason(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(File resp) {
                Log.d(TAG, "下载完成");
            }
        });
        downloadApkRequest.setTag(this);
        OkHttpSdk.getInstance().executeDownload(downloadApkRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getMusicListRequest != null) getMusicListRequest.cancel();
        if (downloadApkRequest != null) downloadApkRequest.cancel();
    }
}
