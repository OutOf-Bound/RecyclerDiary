package net.smartgekko.recyclerdiary.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import net.smartgekko.recyclerdiary.R
import net.smartgekko.recyclerdiary.model.database.entities.Event
import net.smartgekko.recyclerdiary.utilites.DateTimeUtils
import net.smartgekko.recyclerdiary.utilites.TimeList
import net.smartgekko.recyclerdiary.viewmodels.AppState
import net.smartgekko.recyclerdiary.viewmodels.HomeViewModel
import net.smartgekko.recyclerdiary.views.adapters.ItemTouchHelperCallback
import net.smartgekko.recyclerdiary.views.adapters.OnListItemClickListener
import net.smartgekko.recyclerdiary.views.adapters.RecyclerActivityAdapter
import java.util.*

class HomeFragment : Fragment(), OnListItemClickListener {
    private lateinit var viewModel: HomeViewModel
    private lateinit var eventsList: RecyclerView
    private lateinit var eventsAdapter: RecyclerActivityAdapter
    private lateinit var saveButton: ConstraintLayout
    lateinit var itemTouchHelper: ItemTouchHelper
    val outEventsList: MutableList<Pair<Event, Boolean>> = arrayListOf()
    var eventListStartState: Int = 0


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

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        lifecycle.addObserver(viewModel)

        eventsList = requireView().findViewById(R.id.homeRecycler)
        eventsAdapter = RecyclerActivityAdapter(arrayListOf())
        eventsList.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        eventsList.adapter = eventsAdapter

        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(eventsAdapter))
        itemTouchHelper.attachToRecyclerView(eventsList)

        saveButton = requireView().findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            saveEventsToDB()
        }

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

            }
            is AppState.SuccessDeleteEventsByDate -> {
                var counter = 0
                for (event in outEventsList) {
                    if (!event.first.title.isBlank()) {
                        counter++
                        event.first.order_id = counter
                        viewModel.saveEvent(event.first)
                    }
                }
                loadingLayout?.visibility = View.GONE

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

        val timeList = TimeList.timeList

        for (i in 0..timeList.size - 1) {
            outEventsList.add(
                outEventsList.size,
                Pair(
                    Event(0, 0, DateTimeUtils.getDateAsString(Date()), timeList[i], "", "", 0),
                    false
                )
            )
            for (j in 0..events.size - 1) {
                if (events[j].time.equals(timeList[i])) {
                    outEventsList.add(outEventsList.size, Pair(events[j], false))
                }
            }
        }
        eventListStartState = outEventsList.hashCode()
        eventsAdapter.updateEvents(outEventsList)
    }

    override fun onItemClick(event: Event) {
        // addEvent(event.time,event.date)
    }

    private fun getTodayEvents() {
        viewModel.getTodayEvents(DateTimeUtils.getDateAsString(Date()))
    }

    private fun saveEventsToDB() {
        viewModel.deleteEventsByDate(DateTimeUtils.getDateAsString(Date()))
    }
}