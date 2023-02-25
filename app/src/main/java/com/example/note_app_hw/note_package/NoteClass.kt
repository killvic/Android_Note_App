package com.example.note_app_hw.note_package

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//@Parcelize
data class NoteClass(
    var name: String = "",
    var text: String = "",
    var lastChange: String = ""
    ) : java.io.Serializable


//{
//    constructor(parcel: Parcel) : this(
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString()
//    ) {
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(name)
//        parcel.writeString(text)
//        parcel.writeString(lastChange)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<NoteClass> {
//        override fun createFromParcel(parcel: Parcel): NoteClass {
//            return NoteClass(parcel)
//        }
//
//        override fun newArray(size: Int): Array<NoteClass?> {
//            return arrayOfNulls(size)
//        }
//    }
//}
