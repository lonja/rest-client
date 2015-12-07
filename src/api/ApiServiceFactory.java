package api;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class ApiServiceFactory {

    private static final String API_BASIC_URL = "http://localhost:3000/api/";

    public static API createService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASIC_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API service;
        return service = retrofit.create(API.class);
    }
}