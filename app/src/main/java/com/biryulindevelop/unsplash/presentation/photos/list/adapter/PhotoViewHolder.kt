package com.biryulindevelop.unsplash.presentation.photos.list.adapter

import androidx.recyclerview.widget.RecyclerView
import com.biryulindevelop.unsplash.data.state.ClickableView
import com.biryulindevelop.unsplash.databinding.PhotoViewHolderBinding
import com.biryulindevelop.unsplash.domain.model.Photo
import com.biryulindevelop.unsplash.tools.loadImage

class PhotoViewHolder(private val binding: PhotoViewHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Photo, onClick: (ClickableView, item: Photo) -> Unit) {

        binding.photoImgView.setOnClickListener {
            onClick(ClickableView.PHOTO, item)
        }
        binding.isLikedButton.setOnClickListener {
            onClick(ClickableView.LIKE, item)
        }

        binding.currentLikesTextView.text = item.likes.toString()
        binding.isLikedButton.isSelected = item.likedByUser

        binding.photoImgView.loadImage(item.urlsSmall)
        binding.authorAvatarImgView.loadImage(item.userAvatar)

        binding.authorNameTextView.text = item.userName
    }
}

