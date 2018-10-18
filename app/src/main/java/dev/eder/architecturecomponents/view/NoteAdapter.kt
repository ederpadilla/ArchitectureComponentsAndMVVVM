package dev.eder.architecturecomponents.view

import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.eder.architecturecomponents.R
import dev.eder.architecturecomponents.model.Note


class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    private var notes: List<Note> = ArrayList()

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.note_item, parent, false)
        return NoteHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = notes[position]
        holder.textViewTitle.setText(currentNote.title)
        holder.textViewDescription.setText(currentNote.description)
        holder.textViewPriority.setText(currentNote.priority.toString())

    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    fun getNoteAt(position: Int): Note {
        return notes[position]
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView
        val textViewDescription: TextView
        val textViewPriority: TextView

        init {
            textViewTitle = itemView.findViewById(R.id.textViewTitle)
            textViewDescription = itemView.findViewById(R.id.textViewDes)
            textViewPriority = itemView.findViewById(R.id.textViewPriority)
            itemView.setOnClickListener {
                val position = adapterPosition
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(notes[position])
                }
            }
        }
    }
}