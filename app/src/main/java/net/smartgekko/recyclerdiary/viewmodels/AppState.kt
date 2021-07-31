package net.smartgekko.recyclerdiary.viewmodels

import net.smartgekko.recyclerdiary.model.Event

sealed class AppState {
    data class SuccessEventsCount(val eventsCount: Int) : AppState()
    data class SuccessEventsByDate(val events: List<Event>) : AppState()
    data class SuccessEvent(val event: Event) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}