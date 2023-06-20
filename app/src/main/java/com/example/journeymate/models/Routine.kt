package com.example.journeymate.models

import android.os.Parcel
import android.os.Parcelable

data class Routine(
    val __v: Int,
    val _id: String,
    val city: String,
    val country: String,
    val followers: Int,
    val label_category: String,
    val name: String,
    val routine_comments: List<Any>,
    val routine_creator: String,
    val routine_description: String,
    val state_country: String,
    val tasks: List<Any>,
    val town: String,
    val valorations: List<Any>,
    val visibility: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        TODO("routine_comments"),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        TODO("tasks"),
        parcel.readString()!!,
        TODO("valorations"),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(__v)
        parcel.writeString(_id)
        parcel.writeString(city)
        parcel.writeString(country)
        parcel.writeInt(followers)
        parcel.writeString(label_category)
        parcel.writeString(name)
        parcel.writeString(routine_creator)
        parcel.writeString(routine_description)
        parcel.writeString(state_country)
        parcel.writeString(town)
        parcel.writeString(visibility)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Routine> {
        override fun createFromParcel(parcel: Parcel): Routine {
            return Routine(parcel)
        }

        override fun newArray(size: Int): Array<Routine?> {
            return arrayOfNulls(size)
        }
    }
}