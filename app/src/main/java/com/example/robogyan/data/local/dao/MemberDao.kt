package com.example.robogyan.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.robogyan.data.local.entities.MemberData
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {

    /**
     * Inserts a new MemberData entry into the database.
     * If a member with the same primary key (id) already exists, it will be replaced.
     * @param member The MemberData object to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member: MemberData)

    /**
     * Retrieves a MemberData entry by its ID.
     * Returns a [Flow] that emits the MemberData object whenever it changes in the database.
     * This is ideal for observing changes in the UI.
     * @param memberId The ID of the member to retrieve (should be the Supabase Auth UID).
     * @return A Flow emitting the MemberData object, or null if not found.
     */
    @Query("SELECT * FROM members_table WHERE id = :memberId")
    fun getMemberById(memberId: String): Flow<MemberData?>

    /**
     * Deletes a MemberData entry by its ID.
     * @param memberId The ID of the member to delete.
     */
    @Query("DELETE FROM members_table WHERE id = :memberId")
    suspend fun deleteMemberById(memberId: String)

    /**
     * Deletes all MemberData entries from the table.
     * Useful for clearing local cache on logout.
     */
    @Query("DELETE FROM members_table")
    suspend fun deleteAllMembers()
}