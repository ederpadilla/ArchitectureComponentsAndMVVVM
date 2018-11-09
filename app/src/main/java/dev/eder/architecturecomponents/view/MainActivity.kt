package dev.eder.architecturecomponents.view

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dev.eder.architecturecomponents.R
import dev.eder.architecturecomponents.model.Note
import dev.eder.architecturecomponents.viewmodel.NoteViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem






class MainActivity : AppCompatActivity() {

    companion object {
        val ADD_NOTE : Int = 120
        var EDIT_NOTE_REQUEST = 2
    }

    val adapter = NoteAdapter()

    private lateinit var noteViewModel : NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.setLayoutManager(androidx.recyclerview.widget.LinearLayoutManager(this))
        recyclerView.setHasFixedSize(true)

        recyclerView.setAdapter(adapter)

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        noteViewModel.allNotes.observe(this, observer)

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: androidx.recyclerview.widget.RecyclerView, viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, target: androidx.recyclerview.widget.RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(this@MainActivity, R.string.note_deleted, Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recyclerView)
        adapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
                intent.putExtra(AddNoteActivity.EXTRA_ID, note.id)
                intent.putExtra(AddNoteActivity.EXTRA_TILE, note.title)
                intent.putExtra(AddNoteActivity.EXTRA_DES, note.description)
                intent.putExtra(AddNoteActivity.EXTRA_PRIO, note.priority)
                startActivityForResult(intent, EDIT_NOTE_REQUEST)
            }
        })
    }

    val observer = Observer<List<Note>> {
        notes -> adapter.submitList(notes!!)
    }

    fun addNote(view : View){
        startActivityForResult(Intent(this@MainActivity,
                AddNoteActivity::class.java),ADD_NOTE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==ADD_NOTE && resultCode == Activity.RESULT_OK ){
            val title = data!!.getStringExtra(AddNoteActivity.EXTRA_TILE)
            val des = data.getStringExtra(AddNoteActivity.EXTRA_DES)
            val prio = data.getIntExtra(AddNoteActivity.EXTRA_PRIO,1)
            val note = Note(title,des,prio)
            noteViewModel.insert(note)
            Toast.makeText(this,R.string.note_saved,Toast.LENGTH_SHORT).show()
        }else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            val id = data!!.getIntExtra(AddNoteActivity.EXTRA_ID, -1)

            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show()
                return
            }

            val title = data.getStringExtra(AddNoteActivity.EXTRA_TILE)
            val description = data.getStringExtra(AddNoteActivity.EXTRA_DES)
            val priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIO, 1)

            val note = Note(title, description, priority)
            note.id = id
            noteViewModel.update(note)

            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(this,R.string.note_not_saved,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.delete_all_notes ->{
                noteViewModel.deleteAllNotes()
                Toast.makeText(this@MainActivity, R.string.all_notes_deleted, Toast.LENGTH_SHORT).show()
                return true
            }
            else ->{
                return super.onOptionsItemSelected(item)
            }

        }
    }


}
