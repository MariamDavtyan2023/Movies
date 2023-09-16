package com.demo.movies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;

public interface ApiService {

    @GET("v1.3/movie?token=MEB886V-E2W41M8-HCR6N80-5E199Q9&field=rating.imdb&search=7-10&sortField=votes.imdb&sortType=-1&limit=30")
    Single<MovieResponse> loadMovies(@Query("page") int page);

    @GET("v1.3/movie/{id}?token=MEB886V-E2W41M8-HCR6N80-5E199Q9")
    Single<TrailerResponse> loadTrailers(@Path("id") int id);

    @GET("v1/review?token=MEB886V-E2W41M8-HCR6N80-5E199Q9")
    Single<ReviewResponse> loadReviews(@Query("movieId") int id);
}
