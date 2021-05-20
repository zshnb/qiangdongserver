package com.qiangdong.reader.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.qiangdong.reader.exception.HttpException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import okhttp3.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <p> Http请求工具类 </p>
 *
 */
@Component
public class HttpUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    public HttpUtil(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    /**
     * OkHttp请求客户端
     */
    private final OkHttpClient okHttpClient;

    private final static String XML_ROOT_NAME = "xml";

    /**
     * Get请求
     *
     * @param url       请求地址
     * @param headers   请求头
     * @param mediaType 请求媒体类型 {@link MediaType}
     * @param params    请求参数
     * @return String
     */
    public String get(String url, Map<String, String> headers,
                             MediaType mediaType, Map<String, Object> params) {
        try (Response response = execute(url, Method.GET, headers, mediaType, params);) {
            return response.body().string();
        } catch (IOException e) {
            LOGGER.error(String.format("Get请求失败,获取返回结果失败，请求地址：%s, ", url), e);
            throw new HttpException("Http请求失败");
        }
    }

    /**
     * Post请求
     *
     * @param url       请求地址
     * @param headers   请求头
     * @param mediaType 请求媒体类型 {@link MediaType}
     * @param params    请求参数
     * @return String
     */
    public String post(String url, Map<String, String> headers,
                              MediaType mediaType, Map<String, Object> params) {
        try (Response response = execute(url, Method.POST, headers, mediaType, params);) {
            return response.body().string();
        } catch (IOException e) {
            LOGGER.error(String.format("Post请求失败,获取返回结果失败，请求地址：%s", url), e);
            throw new HttpException("Http请求失败");
        }
    }

    /**
     * Put请求
     *
     * @param url       请求地址
     * @param headers   请求头
     * @param mediaType 请求媒体类型 {@link MediaType}
     * @param params    请求参数
     * @return String
     */
    public String put(String url, Map<String, String> headers,
                             MediaType mediaType, Map<String, Object> params) {
        try (Response response = execute(url, Method.PUT, headers, mediaType, params);) {
            return response.body().string();
        } catch (IOException e) {
            LOGGER.error(String.format(String.format("Put请求失败,获取返回结果失败，请求地址：%s", url), e));
            throw new HttpException("Http请求失败");
        }
    }

    /**
     * Put请求
     *
     * @param url       请求地址
     * @param headers   请求头
     * @param mediaType 请求媒体类型 {@link MediaType}
     * @param params    请求参数
     * @return String
     */
    public String delete(String url, Map<String, String> headers,
                                MediaType mediaType, Map<String, Object> params) {
        try (Response response = execute(url, Method.DELETE, headers, mediaType, params);) {
            return response.body().string();
        } catch (IOException e) {
            LOGGER.error(String.format("Delete请求失败,获取返回结果失败，请求地址：%s", url), e);
            throw new HttpException("Http请求失败");
        }
    }

    /**
     * 执行http请求
     *
     * @param url       请求地址
     * @param method    请求方法 {@link Method}
     * @param headers   请求头
     * @param mediaType 请求媒体类型 {@link MediaType}
     * @param params    请求参数
     * @return Response
     */
    public Response execute(String url, Method method, Map<String, String> headers,
                                   MediaType mediaType, Map<String, Object> params) {
        try {
            Request.Builder builder = new Request.Builder();
            builder.url(url);

            //组装请求头
            if (!Objects.isNull(headers) && !headers.isEmpty()) {
                headers.forEach(builder::addHeader);
            }

            //组装请求参数
            if (Objects.equals(method, Method.GET)) {
                if (!Objects.isNull(params) && !params.isEmpty()) {
                    String queryParams = params.entrySet().stream()
                        .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
                        .collect(Collectors.joining("&"));
                    url = String.format("%s%s%s", url, url.contains("?") ? "&" : "?", queryParams);
                    builder.url(url).get();
                }
            } else {
                String data = "";

                //默认请求参数为Json
                if (!Objects.isNull(params) && !params.isEmpty()) {
                    data = JSONUtil.toJsonPrettyStr(params);
                }

                //如果为Form则转换
                if (Objects.equals(mediaType, MediaType.FORM)) {
                    data = getFormData(params);
                }
                //如果为XML则转换
                if (Objects.equals(mediaType, MediaType.XML)) {
                    data = XmlUtil.mapToXmlStr(params, XML_ROOT_NAME);
                }

                RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse(mediaType.getValue()), data);
                builder.method(method.getValue(), requestBody);
            }

            return okHttpClient.newCall(builder.build()).execute();
        } catch (Exception e) {
            LOGGER.error(String.format("Http请求失败,参数[%s]", url), e);
            throw new HttpException("Http请求失败");
        }
    }

    /**
     * 获取表单数据
     *
     * @param params 参数
     * @return url编码数据
     */
    private String getFormData(Map<String, Object> params) {
        if (Objects.isNull(params) || params.isEmpty()) {
            return "";
        }

        List<BasicNameValuePair> paramList = Lists.newLinkedList();
        params.forEach((key, value) -> {
            String s = StrUtil.isBlank(value.toString()) ? "" : value.toString();
            paramList.add(new BasicNameValuePair(key, s));
        });

        return URLEncodedUtils.format(paramList, CharsetUtil.defaultCharset());
    }

    /**
     * <p> 请求方法 </p>
     *
     * @author Tortoise
     * @since 2019-04-13
     */
    public enum Method {

        /**
         * GET
         */
        GET("GET"),

        /**
         * POST
         */
        POST("POST"),

        /**
         * PUT
         */
        PUT("PUT"),

        /**
         * DELETE
         */
        DELETE("DELETE"),
        ;

        private String value;

        Method(String value) {
            this.value = value;
        }

        /**
         * 获取枚举对象的具体值
         *
         * @return 具体值
         */
        public String getValue() {
            return value;
        }

    }

    /**
     * Http请求媒体类型
     *
     * @author Tortoise
     * @since 2018-08-23
     */
    public enum MediaType {

        /**
         * JSON
         */
        JSON("application/json; charset=utf-8"),

        /**
         * XML
         */
        XML("application/xml; charset=utf-8"),

        /**
         * FORM
         */
        FORM("application/x-www-form-urlencoded; charset=utf-8"),
        ;

        private String value;

        MediaType(String value) {
            this.value = value;
        }

        /**
         * 获取枚举对象的具体值
         *
         * @return 具体值
         */
        public String getValue() {
            return value;
        }

    }


    /**
     * jwebplat-sy 访问，设置连接的超时时间
     */
    public String getByUrlConnection(String path) {
        try {
            URL url = new URL(path.trim());
            //打开连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(60000);
            urlConnection.setReadTimeout(60000);
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            try {

                if (200 == urlConnection.getResponseCode()) {
                    //得到输入流
                    is = urlConnection.getInputStream();
                    baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while (-1 != (len = is.read(buffer))) {
                        baos.write(buffer, 0, len);
                        baos.flush();
                    }
                    return baos.toString("utf-8");
                }
            } catch (Exception e) {
                throw new HttpException(String.format("Http请求失败"));
            } finally {
                if (baos != null) {
                    baos.close();
                }
                if (is != null) {
                    is.close();
                }
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        } catch (Exception e) {
            LOGGER.error(String.format("Http请求失败,参数[%s]", e));
            throw new HttpException("Http请求失败");
        }

        return null;
    }

    public String getByUrlConnection(String path, Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        sb.append(path).append("?");
        for (String key : map.keySet()) {
            sb.append(key).append("=").append(map.get(key)).append("&");

        }
        return getByUrlConnection(sb.toString());
    }


}
