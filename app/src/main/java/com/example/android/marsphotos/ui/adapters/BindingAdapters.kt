package com.example.android.marsphotos.ui.adapters

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.android.marsphotos.R
import com.example.android.marsphotos.network.MarsPhoto
import com.example.android.marsphotos.overview.MarsApiStatus
import com.example.android.marsphotos.overview.adapter.PhotoGridAdapter

/**
 * Binding Adapters are annotated methods used to create custom setters for custom properties of your view.
 * <code>
    <ImageView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:imageUrl="@{product.imageUrl}"/>
 * </code>

 * Example of a Binding Adapter:
 * <code>
    @BindingAdapter("imageUrl")
    fun bindImage(imgView: ImageView, imgUrl: String?) {
        imgUrl?.let {
            // Load the image in the background using Coil.
        }
    }
 * </code>
*/
@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        imageView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, photos: List<MarsPhoto>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(photos)
}

@BindingAdapter("marsApiStatus")
fun bindStatus(imageView: ImageView, marsApiStatus: MarsApiStatus?) {
    when(marsApiStatus) {
        MarsApiStatus.LOADING -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.loading_img)
        }
        MarsApiStatus.ERROR -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.ic_connection_error)
        }
        MarsApiStatus.DONE -> {
            imageView.visibility = View.GONE
        }
    }
}
