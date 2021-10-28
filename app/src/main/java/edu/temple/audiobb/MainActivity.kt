package edu.temple.audiobb

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class MainActivity : AppCompatActivity(), recyclingAdapter.OnItemClickListener {

    var bookList = BookList() // how to init my own object/class item
    abstract var container2present: Boolean // check for container 2
    val adapting = recyclingAdapter(bookList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container2present = findViewById<FrameLayout>(R.id.container2) != null

        if(savedInstanceState == null){
            createBooklists() // init the array list
            val recyclerView = findViewById<RecyclerView>(R.id.recycler)
            recyclerView.adapter = adapting
            recyclerView.layoutManager = GridLayoutManager(this, 1,
                GridLayoutManager.VERTICAL, false)
            recyclerView.setHasFixedSize(true)


        }else{


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
            Toast.makeText(applicationContext, bookList.get(i).title + " by "+ bookList.get(i).author, Toast.LENGTH_LONG).show()
            i++
        }
    }

    override fun onItemClick(position: Int) { // send to bookdetails fragment
//        val secondActivityIntent = Intent(this@SelectionActivity, DisplayActivity::class.java)
//        secondActivityIntent.putExtra("String", imageArray[position].description)
//        startActivity(secondActivityIntent)
    }

}