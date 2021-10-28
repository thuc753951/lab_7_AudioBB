package edu.temple.audiobb

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class recyclingAdapter(private val _list: BookList): RecyclerView.Adapter<recyclingAdapter.MyViewHolder>(){


    inner class MyViewHolder (_myLayout : View) : RecyclerView.ViewHolder(_myLayout){
        val title = _myLayout.findViewById<TextView>(R.id.textView1)
        val author = _myLayout.findViewById<TextView>(R.id.textView2)


        init{
            title.setTextColor(Color.BLACK)
            title.textSize = 20f

            author.setTextColor(Color.BLACK)
            author.textSize = 10f

            //_myLayout.setOnClickListener(this)
        }

//        override fun onClick(v: View?) { // forwards click events to main
//            val position = adapterPosition
//            if(position != RecyclerView.NO_POSITION){
//                listener.onItemClick(position)
//            }
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recyclingAdapter.MyViewHolder {
        val newView = LayoutInflater.from(parent.context).inflate(R.layout.temporary, parent, false)
        return MyViewHolder(newView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var current = _list.get(position)
        holder.title.text = current.title
        holder.author.text = current.author
    }

    override fun getItemCount() = _list.size()

//    interface OnItemClickListener{
//        fun onItemClick(position: Int)
//    }
}