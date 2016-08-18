package com.home.vlas.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.home.vlas.retrofit.adapter.MoviesAdapter;
import com.home.vlas.retrofit.model.Movie;
import com.home.vlas.retrofit.model.MovieResponse;
import com.home.vlas.retrofit.rest.ApiClient;
import com.home.vlas.retrofit.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "2ccce225a05a12509c458d340736012d";
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Need API KEY", Toast.LENGTH_SHORT).show();
            return;
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                spinner.setVisibility(View.INVISIBLE);
                List<Movie> movieList = response.body().getResults();
                int statusCode = response.code();
                recyclerView.setAdapter(new MoviesAdapter(movieList, R.layout.list_item_movie, getApplicationContext()));
                Log.d(TAG, "============================");
                Log.d(TAG, "Code: " + statusCode + " | Number of movies recieved: " + movieList.size());
                Log.d(TAG, "============================");
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(TAG, "============================");
                Log.e(TAG, t.toString());
                Log.d(TAG, "============================");
            }
        });
    }
}
