package com.biryulindevelop.unsplash.presentation.screens.collections.digest.adapter

import androidx.recyclerview.widget.DiffUtil
import com.biryulindevelop.unsplash.domain.model.Digest

class DigestDiff : DiffUtil.ItemCallback<Digest>() {

    override fun areItemsTheSame(oldItem: Digest, newItem: Digest) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Digest, newItem: Digest) =
        oldItem == newItem
}