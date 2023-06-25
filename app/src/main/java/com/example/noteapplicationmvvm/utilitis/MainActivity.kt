package com.example.noteapplicationmvvm.utilitis

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapplicationmvvm.Adapter.Note_rcycler_view
import com.example.noteapplicationmvvm.Database.NoteDatabase
import com.example.noteapplicationmvvm.Models.Note
import com.example.noteapplicationmvvm.Models.NoteViewModel
import com.example.noteapplicationmvvm.R
import com.example.noteapplicationmvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),Note_rcycler_view.NotesItemClickListener,PopupMenu.OnMenuItemClickListener {
    lateinit var binding: ActivityMainBinding
    private lateinit var database: NoteDatabase
    lateinit var viewModel:NoteViewModel
    lateinit var adapter:Note_rcycler_view
    lateinit var selectedNote:Note
    private val updateNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
        if(result.resultCode == Activity.RESULT_OK){
            val note = result.data?.getSerializableExtra("note")as?Note
            if(note!=null){
                viewModel.update(note)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //initilizing the ui
        initUI()

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allnotes.observe(this) { list ->
            list?.let {
                adapter.updatedList(list)
            }
        }
        database = NoteDatabase.getDatabase(this)
    }

    private fun initUI() {

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(1,LinearLayout.VERTICAL)
        adapter = Note_rcycler_view(this,this)
        binding.recyclerView.adapter = adapter

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
            if (result.resultCode == Activity.RESULT_OK){
                val note = result.data?.getSerializableExtra("note")as?Note
                if(note != null){
                    viewModel.insert(note)
                }
            }

        }
        binding.floating.setOnClickListener {
            val intent = Intent(this, note_section::class.java)
            getContent.launch(intent)
        }

    }

    override fun onItemClick(note: Note) {
       val intent = Intent(this@MainActivity,note_section::class.java)
        intent.putExtra("current_note",note)
        updateNote.launch(intent)
    }

    override fun onLongItemClicked(note: Note, cardView: CardView) {
        selectedNote = note
        popUpDisplay(cardView)
        
    }

    private fun popUpDisplay(cardView: CardView) {
        val popUpMenu = PopupMenu(this,cardView)
        popUpMenu.setOnMenuItemClickListener(this@MainActivity)
        popUpMenu.inflate(R.menu.pop_up_menu)
        popUpMenu.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.delete_note){
            viewModel.deleteNote(selectedNote)
            return true
        }
        return false
    }

}