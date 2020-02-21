package com.example.notes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDatabaseDao{
    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    //Get specific note based on key
    @Query("SELECT * from daily_note_table WHERE noteId = :key")
    fun getKey(key:Long): Note?

    //clear database without deleting table - delete every row
    @Query("DELETE FROM daily_note_table")
    fun clear()

    //get a list of notes - sorted by most recent
    @Query("SELECT * FROM daily_note_table ORDER BY noteId DESC")
    fun getAllNotes(): LiveData<List<Note>>

    //get most recent note
    @Query("SELECT * FROM daily_note_table ORDER BY noteId DESC LIMIT 1")
    fun getMostRecentNote():Note?
}