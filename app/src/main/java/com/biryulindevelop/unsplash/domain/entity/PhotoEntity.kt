package com.biryulindevelop.unsplash.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.biryulindevelop.unsplash.domain.model.Photo

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey
    @ColumnInfo(name = "photo_id")
    var photoId: String,
    @ColumnInfo(name = "photo_url")
    val smallUrls: String,
    @ColumnInfo(name = "is_liked_by_user")
    var likedByUser: Boolean,
    @ColumnInfo(name = "photo_counter_like")
    var counterLikes: Int,
    @ColumnInfo(name = "user_name")
    val userName: String,
    @ColumnInfo(name = "user_avatar")
    val profileImage: String
) {
    companion object {
        fun fromPhoto(photo: Photo): PhotoEntity {
            return PhotoEntity(
                photoId = photo.id,
                smallUrls = photo.urlsSmall,
                likedByUser = photo.likedByUser,
                counterLikes = photo.likes,
                userName = photo.userName,
                profileImage = photo.userAvatar
            )
        }

        fun toPhoto(photoEntity: PhotoEntity): Photo {
            return Photo(
                id = photoEntity.photoId,
                urlsSmall = photoEntity.smallUrls,
                likedByUser = photoEntity.likedByUser,
                likes = photoEntity.counterLikes,
                userName = photoEntity.userName,
                userAvatar = photoEntity.profileImage,
                height = 0,
                width = 0
            )
        }
    }
}