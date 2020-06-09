package com.example.androidfoodiesapp.Interface;


import android.view.View;

public interface ItemClickListener {
    void onClick(View view, int position, boolean isLongClick);
}