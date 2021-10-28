package edu.temple.audiobb

import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment


class BookDetailsFragment() : Fragment(), Parcelable {
    var book: Book? = null
    var parentActivity: ItemDetailFragmentInterface? = null
    var setRescources = false
    var textViewBook: TextView? = null
    var textViewAuthor: TextView? = null

    constructor(parcel: Parcel) : this() {
        book = parcel.readParcelable(Book::class.java.classLoader)
        setRescources = parcel.readByte() != 0.toByte()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) { // if there is no saved data
            if (arguments != null) {
                this.book = requireArguments().getParcelable("newbook")
                this.setRescources = requireArguments().getBoolean("yes")
            }
        } else { //using saved instance state
            if (arguments != null) {
                this.book = savedInstanceState.getParcelable("savebook")
                this.setRescources = savedInstanceState.getBoolean("yes")
            }
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
            if (context is ItemDetailFragmentInterface) { //check if activity has the interface implement
                parentActivity = context //storing a reference to parent here in memory
            } else { // if not implemented, Tell them to in error
                throw RuntimeException("Please Implement the Item List Interface before attaching this fragment")
            }
    }

    override fun onSaveInstanceState(outState: Bundle) { //change saved state so that it saves the things you want it to
        super.onSaveInstanceState(outState)
        outState.putParcelable("savebook", book)
        outState.putBoolean("yes", setRescources)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout: View = inflater.inflate(R.layout.fragment_book_details, container, false)
        this.textViewBook = layout.findViewById<TextView>(R.id.bookShow)
        this.textViewAuthor = layout.findViewById<TextView>(R.id.authorShow)
        book?.let { displayBook(it) }
        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDetach() { // on detach is used to stop storing the context parent,
        // to stop memory leaks required
        super.onDetach()
        parentActivity = null
    }

    fun displayBook(Book: Book): BookDetailsFragment {
        book = Book
        textViewBook!!.gravity = View.TEXT_ALIGNMENT_CENTER
        textViewAuthor!!.gravity = View.TEXT_ALIGNMENT_CENTER
        textViewBook!!.setText(book!!.title)
        textViewAuthor!!.setText(book!!.author)
        textViewBook!!.textSize = 30f
        textViewAuthor!!.textSize = 24f
        textViewBook!!.textSize = 25f
        textViewAuthor!!.textSize = 20f
        textViewAuthor!!.setPadding(0, 0, 0, 0)
        textViewBook!!.setPadding(0, 0, 0, 0)
        setRescources = true
        return this
    }

    interface ItemDetailFragmentInterface

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(book, flags)
        parcel.writeByte(if (setRescources) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BookDetailsFragment> {
        override fun createFromParcel(parcel: Parcel): BookDetailsFragment {
            return BookDetailsFragment(parcel)
        }

        override fun newArray(size: Int): Array<BookDetailsFragment?> {
            return arrayOfNulls(size)
        }

        fun newInstance(book: Book): Fragment {
            TODO("Not yet implemented")
        }

    }

    fun newInstance(book: Book?): BookDetailsFragment {
        val fragment = BookDetailsFragment()
        val args = Bundle()
        args.putBoolean("yes", true)
        args.putParcelable("newbook", book)
        fragment.setArguments(args)
        return fragment
    }

}