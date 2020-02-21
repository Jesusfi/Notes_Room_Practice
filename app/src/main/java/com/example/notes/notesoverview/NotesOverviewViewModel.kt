package com.example.notes.notesoverview

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.notes.database.NoteDatabaseDao

class NotesOverviewViewModel(
    val database: NoteDatabaseDao,
    application: Application
) : ViewModel() {


}