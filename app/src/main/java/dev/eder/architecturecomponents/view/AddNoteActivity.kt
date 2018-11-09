package dev.eder.architecturecomponents.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import dev.eder.architecturecomponents.R
import kotlinx.android.synthetic.main.activity_add_note.*





class AddNoteActivity : AppCompatActivity() {

    companion object {
        val EXTRA_TILE = "dev.eder.architecturecomponents.EXTRA_TITLE"
        val EXTRA_DES = "dev.eder.architecturecomponents.EXTRA_DES"
        val EXTRA_PRIO = "dev.eder.architecturecomponents.EXTRA_PRIO"
        var EXTRA_ID = "com.codinginflow.architectureexample.EXTRA_ID"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        numberPicker.minValue = 1
        numberPicker.maxValue = 10
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close)
        val intent = intent

        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Note"
            etTitle.setText(intent.getStringExtra(EXTRA_TILE))
            etDes.setText(intent.getStringExtra(EXTRA_DES))
            numberPicker.setValue(intent.getIntExtra(EXTRA_PRIO, 1))
        } else {
            title = "Add Note"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.save_note ->{
                saveNote()
                return true
            }
            else ->{
                return super.onOptionsItemSelected(item)
            }

        }
    }

    private fun saveNote() {
        val title = etTitle.getText().toString()
        val description = etDes.getText().toString()
        val priority = numberPicker.getValue()

        if (title.trim({ it <= ' ' }).isEmpty() || description.trim({ it <= ' ' }).isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent()
        data.putExtra(EXTRA_TILE, title)
        data.putExtra(EXTRA_DES, description)
        data.putExtra(EXTRA_PRIO, priority)

        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id != -1) {
            data.putExtra(EXTRA_ID, id)
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }
}
