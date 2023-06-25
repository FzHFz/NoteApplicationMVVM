package com.example.noteapplicationmvvm.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapplicationmvvm.Database.NoteDatabase
import com.example.noteapplicationmvvm.Database.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application):AndroidViewModel(application) {
    private val repository: NotesRepository
    val allnotes : LiveData<List<Note>>
    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        repository = NotesRepository(dao)
        allnotes = repository.allNote
    }
    fun deleteNote(note:Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }
    fun insert(note:Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
    fun update(note:Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }
}