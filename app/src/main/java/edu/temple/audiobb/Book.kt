package edu.temple.audiobb

import android.os.Parcel
import android.os.Parcelable


class Book() : Parcelable{
    var title = "" // string
    var author = "" // string

    constructor(parcel: Parcel) : this() {
        title = parcel.readString().toString()
        author = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(author)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }

}
