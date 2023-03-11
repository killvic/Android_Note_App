package com.example.note_app_hw

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.note_app_hw.ObjectClasses.NoteClass
import java.text.SimpleDateFormat
import java.util.*

class RecyclerViewAdapter(
    var notes: List<NoteClass> = listOf(),
    var onClick: ((Int) -> Unit) ?= null
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNoteName = view.findViewById<TextView>(R.id.tvNoteNameRow)
        val tvNoteText = view.findViewById<TextView>(R.id.tvNoteTextRow)
        val tvLastChange = view.findViewById<TextView>(R.id.tvLastChangeRow)

        fun bind(view: ViewHolder, note: NoteClass) {
            view.tvNoteName.text = note.name
            view.tvNoteText.text = note.text
            view.tvLastChange.text = convertLongToTime(note.lastChange)
        }

        fun convertLongToTime(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
            return format.format(date)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_view_row, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(viewHolder, notes[position])

        viewHolder.itemView.setOnClickListener {
            onClick?.invoke(notes[position].id)
        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = notes.size

    fun setData(newNoteList: List<NoteClass>) {
        val diffUtil = MyDiffUtil(notes, newNoteList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        notes = newNoteList
        diffResults.dispatchUpdatesTo(this)
    }
}

