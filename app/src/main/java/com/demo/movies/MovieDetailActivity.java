package com.demo.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MovieDetailActivity extends AppCompatActivity {

    private static final String EXTRA_MOVIE = "movie";
    private MovieDetailViewModel viewModel;

    private ImageView imageViewMovie;
    private TextView textViewTitle;
    private TextView textViewYear;
    private TextView textViewDescription;
    private RecyclerView recyclerViewTrailers;
    private RecyclerView recyclerViewReviews;
    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;
    private ImageView imageViewStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initViews();
        trailersAdapter = new TrailersAdapter();
        reviewsAdapter = new ReviewsAdapter();
        recyclerViewReviews.setAdapter(reviewsAdapter);
        recyclerViewTrailers.setAdapter(trailersAdapter);
        viewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);

        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(imageViewMovie);
        textViewTitle.setText(movie.getName());
        textViewYear.setText(String.valueOf(movie.getYear()));
        textViewDescription.setText(movie.getDescription());
        viewModel.loadTrailers(movie.getId());
        viewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                trailersAdapter.setTrailers(trailers);
            }
        });
        trailersAdapter.setOnTrailerClickListener(new TrailersAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(Trailer trailer) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getUrl()));
                startActivity(intent);
            }
        });
        viewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                reviewsAdapter.setReviews(reviews);
            }
        });
        viewModel.loadReviews(movie.getId());
        Drawable starOff = ContextCompat.getDrawable(MovieDetailActivity.this, android.R.drawable.star_big_off);
        Drawable starOn = ContextCompat.getDrawable(MovieDetailActivity.this, android.R.drawable.star_big_on);
        viewModel.getFavouriteMovie(movie.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movieFromDb) {
                if (movieFromDb == null){
                    imageViewStar.setImageDrawable(starOff);
                    imageViewStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.insertMovie(movie);
                        }
                    });
                } else {
                    imageViewStar.setImageDrawable(starOn);
                    imageViewStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.removeMovie(movie.getId());
                        }
                    });
                }
            }
        });
    }

    private void initViews(){
        imageViewMovie = findViewById(R.id.imageViewMovie);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewYear = findViewById(R.id.textViewYear);
        textViewDescription = findViewById(R.id.textViewDescription);
        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
        imageViewStar = findViewById(R.id.imageViewStar);
    }

    public static Intent newIntent(Context context, Movie movie){
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }
}