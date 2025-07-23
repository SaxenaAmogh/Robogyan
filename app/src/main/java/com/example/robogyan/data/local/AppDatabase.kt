package com.example.robogyan.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.robogyan.data.local.dao.AllMembersDao
import com.example.robogyan.data.local.dao.InventoryDao
import com.example.robogyan.data.local.dao.MemberDao
import com.example.robogyan.data.local.dao.ProjectsDao
import com.example.robogyan.data.local.dao.ResourcesDao
import com.example.robogyan.data.local.entities.AllMembers
import com.example.robogyan.data.local.entities.Inventory
import com.example.robogyan.data.local.entities.MemberData
import com.example.robogyan.data.local.entities.Projects
import com.example.robogyan.data.local.entities.Resources

@Database(
    entities = [MemberData::class, AllMembers::class, Inventory::class, Projects::class, Resources::class],
    version = 6,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Provides the DAO for MemberData operations.
     */
    abstract fun memberDao(): MemberDao
    abstract fun allMembersDao(): AllMembersDao
    abstract fun inventoryDao(): InventoryDao
    abstract fun projectsDao(): ProjectsDao
    abstract fun resourcesDao(): ResourcesDao

    companion object {
        // @Volatile annotation ensures that INSTANCE is always up-to-date and visible to all threads.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Returns the singleton instance of the AppDatabase.
         * If the instance is null, it creates a new one in a synchronized block to prevent multiple instances.
         * @param context The application context, used to create the database.
         * @return The singleton instance of AppDatabase.
         */
        fun getDatabase(context: Context): AppDatabase {
            // If INSTANCE is not null, then return it,
            // otherwise create a new database instance.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, // Use applicationContext to avoid memory leaks
                    AppDatabase::class.java,
                    "robogyan_database" // The name of your database file
                )
                    // .fallbackToDestructiveMigration() // Optional: Destroys database on version mismatch
                    // If you change the database schema (e.g., add a column, change a table name),
                    // you must increment the version number. For production, you'd implement
                    // proper migrations instead of fallbackToDestructiveMigration().
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
