package com.zj.vedio;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.net.SSLContextBuilder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.io.InputStream;
import java.security.cert.X509Certificate;

public class AiYinShuDownload {
    public static void main(String[] args) {
        // 书记编号：长生不死
        int bookNo = 21693;
        // 开始章节
        int start = 1;
        // 结束章节
        int end = 1671;
        init();
        for (int i = start; i <= end; i++) {
            HttpRequest httpRequest = HttpUtil.createGet(String.format("https://mp3.shuyinfm.com/d/audio/%s/%s.mp3", bookNo, i));
            HttpResponse response = httpRequest.execute();
            String realUrl = response.header("Location");
            httpRequest = HttpUtil.createGet(realUrl);
            response = httpRequest.execute();
            InputStream inputStream = response.bodyStream();
            FileUtil.writeFromStream(inputStream, String.format("\\audiobooks\\%s.mp3", i));
        }
    }

    private static void init() {
        SSLContext sslContext = SSLContextBuilder.create().setTrustManagers(new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                })
                .build();
        SSLContext.setDefault(sslContext);
    }
}
