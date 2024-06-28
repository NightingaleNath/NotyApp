package com.codelytical.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigrations {
    const val DB_VERSION = 2

    val MIGRATIONS: Array<Migration>
        get() = arrayOf(
            migration12()
        )

    private fun migration12(): Migration = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`username` TEXT NOT NULL, `password` TEXT NOT NULL, `token` TEXT NOT NULL)")
        }
    }
}
