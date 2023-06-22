package com.biryulindevelop.unsplash.data.api.photodto

import com.biryulindevelop.unsplash.data.db.entity.PhotoEntity

class PhotoListDto : ArrayList<PhotoDto>() {

    fun toListEntity(): MutableList<PhotoEntity> {
        val newList = mutableListOf<PhotoEntity>()
        this.forEach {
            newList.add(it.toPhotoEntity())
        }
        return newList
    }
}