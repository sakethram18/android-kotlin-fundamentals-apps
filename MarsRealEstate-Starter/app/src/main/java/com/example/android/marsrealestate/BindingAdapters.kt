/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.marsrealestate.network.MarsProperty
import com.example.android.marsrealestate.overview.MarsApiStatus
import com.example.android.marsrealestate.overview.MarsApiStatus.*
import com.example.android.marsrealestate.overview.PhotoGridAdapter

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()

        Glide.with(imageView.context)
                .load(imageUri)
                .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                .into(imageView)
    }
}

@BindingAdapter("listData")
fun bindList(recyclerView: RecyclerView, data: List<MarsProperty>?) {
    data?.let {
        val adapter = recyclerView.adapter as PhotoGridAdapter
        adapter.submitList(data)
    }
}

@BindingAdapter("marsApiStatus")
fun bindStatusImage(imageView: ImageView, status: MarsApiStatus?) {
    status?.let {
        when(status) {
            LOADING -> {
                imageView.visibility = View.VISIBLE
                imageView.setImageResource(R.drawable.loading_animation)
            }
            SUCCESS -> {
                imageView.visibility = View.GONE
            }
            ERROR -> {
                imageView.visibility = View.VISIBLE
                imageView.setImageResource(R.drawable.ic_connection_error)
            }
        }
    }
}

