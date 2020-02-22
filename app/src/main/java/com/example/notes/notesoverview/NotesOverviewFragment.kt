package com.example.notes.notesoverview


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.notes.R
import com.example.notes.convertLongToDateString
import com.example.notes.database.NotesDatabase
import com.example.notes.databinding.FragmentNotesOverviewBinding

/**
 * A simple [Fragment] subclass.
 */
class NotesOverviewFragment : Fragment() {

    private lateinit var binding: FragmentNotesOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_notes_overview,
            container,
            false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = NotesDatabase.getInstance(application).noteDatabaseDao

        val viewModelFactory = NotesOverviewViewModelFactory(dataSource, application)

        val notesOverviewViewModel =
            ViewModelProvider(this, viewModelFactory).get(NotesOverviewViewModel::class.java)

        binding.notesOverviewViewModel = notesOverviewViewModel
        binding.lifecycleOwner = this

        setObservers(notesOverviewViewModel)

        return binding.root
    }

    private fun setObservers(notesOverviewViewModel: NotesOverviewViewModel) {
        notesOverviewViewModel.navigateToCreateNewNoteEvent.observe(this, Observer {
            if (it == true) {
                //this.findNavController().navigate()
                this.findNavController().navigate(
                    NotesOverviewFragmentDirections.actionNotesOverviewFragmentToCreateNotesFragment(
                        -1
                    )
                )
                notesOverviewViewModel.doneNavigating()
            }
        })

        notesOverviewViewModel.mostRecentNote.observe(this, Observer { note ->

            if (note != null) {
                val dateCreated = convertLongToDateString(note.initialDateNoteCreated)
                binding.textViewMostRecentNote.text = note.noteMessage + " \n" + dateCreated

            } else {
                binding.textViewMostRecentNote.text = "To add a Note Press '+'"
            }

        })
    }


}
