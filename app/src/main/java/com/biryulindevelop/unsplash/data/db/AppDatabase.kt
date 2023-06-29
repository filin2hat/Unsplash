package com.biryulindevelop.unsplash.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.biryulindevelop.unsplash.domain.entity.PhotoEntity

@Database(entities = [PhotoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getPhotosDao(): PhotosDao
}
