package kz.ally.okhttp.callback;

import android.support.annotation.NonNull;

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

    private String dir;
    private String name;

    public FileCallback(@NonNull String dir, @NonNull String name) {
        this.dir = dir;
        this.name = name;
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
            file = new File(dir, name);
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
