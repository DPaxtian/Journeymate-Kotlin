package com.example.journeymate.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.journeymate.R
import com.example.journeymate.models.Routine

class MyListsViewAdapter : RecyclerView.Adapter<MyListsViewAdapter.ViewHolder>() {
    var myRoutines : MutableList<Routine> = ArrayList()
    lateinit var context : Context
    var onRoutineClick : ((Routine) -> Unit)? = null
    var onEditClick : ((Routine) -> Unit)? = null
    var onDeleteClick : ((Routine) -> Unit)? = null

    fun MyListsViewAdapter(myRoutines : MutableList<Routine>, context: Context){
        this.myRoutines = myRoutines
        this.context = context
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val location: TextView
        val description : TextView
        val follows : TextView
        val editButton : ImageButton
        val deleteButton : ImageButton

        init {
            title = view.findViewById(R.id.myroutine_card_title)
            location = view.findViewById(R.id.myroutine_card_location)
            description = view.findViewById(R.id.myroutine_card_description)
            follows = view.findViewById(R.id.myroutine_card_follows)
            editButton = view.findViewById(R.id.follow_routine)
            deleteButton = view.findViewById(R.id.myroutine_delete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.myroutines_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = myRoutines.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val routine = myRoutines[position]
        holder.title.text = myRoutines[position].name
        holder.location.text = myRoutines[position].city + ", " + myRoutines[position].country
        holder.description.text = myRoutines[position].routine_description
        holder.follows.text = myRoutines[position].followers.toString()

        holder.itemView.setOnClickListener {
            onRoutineClick?.invoke(routine)
        }

        holder.editButton.setOnClickListener {
            onEditClick?.invoke(routine)
        }

        holder.deleteButton.setOnClickListener {
            onDeleteClick?.invoke(routine)
        }
    }
}