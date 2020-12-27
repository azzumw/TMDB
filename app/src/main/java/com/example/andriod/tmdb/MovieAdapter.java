package com.example.andriod.tmdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    final private ListItemClickListener listItemClickListener;

    private List<Movie> moviesList;

    public MovieAdapter (List<Movie> list, ListItemClickListener pListItemListener){
        this.moviesList = list;
        this.listItemClickListener = pListItemListener;
    }

    public void addAll(List<Movie> list) {
        moviesList = list;
    }

    public void clear(){
        moviesList.clear();
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem,parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        String baseImagePath ="http://image.tmdb.org/t/p/w185/";
        String currentImagePath = moviesList.get(position).getImage();

        Picasso.get().load(baseImagePath.concat(currentImagePath)).placeholder(R.drawable.ic_launcher_background).into(holder.getImageView());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    public  class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView imageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
             imageView = itemView.findViewById(R.id.moviename_iv);
             itemView.setOnClickListener(this);
        }


        public ImageView getImageView() {
            return imageView;
        }

        @Override
        public void onClick(View view) {

            int clickedPosition = getAdapterPosition();
            listItemClickListener.onListItemClickListener(moviesList.get(clickedPosition));
        }
    }
}
