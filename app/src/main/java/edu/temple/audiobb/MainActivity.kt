package edu.temple.audiobb

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var bookList = BookList() // how to init my own object/class item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            createBooklists()


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
}