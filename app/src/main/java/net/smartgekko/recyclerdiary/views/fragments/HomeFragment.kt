package net.smartgekko.recyclerdiary.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import net.smartgekko.recyclerdiary.R
import net.smartgekko.recyclerdiary.model.database.entities.Event
import net.smartgekko.recyclerdiary.utilites.DateTimeUtils
import net.smartgekko.recyclerdiary.utilites.TimeList
import net.smartgekko.recyclerdiary.viewmodels.AppState
import net.smartgekko.recyclerdiary.viewmodels.HomeViewModel
import net.smartgekko.recyclerdiary.views.adapters.OnListItemClickListener
import net.smartgekko.recyclerdiary.views.adapters.RecyclerActivityAdapter
import java.util.*

class HomeFragment : Fragment(),OnListItemClickListener {
    private lateinit var viewModel: HomeViewModel
    private lateinit var eventsList: RecyclerView
    private lateinit var eventsAdapter: RecyclerActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)


        return view
    }

    override fun onStart() {
        super.onStart()
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        lifecycle.addObserver(viewModel)
        eventsList = requireView().findViewById(R.id.homeRecycler)
        eventsAdapter = RecyclerActivityAdapter(
            arrayListOf(),this )
        eventsList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        eventsList.adapter = eventsAdapter
        getTodayEvents()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply {}
    }




    private fun renderData(appState: AppState) {
        val loadingLayout: ProgressBar? = view?.findViewById(R.id.loadingLayout)

        when (appState) {
            is AppState.SuccessEventsByDate -> {
                val events = appState.events
                loadingLayout?.visibility = View.GONE
                setData(events)
            }
            is AppState.SuccessEvent -> {
                loadingLayout?.visibility = View.GONE
                getTodayEvents()
            }
            is AppState.Loading -> {
                loadingLayout?.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                loadingLayout?.visibility = View.GONE
            }
        }
    }

    private fun setData(events: List<Event>) {
        val outEventsList: ArrayList<Event> = arrayListOf()
        val timeList = TimeList.timeList

            for(i in 0..timeList.size-1){
                outEventsList.add(Event(0,DateTimeUtils.getDateAsString(Date()),timeList[i],"","",0))
                for(j in 0..events.size-1){
                    if(events[j].time.equals(timeList[i])) {
                            outEventsList.add(events[j])
                        }
                }
            }
        eventsAdapter.updateEvents(outEventsList)
    }

    override fun onItemClick(event: Event) {
        addEvent(event.time,event.date)
    }

    public fun addEvent(time:String,date:String){
        viewModel.addTodayEvents(Event(0,date,time,"New Event","Event description here",1))
    }

    private fun getTodayEvents(){
        viewModel.getTodayEvents(DateTimeUtils.getDateAsString(Date()))
    }
}