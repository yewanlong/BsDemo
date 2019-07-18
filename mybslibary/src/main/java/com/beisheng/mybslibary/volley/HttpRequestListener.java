package com.beisheng.mybslibary.volley;


/**
 * 请求监听类
 *
 * @param <T>
 * @author YWL
 */
public abstract class HttpRequestListener<T> {

    /**
     * 成功
     *
     * @param response
     */
    protected abstract void onSuccess(int what, T response);

    /**
     * 错误
     *
     * @param code    TODO
     * @param message TODO
     * @param error
     */
    protected abstract void onError(int what, int code, String message);
}
