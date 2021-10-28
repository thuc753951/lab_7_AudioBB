package edu.temple.audiobb

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class MainActivity : AppCompatActivity(){

    var bookList = BookList() // how to init my own object/class item
    abstract var container2present: Boolean // check for container 2
    var bookListFragment: BookListFragment? = null
    var bookDetailFragment: BookDetailsFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container2present = findViewById<FrameLayout>(R.id.container2) != null

        if(savedInstanceState == null){
            // if the app first load
            bookList = BookList() //initialize everything

            createBooklists()
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.container,
                    BookListFragment.newInstance(bookList).also { bookListFragment = it })
                .commit()
            if (container2present) { //if landscape just make empty detail frag once
                bookDetailFragment = BookDetailsFragment()
                supportFragmentManager.beginTransaction()
                    .add(R.id.container2, bookDetailFragment!!)
                    .hide(bookDetailFragment!!)
                    .commit()
            }
        } else { // if there  is data saved
            bookList = savedInstanceState.getParcelable("list")!!
            bookListFragment = savedInstanceState.getParcelable("listfrag")
            bookDetailFragment = savedInstanceState.getParcelable("detailfrag")
            Log.d("log_tag", "container2present = $container2present")
            if (container2present) { // if landscape,
                if (bookDetailFragment != null) { //if bookDetail is not null, there is one data saved, it could still be blank
                    if (bookDetailFragment!!.setRescources) {
                        Log.d("log_tag", "line 61:")

                        supportFragmentManager.popBackStackImmediate()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container2, bookDetailFragment!!)
                            .commit()
                        val original_book: Book? = bookDetailFragment!!.book
                        Handler(Looper.getMainLooper()).postDelayed({
                            original_book?.let { bookDetailFragment!!.displayBook(it) }
                        }, 100)
                    } else {
                        Log.d("log_tag", "line 77:")
                        bookDetailFragment = BookDetailsFragment()
                        supportFragmentManager.beginTransaction()
                            .add(R.id.container2, bookDetailFragment!!)
                            .commit()
                    }
                } else { //if it is null, make a blank one
                    bookDetailFragment = BookDetailsFragment()
                    Log.d("log_tag", "line 87:")
                    supportFragmentManager.beginTransaction()
                        .add(R.id.container2, bookDetailFragment!!)
                        .commit()
                }
            } else { // if portrait
                //Log.d("log_tag", "Portrait: " + bookDetailFragment.book.title );

                // if there is not a detail showing in the container
                if (bookDetailFragment != null) {
                    val fragment = supportFragmentManager.findFragmentById(R.id.container)
                    //Log.d("log_tag", "Portrait1: " + bookDetailFragment.book.title );
                    Log.d("log_tag", "line 100:")
                    if (fragment is BookListFragment) { // if container has list fragment
                        //Log.d("log_tag", "Portrait2: " + bookDetailFragment.book.title );
                        if (bookDetailFragment!!.setRescources) {
                            Log.d("log_tag", "line 106:")
                            supportFragmentManager.popBackStackImmediate()
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.container, bookDetailFragment!!)
                                .addToBackStack(null)
                                .commit()
                            val original_book: Book? = bookDetailFragment!!.book
                            Handler(Looper.getMainLooper()).postDelayed({
                                if (original_book != null) {
                                    bookDetailFragment!!.displayBook(original_book)
                                }
                            }, 50)
                        } else {
                            Log.d("log_tag", "line 124:")
                            Toast.makeText(this, "line: 123", Toast.LENGTH_SHORT).show()
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.container, bookListFragment!!)
                                .commit()
                        }
                    }
                }
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("list", this.bookList)
        outState.putParcelable("detailfrag", this.bookDetailFragment)
        outState.putParcelable("listfrag", this.bookListFragment)
        //outState.putParcelable("curBook",this.book);
    }

    open fun itemClicked(position: Int) { // onclick to manipulate bookdetailFragment
        if (!container2present) { // if its portrait, keep making them replacing fragments
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BookDetailsFragment.newInstance(bookList.get(position)))
                .addToBackStack(null)
                .commit()
            // when item is clicked and add to back stack is called
        } else { // when its in landscape , two fragments present
            val fragment: Fragment? =
                supportFragmentManager.findFragmentById(R.id.container2) as BookDetailsFragment?
            Log.d("log_tag", "Landscape Item click1")
            if (!bookDetailFragment?.setRescources!!) { // if there isnt a bookdetail frag yet
                //System.out.println("==============Line: 82==================");
                Log.d("log_tag", "Landscape Item click2")
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container2, BookDetailsFragment.newInstance(bookList.get(position)))
                    .commit()
            } else { // if there is a bookdetail frag
                Log.d("log_tag", "Landscape Item click3")
                if (fragment is BookDetailsFragment) {
                    //bookDetailFragment = (BookDetailFragment) fragment;
                    Log.d("log_tag", "Landscape Item click4")
                    bookDetailFragment?.displayBook(bookList.get(position))
                        .also { bookDetailFragment = it}?.let {
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.container2, it)
                                .commitNow()
                        }
                }
            }
        }
    }

    fun createBooklists() { // initialize data, WORKSSSS

        //all arrays are 10 in size/length
        //BookList list = new BookList();
        val BookName = resources.getStringArray(R.array.Book)
        val BookAuthor = resources.getStringArray(R.array.Author)
        var i = 0
        while (i < resources.getStringArray(R.array.Author).size) {
            val book = Book(BookName[i], BookAuthor[i])
            bookList.add(book)
            Toast.makeText(
                applicationContext,
                bookList.get(i).title + " by " + bookList.get(i).author,
                Toast.LENGTH_LONG
            ).show()
            i++
        }
    }


}

private fun Parcelable.putParcelable(s: String, bookListFragment: BookListFragment?) {

}
