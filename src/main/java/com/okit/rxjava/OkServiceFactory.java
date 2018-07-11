package com.okit.rxjava;


import java.text.DateFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.okit.transaction.Transaction;
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
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

            BasicAuthInterceptor authInterceptor = new BasicAuthInterceptor(secretKey, "" );

            OkHttpClient okHttpclient = new OkHttpClient.Builder()
                    .addInterceptor(new RequestHeaderInterceptor())
                    .addInterceptor(authInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build();
                        
            Gson gson = new GsonBuilder()
            	     .registerTypeAdapter(Transaction.class, new TransactionTypeAdapter())            	     
            	     .enableComplexMapKeySerialization()
            	     .serializeNulls()
            	     .setDateFormat(DateFormat.LONG)
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
