package com.example.lab07

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab07.models.Question
import android.view.View.OnClickListener
import android.widget.Button

class DataAdapter(private val list: List<Question>, private val listener: OnItemClickListener, private val listenerDelete: OnDeleteButtonClickListener) : RecyclerView.Adapter<DataAdapter.DataViewHolder>(){

    var  createCounter: Int = 0
    var bindCounter: Int = 0

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
    interface OnDeleteButtonClickListener{
        fun onDeleteClick(position: Int)
    }


    interface myOnClickListener : OnClickListener{
        fun onDeleteClick(position: Int)
    }

    inner class DataViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView), OnClickListener, myOnClickListener{
        val questionTV: TextView = itemView.findViewById(R.id.questionTextTV)
        val correctAnswerTV: TextView = itemView.findViewById(R.id.correctAnswerTV)
        val deleteButton : Button = itemView.findViewById(R.id.deleteButton)
        val detailsButton : Button = itemView.findViewById(R.id.detailsButton)


        init{
            itemView.setOnClickListener(this)
            detailsButton.setOnClickListener(this)
            deleteButton.setOnClickListener{
                onDeleteClick(this.adapterPosition)
                itemView.setVisibility(View.INVISIBLE)
            }

        }


        override fun onClick(p0: View?) {
            val currentPosition = this.adapterPosition
            Log.d("xxx", "AdapterPosition: $currentPosition")
            listener.onItemClick(currentPosition)
        }

        override fun onDeleteClick(position: Int) {
            val currentPosition = this.adapterPosition
            Log.d("xxx", "AdapterPosition: $currentPosition")
            listenerDelete.onDeleteClick(currentPosition)
            notifyItemRemoved(position)


        }

    }


    // 2. Called only a few times = number of items on screen + a few more ones
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        ++createCounter
        Log.d("xxx", "onCreateViewHolder: $createCounter")
        return DataViewHolder(itemView)
    }

    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = list[position]
        holder.questionTV.setText(currentItem.text)
        holder.correctAnswerTV.setText(currentItem.answers.get(0).answer)
        ++bindCounter
        Log.d("xxx", "onBindViewHolder: $bindCounter")
    }

    // 4.
    override fun getItemCount() = list.size


}