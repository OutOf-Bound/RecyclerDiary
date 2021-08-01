package net.smartgekko.recyclerdiary.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import net.smartgekko.recyclerdiary.R
import net.smartgekko.recyclerdiary.model.database.entities.Event
import net.smartgekko.recyclerdiary.utilites.ITEM_TYPE_EVENT
import net.smartgekko.recyclerdiary.utilites.ITEM_TYPE_TIME
import net.smartgekko.recyclerdiary.viewmodels.HomeViewModel


class RecyclerActivityAdapter(
    private var events: ArrayList<Event>,
    private var onListItemClickListener: OnListItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == ITEM_TYPE_TIME) {
            TimeViewHolder(
                inflater.inflate(R.layout.time_item, parent, false) as View
            )
        } else {
            EventViewHolder(
                inflater.inflate(R.layout.event_item, parent, false) as View
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ITEM_TYPE_TIME) {
            holder as TimeViewHolder
            holder.bind(events[position])
        } else {
            holder as EventViewHolder
            holder.bind(events[position])
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (events[position].title.isNullOrBlank()) ITEM_TYPE_TIME else ITEM_TYPE_EVENT
    }

    inner class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(event: Event) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.eventTitle).text = event.title
                itemView.findViewById<TextView>(R.id.eventDescription).text = event.description
            }
        }
    }

    inner class TimeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(event: Event) {
            itemView.findViewById<TextView>(R.id.timeText).text = event.time
            itemView.findViewById<ImageView>(R.id.eventAdd).setOnClickListener {
                onListItemClickListener.onItemClick(event)
            }
        }
    }

    fun updateEvents(eventsList: ArrayList<Event>) {
        events.clear()
        events.addAll(eventsList)
        notifyDataSetChanged()
    }
}
