package net.smartgekko.recyclerdiary.viewmodels

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.smartgekko.recyclerdiary.model.database.entities.Event
import net.smartgekko.recyclerdiary.repositories.MainRepository
import kotlin.random.Random

class HomeViewModel(private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()) :
    ViewModel(), LifecycleObserver {

    fun getLiveData() = liveDataToObserve
    fun getTodayEvents(date: String) = getTodayEventsFromDbSource(date)
    fun addTodayEvents(event: Event) {
        addNewEvent(event)
    }
    fun saveEvent(event: Event) {
        addNewEvent(event)
    }
    fun deleteEventsByDate(date: String) {
        deleteEventsByDatefromDB(date)
    }


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
    private fun addNewEvent(event: Event) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            fun onEventAdded() {
                liveDataToObserve.postValue(AppState.SuccessEvent(1))
            }

            fun onError(throwable: Throwable) {
                liveDataToObserve.postValue(AppState.Error(throwable))
            }

            MainRepository.addToEvents(
                event,
                onSuccess = ::onEventAdded,
                onError = ::onError
            )
        }.start()
    }
    private fun deleteEventsByDatefromDB(date: String) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            fun onEventsDeleted(code:Int) {
                liveDataToObserve.postValue(AppState.SuccessDeleteEventsByDate(code))
            }

            fun onError(throwable: Throwable) {
                liveDataToObserve.postValue(AppState.Error(throwable))
            }

            MainRepository.removeFromEventsByDate(
                date,
                onSuccess = ::onEventsDeleted,
                onError = ::onError
            )
        }.start()
    }

}