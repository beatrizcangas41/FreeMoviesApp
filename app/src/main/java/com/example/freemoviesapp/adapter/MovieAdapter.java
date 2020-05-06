package com.example.freemoviesapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freemoviesapp.R;
import com.example.freemoviesapp.databinding.MatchCalenderItemBinding;
import com.example.freemoviesapp.model.Movie;
import com.example.freemoviesapp.view.DetailsActivity;

import java.util.List;



public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movieList ;
    private Context context;

    public MovieAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MatchCalenderItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.match_calender_item, parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final Movie movie = movieList.get(position);
        holder.binding.setMovie(movie);
        holder.bind(movie);
        holder.binding.setItemClick(f -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("Movie", movie);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private MatchCalenderItemBinding binding;

        public MovieViewHolder(MatchCalenderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Object obj) {
            binding.executePendingBindings();
        }
    }
}