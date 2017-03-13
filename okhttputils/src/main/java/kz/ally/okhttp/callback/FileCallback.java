package kz.ally.okhttp.callback;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.Response;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * 作者：ZhouYou
 * 日期：2017/2/24.
 */
public abstract class FileCallback extends AbsCallback<File> {

    private static final String TAG = "FileCallback";

    private String dir;
    private String fileName;

    public FileCallback(String name) {
        dir = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.d(TAG, dir);
        fileName = name;
    }

    public FileCallback(String dir, String name) {
        this.dir = dir;
        fileName = name;
    }

    @Override
    public File parseResponse(Response resp) {
        return downloadFile(resp);
    }

    private File downloadFile(Response resp) {
        BufferedSink sink;
        BufferedSource source;
        File file = null;
        try {
            long total = resp.body().contentLength();
            source = resp.body().source();
            file = new File(dir, fileName);
            if (!file.exists()) file.createNewFile();
            sink = Okio.buffer(Okio.sink(file));
            byte[] buf = new byte[1024];
            int len;
            long progress = 0;
            while ((len = source.read(buf)) != -1) {
                progress += len;
                sink.write(buf, 0, len);
                updateProgress(progress, total);
            }
            sink.close();
            source.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (resp.body() != null) {
                resp.body().close();
            }
        }
        return file;
    }

    /**
     * 更新进度
     *
     * @param progress
     * @param total
     */
    private void updateProgress(final float progress, final long total) {
        MainThread.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                inProgress(progress * 1.0f / total, total);
            }
        });
    }

    protected void inProgress(float progress, long total) {

    }
}
