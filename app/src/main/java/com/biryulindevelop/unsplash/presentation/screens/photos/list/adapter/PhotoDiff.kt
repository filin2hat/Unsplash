package com.biryulindevelop.unsplash.presentation.screens.photos.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.biryulindevelop.domain.model.Photo

class PhotoDiff : DiffUtil.ItemCallback<Photo>() {

    override fun areItemsTheSame(oldItem: Photo, newItem: Photo) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo) =
        oldItem == newItem
}