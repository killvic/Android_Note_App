package com.example.note_app_hw

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.note_app_hw.note_package.NoteClass

class RecyclerViewAdapter(
    var notes: List<NoteClass> = listOf(),
    var onClick: ((Int) -> Unit) ?= null
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNoteName = view.findViewById<TextView>(R.id.tvNoteNameRow)
        val tvNoteText = view.findViewById<TextView>(R.id.tvNoteTextRow)
        val tvLastChange = view.findViewById<TextView>(R.id.tvLastChangeRow)
        //val rootLayout: constraing = view.findViewById(R.id.recycler_view_row_root)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_view_row, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvNoteName.text = notes[position].name
        viewHolder.tvNoteText.text = notes[position].text
        viewHolder.tvLastChange.text = notes[position].lastChange


//        val colorRes = when (position % 6) {
//            0 -> R.color.color_purple_background
//            1 -> R.color.color_red_background
//            2 -> R.color.color_green_background
//            3 -> R.color.color_yellow_background
//            4 -> R.color.color_blue_background
//            else -> R.color.color_dark_blue_background
//        }

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
