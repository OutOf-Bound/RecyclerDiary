package net.smartgekko.recyclerdiary.views.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.MotionEventCompat
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import net.smartgekko.recyclerdiary.R
import net.smartgekko.recyclerdiary.model.database.entities.Event
import net.smartgekko.recyclerdiary.utilites.DateTimeUtils
import net.smartgekko.recyclerdiary.utilites.ITEM_TYPE_EVENT
import net.smartgekko.recyclerdiary.utilites.ITEM_TYPE_TIME
import net.smartgekko.recyclerdiary.viewmodels.HomeViewModel
import java.util.*
import kotlin.collections.ArrayList


class RecyclerActivityAdapter(
    private var events: MutableList<Pair<Event, Boolean>>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter {

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
        return if (events[position].first.title.isNullOrBlank()) ITEM_TYPE_TIME else ITEM_TYPE_EVENT
    }

    inner class EventViewHolder(view: View) : RecyclerView.ViewHolder(view),ItemTouchHelperViewHolder {
        fun bind(event: Pair<Event, Boolean>) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.eventTitle).text = event.first.title
                with(itemView.findViewById<TextView>(R.id.eventDescription)) {
                    text = event.first.description
                    visibility = if (event.second) View.VISIBLE else View.GONE
                }
                itemView.findViewById<ConstraintLayout>(R.id.textLayout).setOnClickListener {
                    toggleText()
                }
            }
        }
        private fun toggleText() {
            events[layoutPosition] = events[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }
        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }

    }

    inner class TimeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(event: Pair<Event, Boolean>) {
            itemView.findViewById<TextView>(R.id.timeText).text = event.first.time
            itemView.findViewById<ImageView>(R.id.eventAdd).setOnClickListener {
               // onListItemClickListener.onItemClick(event)
                if(layoutPosition<events.size-1){
                    events.add(layoutPosition+1,Pair(Event(0,
                        DateTimeUtils.getDateAsString(Date()),event.first.time,"New event","Event description here",0),false))
                } else {
                    events.add(0,Pair(Event(0,
                        DateTimeUtils.getDateAsString(Date()),event.first.time,"New event","Event description here",0),false))
                }
                notifyItemInserted(layoutPosition)
            }
        }
    }

    fun updateEvents(eventsList: MutableList<Pair<Event, Boolean>>) {
        events.clear()
        events.addAll(eventsList)
        notifyDataSetChanged()
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        events.removeAt(fromPosition).apply {
            events.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)

    }

    override fun onItemDismiss(position: Int) {
        events.removeAt(position)
        notifyItemRemoved(position)
    }


}
