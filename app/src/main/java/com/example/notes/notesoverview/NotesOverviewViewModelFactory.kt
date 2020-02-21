package com.example.notes.notesoverview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.database.NoteDatabaseDao
import java.lang.IllegalArgumentException

class NotesOverviewViewModelFactory (
    val database: NoteDatabaseDao,
    val application: Application
): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NotesOverviewViewModel::class.java)){
            return NotesOverviewViewModel(database, application) as T
        }
        throw IllegalArgumentException("Unknown Arguments")
    }
}