package com.example.robogyan.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.robogyan.data.local.entities.Inventory
import com.example.robogyan.data.local.entities.MemberData
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInventory(members: List<Inventory>)

    @Query("SELECT * FROM inventory_table")
    fun getInventory(): Flow<List<Inventory>>

    @Query("DELETE FROM inventory_table")
    suspend fun deleteInventory()

    @Query("SELECT * FROM inventory_table WHERE id = :id")
    fun getAssetById(id: Int): Flow<Inventory?>

    @Query("SELECT COUNT(*) FROM inventory_table")
    suspend fun getInventoryCount(): Int

}