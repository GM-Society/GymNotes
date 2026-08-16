package com.gmsociety.gymnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import androidx.lifecycle.ViewModelProvider
import com.gmsociety.gymnotes.data.local.GymNotesDatabase
import com.gmsociety.gymnotes.data.repository.NoteRepository
import com.gmsociety.gymnotes.ui.theme.GymNotesTheme
import com.gmsociety.gymnotes.ui.theme.screens.notes.NotesScreen
import com.gmsociety.gymnotes.viewmodel.NotesViewModel
import com.gmsociety.gymnotes.viewmodel.NotesViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        // Database
        val database = GymNotesDatabase.getDatabase(applicationContext)

        // Repository
        val repository = NoteRepository(database.noteDao())

        // ViewModel
        val viewModel = ViewModelProvider(
            this,
            NotesViewModelFactory(repository)
        )[NotesViewModel::class.java]

        window.navigationBarColor = android.graphics.Color.BLACK

        setContent {
            GymNotesTheme {
                NotesScreen(viewModel = viewModel)
            }
        }
    }
}
