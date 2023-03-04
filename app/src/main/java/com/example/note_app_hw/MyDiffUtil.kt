package com.example.note_app_hw

import androidx.recyclerview.widget.DiffUtil
import com.example.note_app_hw.note_package.NoteClass
import com.example.note_app_hw.note_package.areNotesTheSame

class MyDiffUtil(
    private val oldList: List<NoteClass>,
    private val newList: List<NoteClass>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areNotesTheSame(oldList[oldItemPosition], newList[newItemPosition])
    }
}