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

class RecyclerViewAdapterForFolder(
    var notes: List<NoteClass> = listOf(),
    var onClick: ((Int) -> Unit) ?= null
) :
    RecyclerView.Adapter<RecyclerViewAdapterForFolder.ViewHolderFav>() {
    class ViewHolderFav(view: View) : RecyclerView.ViewHolder(view) {
        val tvNoteName = view.findViewById<TextView>(R.id.tvNameOfNoteFav)
        val tvNoteText = view.findViewById<TextView>(R.id.tvTextOfNoteFav)
        val tvLastChange = view.findViewById<TextView>(R.id.tvLastChangeFav)
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolderFav {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_view_row_for_folder, viewGroup, false)

        return ViewHolderFav(view)
    }
    override fun onBindViewHolder(viewHolder: ViewHolderFav, position: Int) {
        viewHolder.tvNoteName.text = notes[position].name
        viewHolder.tvNoteText.text = notes[position].text
        viewHolder.tvLastChange.text = convertLongToTime(notes[position].lastChange)

        viewHolder.itemView.setOnClickListener {
            onClick?.invoke(notes[position].id)
        }
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }
    fun setData(newNoteList: List<NoteClass>) {
        val diffUtil = MyDiffUtil(notes, newNoteList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        notes = newNoteList
        diffResults.dispatchUpdatesTo(this)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = notes.size
}