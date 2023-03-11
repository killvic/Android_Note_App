package com.example.note_app_hw

import androidx.recyclerview.widget.DiffUtil
import com.example.note_app_hw.ObjectClasses.*

class DiffUtilForNoteItem(
    private val oldList: List<NoteListItem>,
    private val newList: List<NoteListItem>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean { // ПРОВЕРИТЬ НОРМАЛЬНАЯ ЛИ ЭТО РЕАЛИЗАЦИЯ
        return when (oldList[oldItemPosition]) {
            is NoteClass -> {
                if (newList[newItemPosition] is NoteClass)
                    ((oldList[oldItemPosition] as NoteClass).id == (newList[newItemPosition] as NoteClass).id)
                else
                    false
            }
            is DateClass -> {
                if (newList[newItemPosition] is DateClass)
                    ((oldList[oldItemPosition] as DateClass).date == (newList[newItemPosition] as DateClass).date)
                else
                    false
            }
            else -> {throw IllegalArgumentException("Invalid item type in diffUtil")}
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when (oldList[oldItemPosition]) {
            is NoteClass -> areNotesTheSame(oldList[oldItemPosition] as NoteClass, newList[newItemPosition] as NoteClass)
            is DateClass -> areDatesTheSame(oldList[oldItemPosition] as DateClass, newList[newItemPosition] as DateClass)
            else -> {throw IllegalArgumentException("Invalid item type in diffUtil")}
        }
    }
}