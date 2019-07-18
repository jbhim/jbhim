package com.amcjt.jbhim.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class HttpRequestHelper {

    public static final String CHARSET = "UTF-8";
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestHelper.class);

    /**
     * HTTP Get 获取内容
     *
     * @param url    请求的url地址 ? 之前的地址
     * @param params 请求的参数
     * @return 页面内容
     */
    public static String sendGet(String url, Map<String, Object> params) {
        StringBuffer resultBuffer = new StringBuffer();
        try {
            if (!StringUtils.hasText(url)) {
                throw new IOException("url can not be null or empty value");
            }
            StringBuilder urlBuilder = new StringBuilder(url);
            urlBuilder.append("?");
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
            }
            URL localUrl = new URL(urlBuilder.toString().substring(0, urlBuilder.length() - 1));
            URLConnection connection = localUrl.openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) connection;
            httpUrlConnection.setRequestProperty("Accept-Charset", CHARSET);
            httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String tempLine = null;
            //响应失败
            if (httpUrlConnection.getResponseCode() != 200) {
                throw new IOException("HTTP Request is not success, Response code is " + httpUrlConnection.getResponseCode());
            }
            try (InputStream inputStream = httpUrlConnection.getInputStream();
                 InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                 BufferedReader reader = new BufferedReader(inputStreamReader)) {
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                }
            }
        } catch (IOException exp) {
            exp.printStackTrace();
        }
        return resultBuffer.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     */
    public static String sendPost(String url, String param) throws IOException {
        StringBuilder result = new StringBuilder();
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        URLConnection conn = realUrl.openConnection();
        // 设置通用的请求属性
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        // 获取URLConnection对象对应的输出流
        PrintWriter out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8));
        // 发送请求参数
        out.print(param);
        // flush输出流的缓冲
        out.flush();
        // 定义BufferedReader输入流来读取URL的响应
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        out.close();
        bufferedReader.close();
        return result.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param targetUrl 发送请求的 URL
     * @param param     请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param encoding  字符编码
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String targetUrl, String param, String encoding, String requestType) {
        String type = "";
        if ("1".equals(requestType)) {
            //表单数据(key,value形式)
            type = "application/x-www-form-urlencoded";
        } else if ("2".equals(requestType)) {
            //xml数据
            type = "application/x-javascript text/xml";
        } else if ("3".equals(requestType)) {
            //json数据
            type = "application/json";
        } else if ("5".equals(requestType)) {
            //文件数据
            type = "multipart/form-data";
        }
        String result = "";
        try {
            byte[] data = param.getBytes(encoding);
            URL url = new URL(targetUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            //application/x-javascript text/xml->xml数据 application/x-javascript->json对象 application/x-www-form-urlencoded->表单数据
            conn.setRequestProperty("Content-Type", type + "; charset=" + encoding);
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            conn.setConnectTimeout(5 * 1000);
            OutputStream outStream = conn.getOutputStream();
            outStream.write(data);
            outStream.flush();
            outStream.close();
            // System.out.println(conn.getResponseCode()); //响应代码 200表示成功
            if (conn.getResponseCode() == 200) {
                InputStream inStream = conn.getInputStream();
                result = getResponseResult(inStream, encoding);
            } else {
                InputStream inStream = conn.getErrorStream();
                String error = getResponseResult(inStream, encoding);
                int code = (int) JSONObject.parseObject(error, Map.class).get("code");
                String errorResult = (String) JSONObject.parseObject(error, Map.class).get("result");
                LOGGER.info("code:" + code + "  errorResult:" + errorResult);
            }
            conn.disconnect();
        } catch (IOException exption) {
            LOGGER.error(exption.getMessage());
        }
        return result;
    }

    private static String getResponseResult(InputStream inStream, String encoding) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = inStream.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        String result = baos.toString(encoding);
        baos.close();
        inStream.close();
        return result;
    }

    /**
     * @param sendUrl    发送POST方法的请求
     * @param textParams 文字数据
     * @param fileParams 文件数据
     * @return
     */
    public static String sendPostFile(String sendUrl, Map<String, String> textParams,
                                      Map<String, MultipartFile> fileParams) throws Exception {
        StringBuilder result = new StringBuilder();
        String boundary = "--------ywkjRedCat" + DateHelper.getTime(new Date());

        URL url = new URL(sendUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        //连接超时为10秒
        conn.setConnectTimeout(10000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        conn.connect();

        PrintStream dataOutputStream = new PrintStream(conn.getOutputStream());
        //文件数据
        Set<String> keySetFile = fileParams.keySet();
        for (String name : keySetFile) {
            MultipartFile file = fileParams.get(name);
            dataOutputStream.print("--" + boundary + "\r\n");
            dataOutputStream.print("Content-Disposition: form-data; name=\"" + name
                    + "\"; filename=\"" + encode(file.getOriginalFilename()) + "\"\r\n");
            dataOutputStream.print("\r\n");
            dataOutputStream.write(getBytes(file));
            dataOutputStream.print("\r\n");
        }
        //文字数据
        Set<String> keySetText = textParams.keySet();
        keySetText.forEach(name -> {
            String value = textParams.get(name);
            dataOutputStream.print("--" + boundary + "\r\n");
            dataOutputStream.print("Content-Disposition: form-data; name=\"" + name + "\"\r\n");
            dataOutputStream.print("\r\n");
            dataOutputStream.print(value + "\r\n");
        });
        //结尾数据
        dataOutputStream.print("--" + boundary + "--" + "\r\n");
        dataOutputStream.print("\r\n");
        dataOutputStream.flush();
        dataOutputStream.close();
        InputStream inStream;
        if (conn.getResponseCode() >= 400) {
            inStream = conn.getErrorStream();
        } else {
            inStream = conn.getInputStream();
        }
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inStream, StandardCharsets.UTF_8));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        bufferedReader.close();
        inStream.close();
        conn.disconnect();
        return result.toString();
    }

    /**
     * 对包含中文的字符串进行转码，此为UTF-8。
     */
    private static String encode(String value) throws Exception {
        return URLEncoder.encode(value, "UTF-8");
    }

    /**
     * 把文件转换成字节数组
     */
    private static byte[] getBytes(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int index;
        while ((index = inputStream.read(bytes)) != -1) {
            out.write(bytes, 0, index);
        }
        inputStream.close();
        return out.toByteArray();
    }
}
