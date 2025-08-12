package com.example.robogyan.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.robogyan.data.local.entities.AssetUsage
import kotlinx.coroutines.flow.Flow

@Dao
interface UsageDao {

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertUsage(usage: List<AssetUsage>)

     @Query("SELECT * FROM asset_usage_table WHERE asset_id = :assetId")
     fun getUsageByAssetId(assetId: Int): Flow<List<AssetUsage>>

     @Query("SELECT * FROM asset_usage_table WHERE id = :usageId")
     fun getUsageByUsageId(usageId: Int): Flow<AssetUsage>

     @Update
     suspend fun updateUsage(usage: AssetUsage): Int

     @Query("DELETE FROM projects_table")
     suspend fun deleteUsage()

     @Query("SELECT COUNT(*) FROM asset_usage_table")
     suspend fun getUsageCount(): Int
}