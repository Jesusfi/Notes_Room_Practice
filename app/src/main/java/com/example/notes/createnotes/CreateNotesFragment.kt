package com.example.notes.createnotes


import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notes.R
import com.example.notes.database.NotesDatabase
import com.example.notes.databinding.FragmentCreateNotesBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 */
class CreateNotesFragment : Fragment() {
    private lateinit var binding: FragmentCreateNotesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_notes, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = NotesDatabase.getInstance(application).noteDatabaseDao

        val viewModelFactory = CreateNotesViewModelFactory(dataSource)

        val createNoteViewModel = ViewModelProvider(this, viewModelFactory).get(CreateNotesViewModel::class.java)

        binding.createNotesViewModel = createNoteViewModel
        binding.lifecycleOwner = this

        setObservers(createNoteViewModel)

        binding.buttonSaveNote.setOnClickListener {
            val message = binding.noteMessage.text.toString().trim()
            if(!TextUtils.isEmpty(message)){
                createNoteViewModel.saveNote(message)
            }else{
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    "Please enter text to save",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }


        return binding.root
    }

    private fun setObservers(createNoteViewModel: CreateNotesViewModel) {
        createNoteViewModel.navigateToNoteOverviewEvent.observe(this, Observer {
            if (it == true){
                this.findNavController().navigate(CreateNotesFragmentDirections.actionCreateNotesFragmentToNotesOverviewFragment())
                createNoteViewModel.doneNavigating()
            }
        })
    }


}
