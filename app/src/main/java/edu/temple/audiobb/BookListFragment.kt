package edu.temple.audiobb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


open class BookListFragment : Fragment(){
    private val key = "key"
    private var mColumnCount = 1
    private var bookList = BookList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bookList = it.getParcelable<BookList>(key)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_book_list, container, false)
        val recycler = layout.findViewById<RecyclerView>(R.id.recycler)
        recycler.adapter = recyclingAdapter(bookList)
        recycler.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL,false)
        recycler.setHasFixedSize(true)
        return layout
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("list", bookList.bookList)
    }

    companion object {
        fun newInstance(param1: BookList) = BookListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(key, param1)
                }
            }
    }
    internal interface ItemListFragmentInterface {
        fun itemClicked(position: Int)
    }


}