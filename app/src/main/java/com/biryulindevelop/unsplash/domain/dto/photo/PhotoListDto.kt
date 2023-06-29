package com.biryulindevelop.unsplash.domain.dto.photo

import com.biryulindevelop.unsplash.domain.entity.PhotoEntity

class PhotoListDto : ArrayList<PhotoDto>() {

    fun toListEntity(): MutableList<PhotoEntity> {
        val newList = mutableListOf<PhotoEntity>()
        this.forEach {
            newList.add(it.toPhotoEntity())
        }
        return newList
    }
}