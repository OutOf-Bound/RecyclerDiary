package net.smartgekko.recyclerdiary.viewmodels

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.smartgekko.recyclerdiary.model.database.entities.Event
import net.smartgekko.recyclerdiary.repositories.MainRepository

class HomeViewModel(private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()) :
    ViewModel(), LifecycleObserver {

    fun getLiveData() = liveDataToObserve
    fun getTodayEvents(date: String) = getTodayEventsFromDbSource(date)

    private fun getTodayEventsFromDbSource(date: String) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            fun onEventsFetched(events: List<Event>) {
                liveDataToObserve.postValue(AppState.SuccessEventsByDate(events))
            }

            fun onError(throwable: Throwable) {
                liveDataToObserve.postValue(AppState.Error(throwable))
            }

            MainRepository. getEventsByDate(
                date,
                onSuccess = ::onEventsFetched,
                onError = ::onError
            )
        }.start()
    }
}