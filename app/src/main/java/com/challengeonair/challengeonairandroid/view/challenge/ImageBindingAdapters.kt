package com.challengeonair.challengeonairandroid.view.challenge

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.challengeonair.challengeonairandroid.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    url?.let {
        Glide.with(view.context)
            .load(it)
            .into(view)
    }
}

@BindingAdapter("isVisibleIfHost")
fun isVisibleIfHost(view: View, isHost: Boolean) {
    view.visibility = if (isHost) View.VISIBLE else View.GONE
}

@BindingAdapter("isVisibleIfNotHost")
fun isVisibleIfNotHost(view: View, isHost: Boolean) {
    view.visibility = if (!isHost) View.VISIBLE else View.GONE
}