package com.example.note_app_hw

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.note_app_hw.ObjectClasses.DateClass
import com.example.note_app_hw.ObjectClasses.NoteClass
import com.example.note_app_hw.ObjectClasses.NoteListItem
import java.text.SimpleDateFormat
import java.util.*

class RecyclerViewAdapterMultipleItems (
    var itemList: List<NoteListItem> = listOf(),
    var onClick: ((Int) -> Unit) ?= null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val NOTE_VIEW_TYPE = 1
        const val DATE_VIEW_TYPE = 2
    }

    class ViewHolderForNote(view: View) : RecyclerView.ViewHolder(view) {
        val tvNoteName = view.findViewById<TextView>(R.id.tvNoteNameRow)
        val tvNoteText = view.findViewById<TextView>(R.id.tvNoteTextRow)
        val tvLastChange = view.findViewById<TextView>(R.id.tvLastChangeRow)

        fun bind(note: NoteClass) {
            tvNoteName.text = note.name
            tvNoteText.text = note.text
            tvLastChange.text = convertLongToTime(note.lastChange)
        }
    }

    class ViewHolderForDate(view: View) : RecyclerView.ViewHolder(view) {
        val tvDate = view.findViewById<TextView>(R.id.tvDate)

        fun bind(date: DateClass) {
            tvDate.text = convertLongToTime(date.date)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            NOTE_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_view_row, parent, false)
                ViewHolderForNote(view)
            }
            DATE_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_view_row_for_date, parent, false)
                ViewHolderForDate(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            NOTE_VIEW_TYPE -> {
                val item = itemList[position] as NoteClass
                val itemHolder = holder as ViewHolderForNote
                itemHolder.bind(item)
                holder.itemView.setOnClickListener {
                    onClick?.invoke(itemList[position].id)
                }
            }
            DATE_VIEW_TYPE -> {
                val item = itemList[position] as DateClass
                val itemHolder = holder as ViewHolderForDate
                itemHolder.bind(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]) {
            is NoteClass -> NOTE_VIEW_TYPE
            is DateClass -> DATE_VIEW_TYPE
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    fun setData(newItemList: List<NoteListItem>) {
        val diffUtil = DiffUtilForNoteItem(itemList, newItemList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        itemList = newItemList
        diffResults.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = itemList.size
}

// HELP FUNCTIONS

private fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
    return format.format(date)
}