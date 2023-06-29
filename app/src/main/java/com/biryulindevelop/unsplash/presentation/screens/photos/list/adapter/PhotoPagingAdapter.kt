package com.biryulindevelop.unsplash.presentation.screens.photos.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.biryulindevelop.domain.model.Photo
import com.biryulindevelop.domain.state.ClickableView
import com.biryulindevelop.unsplash.databinding.PhotoViewHolderBinding

class PhotoPagingAdapter(
    private val onClick: (ClickableView, Photo) -> Unit
) : PagingDataAdapter<Photo, PhotoViewHolder>(PhotoDiff()) {

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item) { ClickableView, Photo ->
                onClick(ClickableView, Photo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PhotoViewHolder(
        PhotoViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
}