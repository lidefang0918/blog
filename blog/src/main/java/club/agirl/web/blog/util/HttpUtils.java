package club.agirl.web.blog.util;

import club.agirl.web.blog.exception.YuqueException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
public class HttpUtils {

    private static String doGet(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        addToken(httpGet);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            return getResult(url, response);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new YuqueException("调用Yuque失败，原因:" + e.getMessage() + "; url = " + url);
        } finally {
            closeResource(response, httpClient);
        }
    }

    private static void addToken(HttpRequestBase request) {
        request.setHeader("X-Auth-Token", "12345");
    }

    private static String getResult(String url, CloseableHttpResponse response) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        switch (statusCode) {
            case 200: // 成功
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    return EntityUtils.toString(responseEntity);
                } else {
                    throw new YuqueException("数据不存在; url = " + url);
                }
            case 400: // 请求的参数不正确，或缺少必要信息，请对比文档
                throw new YuqueException("请求的参数不正确，或缺少必要信息，请对比文档; url = " + url);
            case 401: // 需要用户认证的接口用户信息不正确
                throw new YuqueException("需要用户认证的接口用户信息不正确; url = " + url);
            case 403: // 缺少对应功能的权限
                throw new YuqueException("缺少对应功能的权限; url = " + url);
            case 404: // 数据不存在，或未开放
                throw new YuqueException("数据不存在，或未开放; url = " + url);
            case 500: // 服务器异常
                throw new YuqueException("服务器异常; url = " + url);
            default:
                throw new YuqueException("未知错误; url = " + url);
        }
    }

    private static void closeResource(Closeable... resources) {
        if (resources == null) {
            return;
        }
        for (Closeable resource : resources) {
            if (resource == null) {
                continue;
            }
            try {
                resource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> Optional<T> doGetOne(String url, Class<T> clazz) {
        log.info("doGetOne ===> url={}, class={}", url, clazz.getSimpleName());
        String result = doGet(url);
        Optional<T> optional = Optional.ofNullable(JSON.parseObject(getData(result), clazz));
        log.info("doGetOne success");
        return optional;
    }

    public static <T> List<T> doGetArray(String url, Class<T> clazz) {
        log.info("doGetArray ===> url={}, class={}", url, clazz.getSimpleName());
        String result = HttpUtils.doGet(url);
        List<T> array = JSON.parseArray(getData(result), clazz);
        log.info("doGetArray success");
        return array;
    }

    public static String getData(String result) {
        if (StringUtils.isEmpty(result)) {
            throw new YuqueException("数据不存在");
        }
        String data = JSON.parseObject(result).getString("data");
        if (StringUtils.isEmpty(data)) {
            throw new YuqueException("数据不存在;  result = " + result);
        }
        return data;
    }
}
