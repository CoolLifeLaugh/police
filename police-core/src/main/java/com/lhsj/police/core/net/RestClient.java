package com.lhsj.police.core.net;

import com.lhsj.police.core.json.ReJsons;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class RestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);

    private static OkHttpClient CLIENT;

    private static class RestClientLoader {
        private static final RestClient REST_CLIENT = new RestClient();
    }

    private RestClient() {
        CLIENT = new OkHttpClient.Builder()
                .connectTimeout(3000, TimeUnit.MILLISECONDS)
                .readTimeout(3000, TimeUnit.MILLISECONDS)
                .writeTimeout(3000, TimeUnit.MILLISECONDS)
                .build();
    }

    public static RestClient instance() {
        return RestClientLoader.REST_CLIENT;
    }

    private static Headers headers(Map<String, Object> params) {
        Headers.Builder headerBuilder = new Headers.Builder();

        Optional.ofNullable(params).map(Map::entrySet)
                .orElse(new HashSet<>())
                .stream()
                .filter(e -> e.getKey() != null)
                .filter(e -> e.getValue() != null)
                .forEach(e -> headerBuilder.add(e.getKey(), e.getValue().toString()));

        return headerBuilder.build();
    }

    private static String params(Map<String, Object> params) {
        return Optional.ofNullable(params).map(Map::entrySet)
                .orElse(new HashSet<>())
                .stream()
                .filter(e -> e.getKey() != null)
                .filter(e -> e.getValue() != null)
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&", "?", ""));
    }

    private static RequestBody postBody(Map<String, Object> params) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();

        Optional.ofNullable(params).map(Map::entrySet)
                .orElse(new HashSet<>())
                .stream()
                .filter(e -> e.getKey() != null)
                .filter(e -> e.getValue() != null)
                .forEach(e -> formBodyBuilder.add(e.getKey(), e.getValue().toString()));

        return formBodyBuilder.build();
    }

    public static <T> T parseResponse(Response response, Class<T> clazz) {
        return Optional.ofNullable(response)
                .filter(Response::isSuccessful)
                .map(Response::body)
                .map(bytesMapper)
                .map(e -> new String(e, StandardCharsets.UTF_8))
                .map(e -> ReJsons.string2Obj(e, clazz))
                .orElse(null);
    }

    private static Function<ResponseBody, byte[]> bytesMapper = e -> {
        try {
            return e.bytes();
        } catch (IOException ioe) {
            LOGGER.error("bytes error: ", ioe);
        }
        return null;
    };

    // ----------------------------------------------------------------------------------------

    public <T> T get(String url, Class<T> clazz) {
        return get(url, null, null, clazz);
    }

    public <T> T getByHeaders(String url, Map<String, Object> headers, Class<T> clazz) {
        return get(url, null, headers, clazz);
    }

    public <T> T getByParams(String url, Map<String, Object> params, Class<T> clazz) {
        return get(url, params, null, clazz);
    }

    public <T> T get(String url, Map<String, Object> params, Map<String, Object> headers, Class<T> clazz) {
        Request request = new Request.Builder()
                .url(url + params(params))
                .headers(headers(headers))
                .get()
                .build();

        Call call = CLIENT.newCall(request);

        try {
            return parseResponse(call.execute(), clazz);
        } catch (Throwable e) {
            LOGGER.error("RestClient get error: ", e);
        }
        return null;
    }

    // ----------------------------------------------------------------------------------------

    public void getAsync(String url, Callback callback) {
        getAsync(url, null, null, callback);
    }

    public void getAsyncByHeaders(String url, Map<String, Object> headerParams, Callback callback) {
        getAsync(url, null, headerParams, callback);
    }

    public void getAsyncByParams(String url, Map<String, Object> urlParams, Callback callback) {
        getAsync(url, urlParams, null, callback);
    }

    public void getAsync(String url, Map<String, Object> urlParams, Map<String, Object> headerParams, Callback callback) {
        Request request = new Request.Builder()
                .url(url + params(urlParams))
                .headers(headers(headerParams))
                .get()
                .build();

        Call call = CLIENT.newCall(request);

        try {
            call.enqueue(callback);
        } catch (Exception e) {
            LOGGER.error("RestClient getAsync error: ", e);
        }
    }

    // -------------------------------------- post json --------------------------------------------------

    public <T> T post(String url, Object body, Class<T> clazz) {
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json"), ReJsons.obj2String(body));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Call call = CLIENT.newCall(request);

        try {
            return parseResponse(call.execute(), clazz);
        } catch (Exception e) {
            LOGGER.error("RestClient post error: ", e);
        }
        return null;
    }

    public <T> T post(String url, Object body, Map<String, Object> headers, Class<T> clazz) {
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json"), ReJsons.obj2String(body));
        Request request = new Request.Builder()
                .url(url)
                .headers(headers(headers))
                .post(requestBody)
                .build();

        Call call = CLIENT.newCall(request);

        try {
            return parseResponse(call.execute(), clazz);
        } catch (Exception e) {
            LOGGER.error("RestClient post error: ", e);
        }
        return null;
    }

    // ----------------------------------------------------------------------------------------

    public void postAsync(String url, Object body, Callback callback) {
        postAsync(url, body, null, callback);
    }

    public void postAsync(String url, Object body, Map<String, Object> headers, Callback callback) {
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json"), ReJsons.obj2String(body));
        Request request = new Request.Builder()
                .url(url)
                .headers(headers(headers))
                .post(requestBody)
                .build();

        Call call = CLIENT.newCall(request);

        try {
            call.enqueue(callback);
        } catch (Exception e) {
            LOGGER.error("RestClient postAsync error: ", e);
        }
    }

    // ---------------------------------------- post form ------------------------------------------------

    public <T> T post(String url, Map<String, Object> body, Class<T> clazz) {
        return post(url, body, null, clazz);
    }

    public <T> T post(String url, Map<String, Object> body, Map<String, Object> headers, Class<T> clazz) {
        Request request = new Request.Builder()
                .url(url)
                .headers(headers(headers))
                .post(postBody(body))
                .build();

        Call call = CLIENT.newCall(request);

        try {
            return parseResponse(call.execute(), clazz);
        } catch (Exception e) {
            LOGGER.error("RestClient post error: ", e);
        }
        return null;
    }

    // ------------------------------------- post async form -------------------------------------------------

    public void postAsync(String url, Map<String, Object> body, Callback callback) {
        postAsync(url, body, null, callback);
    }

    public void postAsync(String url, Map<String, Object> body, Map<String, Object> headers, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .headers(headers(headers))
                .post(postBody(body))
                .build();

        Call call = CLIENT.newCall(request);

        try {
            call.enqueue(callback);
        } catch (Exception e) {
            LOGGER.error("RestClient postAsync error: ", e);
        }
    }

    // ------------------------------------- callback -------------------------------------------------

    public static final Callback IGNORE_CALLBACK = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
        }

        @Override
        public void onResponse(Call call, Response response) {
        }
    };
}
