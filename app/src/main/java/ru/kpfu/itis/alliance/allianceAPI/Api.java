package ru.kpfu.itis.alliance.allianceAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private static final Api ourInstance = new Api();

    private AllianceAPI api;

    private Api() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://afcon.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(AllianceAPI.class);
    }

    public static Api getInstance() {
        return ourInstance;
    }

    public AllianceAPI getApi() {
        return api;
    }
}
