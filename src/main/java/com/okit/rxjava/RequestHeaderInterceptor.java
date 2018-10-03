package com.okit.rxjava;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestHeaderInterceptor implements Interceptor {
    
    public Response intercept(Interceptor.Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("content-type", "application/json");
        return chain.proceed(builder.build());
    }
}

