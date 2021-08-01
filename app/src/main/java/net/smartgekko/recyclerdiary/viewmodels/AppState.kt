package net.smartgekko.recyclerdiary.viewmodels

import net.smartgekko.recyclerdiary.model.database.entities.Event

sealed class AppState {
    data class SuccessEventsCount(val eventsCount: Int) : AppState()
    data class SuccessEventsByDate(val events: List<Event>) : AppState()
    data class SuccessEvent(val result: Int): AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}