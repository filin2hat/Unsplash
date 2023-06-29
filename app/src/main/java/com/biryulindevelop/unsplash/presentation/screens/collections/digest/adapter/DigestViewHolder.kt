package com.biryulindevelop.unsplash.presentation.screens.collections.digest.adapter

import androidx.recyclerview.widget.RecyclerView
import com.biryulindevelop.unsplash.R
import com.biryulindevelop.unsplash.databinding.DigestViewHolderBinding
import com.biryulindevelop.unsplash.domain.model.Digest
import com.biryulindevelop.unsplash.presentation.utils.imgLoader

class DigestViewHolder(
    private val binding: DigestViewHolderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Digest, onClick: (item: Digest) -> Unit) {
        binding.root.setOnClickListener {
            onClick(item)
        }
        with(binding) {
            previewImgView.imgLoader(item.previewPhoto)
            authorAvatarImgView.imgLoader(item.userProfileImage)
            authorNameTextView.text = item.userUsername
            totalPhotos.text =
                itemView.context.resources.getQuantityString(
                    R.plurals.total_photos, item.totalPhotos, item.totalPhotos
                )
            collectionTitle.text = item.title
        }
    }
}

