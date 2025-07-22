package com.example.robogyan.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.robogyan.data.local.entities.Inventory
import com.example.robogyan.data.local.entities.Projects
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProjects(members: List<Projects>)

    @Query("SELECT * FROM projects_table")
    fun getProjects(): Flow<List<Projects>>

    @Query("DELETE FROM projects_table")
    suspend fun deleteProjects()

    @Query("SELECT * FROM projects_table WHERE id = :id")
    fun getProjectById(id: Int): Flow<Projects?>

    @Query("SELECT COUNT(*) FROM projects_table")
    suspend fun getProjectsCount(): Int

}