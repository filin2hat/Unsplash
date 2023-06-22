package com.biryulindevelop.unsplash.data.db.entity

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
    fun toPhoto() = Photo(
        id = photoId,
        urlsSmall = smallUrls,
        likedByUser = likedByUser,
        likes = counterLikes,
        userName = userName,
        userAvatar = profileImage,
        height = 0,
        width = 0
    )
}