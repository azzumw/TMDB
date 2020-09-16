package com.example.andriod.tmdb;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings({"ALL", "unused"})
class Movie implements Parcelable {

    private final String movieName;
    private final String overView;
    private final String image;
    private final String releaseDate;
    private int movie_id;
    private int vote_avg;

    public Movie(String title, String mOverView, String imagePath, int id,String releaseDate,int vote_avg) {
        this.movieName = title;
        this.overView = mOverView;
        this.image = imagePath;
        this.movie_id = id;
        this.releaseDate = releaseDate;
        this.vote_avg = vote_avg;
    }

    private Movie(Parcel in){
        movieName = in.readString();
        overView = in.readString();
        image = in.readString();
        movie_id = in.readInt();
        releaseDate = in.readString();
        vote_avg = in.readInt();
    }

    public String getMovieName() {
        return movieName;
    }

    public String getOverView() {
        return overView;
    }

    public String getImage() {
        return image;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public int getVote_avg() {
        return vote_avg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieName);
        parcel.writeString(overView);
        parcel.writeString(image);
        parcel.writeInt(movie_id);
        parcel.writeString(releaseDate);
        parcel.writeInt(vote_avg);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }

    };
}
