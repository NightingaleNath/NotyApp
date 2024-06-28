package com.codelytical.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codelytical.data.local.dao.NotesDao
import com.codelytical.data.local.dao.UserDao
import com.codelytical.data.local.entity.NoteEntity
import com.codelytical.data.local.entity.UserEntity

@Database(
    entities = [NoteEntity::class, UserEntity::class],
    version = DatabaseMigrations.DB_VERSION,
    exportSchema = false
)
abstract class NotyDatabase : RoomDatabase() {

    abstract fun getNotesDao(): NotesDao
    abstract fun getUserDao(): UserDao

    companion object {
        private const val DB_NAME = "noty_database"

        @Volatile
        private var INSTANCE: NotyDatabase? = null

        fun getInstance(context: Context): NotyDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotyDatabase::class.java,
                    DB_NAME
                ).addMigrations(*DatabaseMigrations.MIGRATIONS).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
