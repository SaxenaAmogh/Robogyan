package com.example.robogyan.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.robogyan.data.local.entities.AllMembers
import kotlinx.coroutines.flow.Flow

@Dao
interface AllMembersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMembers(members: List<AllMembers>)

    @Query("SELECT * FROM all_members_table")
    fun getAllMembers(): Flow<List<AllMembers>>

    @Query("DELETE FROM all_members_table")
    suspend fun deleteAllMembers()

    @Query("SELECT COUNT(*) FROM members_table")
    suspend fun getMemberCount(): Int

}