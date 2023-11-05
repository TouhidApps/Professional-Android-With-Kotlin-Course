package com.touhidapps.roomdbsqlite

import android.content.Context
import android.util.Log
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.DeleteTable
import androidx.room.RenameColumn
import androidx.room.RenameTable
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [ContactEntity::class], version = DbConstants.LATEST_VERSION, exportSchema = true/*, autoMigrations = [AutoMigration(from = 1, to = 2, spec = AppDatabase.MyAutoMigration::class)]*/)
abstract class AppDatabase : RoomDatabase() {

//    @DeleteColumn
//    @DeleteTable
//    @RenameColumn
    @RenameTable(fromTableName = DbConstants.TABLE_CONTACT, toTableName = DbConstants.TABLE_CONTACT_2)
    class MyAutoMigration: AutoMigrationSpec {
        override fun onPostMigrate(db: SupportSQLiteDatabase) {
            super.onPostMigrate(db)
            // After migration
            // Your code
        }
    }

    abstract fun contactDao(): ContactDao

    companion object {
        lateinit var mDb: AppDatabase

        fun getDb(context: Context): AppDatabase {

            mDb = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, DbConstants.DATABSE_NAME
            )
                .addCallback(roomCallback)
              //  .allowMainThreadQueries() // For test only
                .addMigrations(MIGRATION_1_2)
                .build()
                .also {
                    Log.d("<ROOM_TAG>", it?.openHelper?.writableDatabase?.path ?: "")
                }

            return mDb
        } // getDb

        val roomCallback = object: Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                // todo if necessary after creating db

            }
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE ${DbConstants.TABLE_CONTACT} RENAME TO ${DbConstants.TABLE_CONTACT_2}")
            }
        }

    } // object

}