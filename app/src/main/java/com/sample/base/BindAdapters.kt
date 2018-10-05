package com.sample.base

import android.graphics.drawable.ColorDrawable
import android.webkit.URLUtil
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (URLUtil.isValidUrl(imageUrl)) {
        val options = RequestOptions()
            .centerCrop()
            .placeholder(null)
            .error(ColorDrawable(ColorGenerator.MATERIAL.getColor(imageUrl)))
        Glide.with(view.context)
            .load(imageUrl)
            .apply(options)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    } else {
        view.setImageDrawable(ColorDrawable(ColorGenerator.MATERIAL.randomColor))
    }
}
