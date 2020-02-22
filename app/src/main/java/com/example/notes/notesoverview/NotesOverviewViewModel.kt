package com.example.notes.notesoverview

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.notes.database.Note
import com.example.notes.database.NoteDatabaseDao
import kotlinx.coroutines.*

class NotesOverviewViewModel(
    private val database: NoteDatabaseDao,
    private val application: Application
) : ViewModel() {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToCreateNewNoteEvent = MutableLiveData<Boolean?>()
    val navigateToCreateNewNoteEvent: LiveData<Boolean?>
        get() = _navigateToCreateNewNoteEvent


    private val _notes = database.getAllNotes()
    val notes: LiveData<List<Note>>
        get() = _notes

    private val _mostRecentNote = MutableLiveData<Note?>()
    val mostRecentNote: LiveData<Note?>
        get() = _mostRecentNote


    init {
        initializeMostRecentNote()
    }

    private fun initializeMostRecentNote() {
        uiScope.launch {
            _mostRecentNote.value = getMostRecentNote()
        }
    }

    private suspend fun getMostRecentNote() : Note? {
       return withContext(Dispatchers.IO){
          val note =  database.getMostRecentNote()

           note
       }
    }

    val clearButtonVisible = Transformations.map(notes){
        it?.isNotEmpty()
    }

    fun navigateToCreateNewNote() {
        _navigateToCreateNewNoteEvent.value = true
    }

    fun doneNavigating() {
        _navigateToCreateNewNoteEvent.value = false
    }

    fun clearNotes() {
        uiScope.launch {
            clear()
            _mostRecentNote.value = getMostRecentNote()
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}