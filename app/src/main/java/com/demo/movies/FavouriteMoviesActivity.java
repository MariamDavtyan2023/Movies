package com.demo.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

public class FavouriteMoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movies);
        RecyclerView recyclerViewFavMovies = findViewById(R.id.recyclerviewFavMovies);
        recyclerViewFavMovies.setLayoutManager(new GridLayoutManager(this, 2));
        MoviesAdapter moviesAdapter = new MoviesAdapter();
        recyclerViewFavMovies.setAdapter(moviesAdapter);
        moviesAdapter.setOnMovieClickListener(new MoviesAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                Intent intent = MovieDetailActivity.newIntent(FavouriteMoviesActivity.this, movie);
                startActivity(intent);
            }
        });
        FavouriteMoviesViewModel viewModel = new ViewModelProvider(this).get(FavouriteMoviesViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesAdapter.setMovies(movies);
            }
        });
    }
    public static Intent newIntent(Context context){
        return new Intent(context, FavouriteMoviesActivity.class);
    }
}