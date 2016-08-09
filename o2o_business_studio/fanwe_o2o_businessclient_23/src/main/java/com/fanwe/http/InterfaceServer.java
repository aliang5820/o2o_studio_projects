package com.fanwe.http;

import com.fanwe.application.App;
import com.fanwe.common.HttpManagerX;
import com.fanwe.constant.ApkConstant;
import com.fanwe.http.listener.proxy.RequestCallBackProxy;
import com.fanwe.library.utils.AESUtil;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDBase64;
import com.fanwe.library.utils.SDFileUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.RequestModel;
import com.fanwe.model.RequestModel.MultiFile;
import com.fanwe.model.RequestModel.RequestDataType;
import com.fanwe.utils.JsonUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.ta.util.netstate.TANetWorkUtil;

import org.apache.http.NameValuePair;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 接口请求类
 */
public class InterfaceServer {

    private static final class Holder {
        static final InterfaceServer mInstance = new InterfaceServer();
    }

    private InterfaceServer() {

    }

    public static InterfaceServer getInstance() {
        return Holder.mInstance;
    }

    public HttpHandler<String> requestInterface(RequestModel model, RequestCallBack<String> responseListener) {
        return requestInterface(HttpMethod.POST, model, null, true, responseListener);
    }

    /**
     * @param httpMethod       请求方法
     * @param model            RequestModel
     * @param responseListener 回调监听
     * @param httpUtils        HttpUtils对象，如果为null则用全局单例对象，不为null则用该对象
     * @param isNeedProxy      是否需要代理
     * @return
     */
    public HttpHandler<String> requestInterface(HttpMethod httpMethod, RequestModel model, HttpUtils httpUtils, boolean isNeedProxy,
                                                RequestCallBack<String> responseListener) {
        if (TANetWorkUtil.isNetworkAvailable(App.getApp())) {
            if (model != null) {
                RequestParams requestParams = getRequestParams(model);
                RequestCallBack<String> listener = null;
                if (isNeedProxy) {
                    listener = getDefaultProxy(responseListener, model);
                } else {
                    listener = responseListener;
                }
                if (httpUtils != null) {
                    return httpUtils.send(httpMethod, ApkConstant.getServerApiUrl(), requestParams, listener);
                } else {
                    return HttpManagerX.getHttpUtils().send(httpMethod, ApkConstant.getServerApiUrl(), requestParams, listener);
                }
            } else {
                return null;
            }
        } else {
            SDToast.showToast("网络不可用");
            responseListener.onFailure(null, "网络不可用");
            responseListener.onFinish();
            return null;
        }
    }

    private RequestCallBack<String> getDefaultProxy(RequestCallBack<String> handler, RequestModel model) {
        return new RequestCallBackProxy(handler, model);
    }

    private void printRequestUrl(RequestParams param) {
        if (ApkConstant.DEBUG) {
            LogUtil.e(getRequestUrl(param));
        }
    }

    private String getRequestUrl(RequestParams param) {
        StringBuilder sb = new StringBuilder(ApkConstant.getServerApiUrl() + "?");
        if (param != null) {
            List<NameValuePair> listParam = param.getQueryStringParams();
            for (NameValuePair nameValuePair : listParam) {
                String name = nameValuePair.getName();
                String value = nameValuePair.getValue();

                if ("r_type".equals(name)) {
                    value = String.valueOf(2);
                }
                sb.append("&");
                sb.append(name);
                sb.append("=");
                sb.append(value);
            }
        }
        return sb.toString();
    }

    private RequestParams getRequestParams(RequestModel model) {
        RequestParams requestParams = new RequestParams();
        Map<String, Object> data = model.getmData();
        Map<String, File> dataFile = model.getmDataFile();
        List<MultiFile> multiFile = model.getmMultiFile();
        if (data != null) {
            String requestData = null;
            String json = JsonUtil.object2Json(data);

            switch (model.getmRequestDataType()) {
                case RequestDataType.BASE64:
                    requestData = SDBase64.encodeToString(json);
                    break;
                case RequestDataType.AES:
                    requestData = AESUtil.encrypt(json, ApkConstant.KEY_AES);
                    break;
                default:
                    break;
            }

            requestParams.addQueryStringParameter("requestData", requestData);
            requestParams.addQueryStringParameter("i_type", String.valueOf(model.getmRequestDataType()));
            requestParams.addQueryStringParameter("r_type", String.valueOf(model.getmResponseDataType()));
            requestParams.addQueryStringParameter("ctl", String.valueOf(data.get("ctl")));
            requestParams.addQueryStringParameter("act", String.valueOf(data.get("act")));
        }
        if (dataFile != null) {
            for (Entry<String, File> itemFile : dataFile.entrySet()) {
                if (itemFile != null) {
                    String key = itemFile.getKey();
                    File file = itemFile.getValue();
                    String mimeType = SDFileUtil.getMimeType(file);
                    requestParams.addBodyParameter(key, file, mimeType);
                }
            }
        }

        if (multiFile != null) {
            for (MultiFile itemFile : multiFile) {
                String key = itemFile.getKey();
                File file = itemFile.getFile();
                String mimeType = SDFileUtil.getMimeType(file);
                requestParams.addBodyParameterMulti(key, file, mimeType);
            }
        }

        printRequestUrl(requestParams);
        return requestParams;
    }

}
