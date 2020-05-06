package com.example.freemoviesapp.binding_adatpers;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.freemoviesapp.util.Constant;

public class BindingAdatpers {
    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext()).load(Constant.Api.BASE_IMAGE_URL + imageUrl).into(view);
    }
}