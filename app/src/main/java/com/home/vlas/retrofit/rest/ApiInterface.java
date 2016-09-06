package com.home.vlas.retrofit.rest;

import com.home.vlas.retrofit.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    //http://api.themoviedb.org/3/movie/top_rated?api_key={YOUR API KEY}}
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);
}
