package com.example.githubrepo.network;

import com.example.githubrepo.model.GitRepo;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("repositories")
    Observable<GitRepo> getRepo(
            @Query("q")  String q,
            @Query("sort") String sort,
            @Query("order") String order
    );
}
