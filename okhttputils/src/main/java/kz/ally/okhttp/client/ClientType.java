package kz.ally.okhttp.client;

/**
 * Author: ZhouYou
 * Date: 2017/5/27.
 * 记录进行的请求是在哪个Client
 */
public enum ClientType {

    NORMAL(0),

    UPLOAD_DOWNLOAD(1),

    IMAGE_LOADER(2),;

    private final int value;

    ClientType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
