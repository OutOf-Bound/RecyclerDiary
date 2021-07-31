package net.smartgekko.recyclerdiary.repositories

import net.smartgekko.recyclerdiary.model.Event
import net.smartgekko.recyclerdiary.model.database.dao.DiaryDao
import net.smartgekko.recyclerdiary.utilites.MyApplication

object MainRepository {
    private var diaryDao: DiaryDao

    init {
        val db = MyApplication.getDatabase()
        diaryDao = db?.diaryDao()!!
    }

    fun getEventsCountByDate(
        date: String,
        onSuccess: (eventsCount: Int) -> Unit,
        onError: () -> Unit
    ) {
        try {
            onSuccess.invoke(diaryDao.getEventsCountByDate(date)!!)
        } catch (e: Exception) {
            onError.invoke()
        }
    }

    fun getEventsByDate(
        date: String,
        onSuccess: (events: List<Event>) -> Unit,
        onError: () -> Unit
    ) {
        try {
            onSuccess.invoke(diaryDao.getEventsByDate(date)!!)
        } catch (e: Exception) {
            onError.invoke()
        }
    }

    fun addToEvents(
        event: Event?,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        try {
            diaryDao.insert(event)
            onSuccess.invoke()
        } catch (e: Exception) {
            onError.invoke()
        }
    }

    fun removeFromNotes(
        event: Event?,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        try {
            diaryDao.delete(event)
            onSuccess.invoke()
        } catch (e: Exception) {
            onError.invoke()
        }
    }
}