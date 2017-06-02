package kz.ally.okhttp.error;

/**
 * Author: ZhouYou
 * Date: 2017/6/2.
 */
public enum ErrorReason {

    NET_TIMEOUT_ERROR("网络请求超时，请检查网络后重试"),
    NET_ERROR("网络请求失败，请检查网络后重试"),
    JSON_ERROR("数据读取失败，请稍后重试"),
    DOWNLOAD_ERROR("文件下载失败，请检查网络后重试"),
    LOGIN_ERROR("登录发生异常，请重新登录"),
    OTHER("数据请求未知异常，请稍后重试");

    private String reason;
    ErrorReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
