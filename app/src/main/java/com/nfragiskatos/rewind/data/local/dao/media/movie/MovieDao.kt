package com.nfragiskatos.rewind.data.local.dao.media.movie

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.nfragiskatos.rewind.data.local.entity.media.movie.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movieentity")
    suspend fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM movieentity WHERE id = :id")
    suspend fun findById(id: Int): MovieEntity?

    @Insert
    suspend fun insertAll(vararg movies: MovieEntity)

    @Delete
    suspend fun delete(movie: MovieEntity)
}