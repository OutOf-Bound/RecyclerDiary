package net.smartgekko.recyclerdiary.repositories

import net.smartgekko.recyclerdiary.model.database.entities.Event
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
        onError: (t: Throwable) -> Unit
    ) {
        try {
            onSuccess.invoke(diaryDao.getEventsCountByDate(date)!!)
        } catch (e: Exception) {
            val t = Throwable("Events count getting failed")
            onError.invoke(t)
        }
    }

    fun getEventsByDate(
        date: String,
        onSuccess: (events: List<Event>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        try {
            onSuccess.invoke(diaryDao.getEventsByDate(date)!!)
        } catch (e: Exception) {
            val t = Throwable("Events getting failed")
            onError.invoke(t)
        }
    }

    fun addToEvents(
        event: Event?,
        onSuccess: () -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        try {
            diaryDao.insert(event)
            onSuccess.invoke()
        } catch (e: Exception) {
            val t = Throwable("Events adding failed")
            onError.invoke(t)
        }
    }

    fun removeFromEvents(
        event: Event?,
        onSuccess: () -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        try {
            diaryDao.delete(event)
            onSuccess.invoke()
        } catch (e: Exception) {
            val t = Throwable("Events deleting failed")
            onError.invoke(t)
        }
    }

    fun removeFromEventsByDate(
        date: String?,
        onSuccess: (code:Int) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        try {
            diaryDao.deleteByDate(date!!)
            onSuccess.invoke(1)
        } catch (e: Exception) {
            val t = Throwable("Events deleting by date failed")
            onError.invoke(t)
        }
    }
}