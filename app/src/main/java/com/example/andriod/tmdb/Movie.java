package com.example.andriod.tmdb;

@SuppressWarnings({"ALL", "unused"})
class Movie {

    private final String movieName;
    private final String overView;
    private final String image;

    public Movie(String title, String mOverView, String imagePath) {
        this.movieName = title;
        this.overView = mOverView;
        this.image = imagePath;
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
}
