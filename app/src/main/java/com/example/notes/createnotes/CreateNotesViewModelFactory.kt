package com.example.notes.createnotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.database.NoteDatabaseDao
import java.lang.IllegalArgumentException

class CreateNotesViewModelFactory(private val dataSource: NoteDatabaseDao):ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CreateNotesViewModel::class.java)){
            return CreateNotesViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Arguments are unknown")
    }
}