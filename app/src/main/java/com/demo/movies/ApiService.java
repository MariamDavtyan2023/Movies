package com.demo.movies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie?token=MEB886V-E2W41M8-HCR6N80-5E199Q9&field=rating.imdb&search=7-10&sortField=votes.imdb&sortType=-1&limit=30")
    Single<MovieResponse> loadMovies(@Query("page") int page);

    @GET("movie?token=MEB886V-E2W41M8-HCR6N80-5E199Q9&field=id")
    Single<TrailerResponse> loadTrailers(@Query("search") int id);

    @GET("review?token=MEB886V-E2W41M8-HCR6N80-5E199Q9&field=movieId&limit=10")
    Single<ReviewResponse> loadReviews(@Query("search") int id);
}
