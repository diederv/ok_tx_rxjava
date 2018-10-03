package com.okit.rxjava;


import java.text.DateFormat;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.okit.client.Authorisation;
import com.okit.client.Transaction;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class OkServiceFactory {

    private static OkService instance;
    
    private final static String BASE_URL = "https://secure.okit.com";

    public static OkService getInstance(String secretKey) {
        if (OkServiceFactory.instance == null) {

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            BasicAuthInterceptor authInterceptor = new BasicAuthInterceptor(secretKey, "" );

            Builder builder = new OkHttpClient.Builder();
            
            // important for the ?sync=true calles:
            builder.readTimeout(300, TimeUnit.SECONDS);
            builder.writeTimeout(300, TimeUnit.SECONDS);
            
            OkHttpClient okHttpclient = builder
                    .addInterceptor(new RequestHeaderInterceptor())
                    .addInterceptor(authInterceptor)
                    .addInterceptor(loggingInterceptor)                    
                    .build();     
                        
            Gson gson = new GsonBuilder()
            	     .registerTypeAdapter(Transaction.class, 	new TransactionTypeAdapter())         
            	     .registerTypeAdapter(Authorisation.class, 	new AuthorisationTypeAdapter())
            	     .enableComplexMapKeySerialization()
            	     .serializeNulls()
            	     .setDateFormat(DateFormat.LONG)
            	     .setLenient()
            	     .setPrettyPrinting()
            	     .setVersion(1.0)
            	     .create();
            
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(OkServiceFactory.BASE_URL)
                    .client(okHttpclient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            OkServiceFactory.instance = retrofit.create(OkService.class);
        }
        return OkServiceFactory.instance;
    }   


}
