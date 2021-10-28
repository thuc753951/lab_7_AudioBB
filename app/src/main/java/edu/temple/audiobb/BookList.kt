package edu.temple.audiobb

import android.os.Parcel
import android.os.Parcelable


class BookList() : Parcelable{
    var bookList = ArrayList<Book>()

    constructor(parcel: Parcel) : this() {

    }

    fun add(book: Book){
        bookList.add(book)
    }

    fun remove(book: Book){
        bookList.remove(book)
    }

    fun get(postion: Int): Book{
        return bookList[postion]
    }

    fun size(): Int{
        return bookList.size
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BookList> {
        override fun createFromParcel(parcel: Parcel): BookList {
            return BookList(parcel)
        }

        override fun newArray(size: Int): Array<BookList?> {
            return arrayOfNulls(size)
        }
    }

}