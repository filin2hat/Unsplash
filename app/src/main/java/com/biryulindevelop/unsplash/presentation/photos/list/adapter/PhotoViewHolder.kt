package com.biryulindevelop.unsplash.presentation.photos.list.adapter

import androidx.recyclerview.widget.RecyclerView
import com.biryulindevelop.unsplash.data.state.ClickableView
import com.biryulindevelop.unsplash.databinding.PhotoViewHolderBinding
import com.biryulindevelop.unsplash.domain.model.Photo
import com.biryulindevelop.unsplash.utils.imgLoader

class PhotoViewHolder(private val binding: PhotoViewHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Photo, onClick: (ClickableView, item: Photo) -> Unit) {
        with(binding) {
            photoImgView.setOnClickListener {
                onClick(ClickableView.PHOTO, item)
            }
            isLikedButton.setOnClickListener {
                onClick(ClickableView.LIKE, item)
            }
            currentLikesTextView.text = item.likes.toString()
            isLikedButton.isSelected = item.likedByUser
            photoImgView.imgLoader(item.urlsSmall)
            authorAvatarImgView.imgLoader(item.userAvatar)
            authorNameTextView.text = item.userName
        }
    }
}

