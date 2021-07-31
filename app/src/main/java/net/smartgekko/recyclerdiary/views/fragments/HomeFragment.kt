package net.smartgekko.recyclerdiary.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import net.smartgekko.recyclerdiary.R
import net.smartgekko.recyclerdiary.model.database.entities.Event
import net.smartgekko.recyclerdiary.utilites.DateTimeUtils
import net.smartgekko.recyclerdiary.utilites.TimeList
import net.smartgekko.recyclerdiary.viewmodels.AppState
import net.smartgekko.recyclerdiary.viewmodels.HomeViewModel
import net.smartgekko.recyclerdiary.views.adapters.RecyclerActivityAdapter
import java.util.*

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var eventsList: RecyclerView
    private lateinit var eventsAdapter: RecyclerActivityAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        lifecycle.addObserver(viewModel)
        viewModel.getTodayEvents(DateTimeUtils.getDateAsString(Date()))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        eventsList = view.findViewById(R.id.homeRecycler)
        eventsAdapter = RecyclerActivityAdapter(listOf())
        eventsList.adapter = eventsAdapter
        return view
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
        var counter =0
        for(i in 0..events.size-1){
            while(events[i].time!=timeList[counter]){
                outEventsList.add(Event(0,"",timeList[counter],"","",0))
                counter++
            }
            outEventsList.add(events[i])
        }
        eventsAdapter.updateEvents(outEventsList)
    }
}