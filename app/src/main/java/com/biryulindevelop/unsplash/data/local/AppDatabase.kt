package com.biryulindevelop.unsplash.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.biryulindevelop.unsplash.data.local.entity.PhotoEntity

@Database(entities = [PhotoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getPhotosDao(): PhotosDao
}