package com.biryulindevelop.unsplash.presentation.collections.adapter

import androidx.recyclerview.widget.RecyclerView
import com.biryulindevelop.unsplash.R
import com.biryulindevelop.unsplash.databinding.DigestViewHolderBinding
import com.biryulindevelop.unsplash.domain.model.Digest
import com.biryulindevelop.unsplash.tools.loadImage

class DigestViewHolder(private val binding: DigestViewHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Digest, onClick: (item: Digest) -> Unit) {

        binding.root.setOnClickListener {
            onClick(item)
        }

        binding.previewImgView.loadImage(item.previewPhoto)
        binding.authorAvatarImgView.loadImage(item.userProfileImage)
        binding.authorNameTextView.text = item.userUsername
        binding.totalPhotos.text =
            itemView.context.resources.getQuantityString(
                R.plurals.total_photos, item.totalPhotos, item.totalPhotos
            )
        binding.collectionTitle.text = item.title

    }
}

