package com.okit.rxjava;


import java.net.InetSocketAddress;
import java.net.Proxy;
import java.text.DateFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.okit.client.Authorisation;
import com.okit.client.LineItem;
import com.okit.client.Transaction;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class OkServiceFactory {

    private static OkService instance;

    public static OkService getInstance(String baseUrl, String secretKey) {
        if (OkServiceFactory.instance == null) {

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            BasicAuthInterceptor authInterceptor = new BasicAuthInterceptor(secretKey, "" );

            OkHttpClient okHttpclient = new OkHttpClient.Builder()
                    .addInterceptor(new RequestHeaderInterceptor())
                    .addInterceptor(authInterceptor)
                    .addInterceptor(loggingInterceptor)
//                    .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888)))
                    .build();
                        
            Gson gson = new GsonBuilder()
            	     .registerTypeAdapter(Transaction.class, 	new TransactionTypeAdapter())         
            	     .registerTypeAdapter(Authorisation.class, 	new AuthorisationTypeAdapter())
//            	     .registerTypeAdapter(LineItem.class, 		new LineItemTypeAdapter())
            	     .enableComplexMapKeySerialization()
            	     .serializeNulls()
            	     .setDateFormat(DateFormat.LONG)
            	     .setLenient()
            	     .setPrettyPrinting()
            	     .setVersion(1.0)
            	     .create();
            
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpclient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            OkServiceFactory.instance = retrofit.create(OkService.class);
        }
        return OkServiceFactory.instance;
    }   


}
