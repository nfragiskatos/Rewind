package com.nfragiskatos.rewind.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nfragiskatos.rewind.data.local.dao.media.movie.MovieDao
import com.nfragiskatos.rewind.data.local.entity.media.movie.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class RewindDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}