package com.example.robogyan.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.robogyan.data.local.entities.Resources
import kotlinx.coroutines.flow.Flow

@Dao
interface ResourcesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResources(resources: List<Resources>)

    @Query("SELECT * FROM resources_table")
    fun getResources(): Flow<List<Resources>>

    @Query("DELETE FROM resources_table")
    suspend fun deleteResources()

    @Query("SELECT COUNT(*) FROM resources_table")
    suspend fun getResourcesCount(): Int
}