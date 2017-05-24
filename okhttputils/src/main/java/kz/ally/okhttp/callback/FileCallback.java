package kz.ally.okhttp.callback;

import java.io.File;
import java.io.IOException;

import okhttp3.Response;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * Author: ZhouYou
 * Date: 2017/5/24.
 */
public abstract class FileCallback extends AbsCallback<File> {

    private String dir;
    private String fileName;

    public FileCallback(String dir, String fileName) {
        this.dir = dir;
        this.fileName = fileName;
    }

    @Override
    public File parseResponse(Response resp) throws IOException {
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
        }, 1000);
    }

    protected void inProgress(float progress, long total) {

    }
}
