package com.kanzhun.okhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.kanzhun.okhttp.common.GetMusicListRequest;
import com.kanzhun.okhttp.common.GetMusicListResponse;

import kz.ally.okhttp.OkHttpSdk;
import kz.ally.okhttp.callback.ObjCallback;
import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ImageView ivImage;
    private ProgressBar pb;
    private Button btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDownload = (Button) findViewById(R.id.btn_download);
        ivImage = (ImageView) findViewById(R.id.iv_image);
        pb = (ProgressBar) findViewById(R.id.progress_bar);
        pb.setProgress(100);

        get();
    }

    private void get() {
        GetMusicListRequest request = new GetMusicListRequest(new ObjCallback<GetMusicListResponse>() {
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpSdk.getInstance().cancelDefaultTag();
    }
}
