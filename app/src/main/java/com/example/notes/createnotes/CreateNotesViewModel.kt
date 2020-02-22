package com.example.notes.createnotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notes.database.Note
import com.example.notes.database.NoteDatabaseDao
import kotlinx.coroutines.*

class CreateNotesViewModel(val dataSource: NoteDatabaseDao) : ViewModel() {


    private val viewModelJobs = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJobs)

    private val _navigateToNoteOverviewEvent = MutableLiveData<Boolean?>()
    val navigateToNoteOverviewEvent: LiveData<Boolean?>
        get() = _navigateToNoteOverviewEvent

    fun doneNavigating(){
        _navigateToNoteOverviewEvent.value = false
    }

    fun saveNote(noteMessage: String) {
        uiScope.launch {
            val newNote = Note(noteMessage = noteMessage)
            save(newNote)
            _navigateToNoteOverviewEvent.value = true
        }
    }

    private suspend fun save(note: Note) {
        withContext(Dispatchers.IO) {
            dataSource.insert(note)
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJobs.cancel()
    }
}