package com.example.service;

import com.example.model.Cine;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CineApi {

    @GET("pam/cine.json")
    Call<Cine> getCine();

}
