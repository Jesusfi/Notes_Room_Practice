package com.example.notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    // tell the database which dao to use
    abstract val noteDatabaseDao: NoteDatabaseDao

    // create companion object to provide client with database
    companion object {
        @Volatile // variable never cached - instance always up to date
        private var INSTANCE: NotesDatabase? = null

        fun getInstance(context: Context): NotesDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) { // check if database exists
                    //create a database
                    instance = Room.databaseBuilder(
                        context.applicationContext, // supply context
                        NotesDatabase::class.java, // which database to build
                        "Note_history_database" //name database
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance //assign instance to new database


                }

                return instance
            }
        }

    }
}