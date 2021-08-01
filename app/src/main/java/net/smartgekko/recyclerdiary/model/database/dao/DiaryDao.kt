package net.smartgekko.recyclerdiary.model.database.dao

import androidx.room.*
import net.smartgekko.recyclerdiary.model.database.entities.Event

@Dao
interface DiaryDao {
    @Query("SELECT * FROM Events")
    fun getAllEvents(): List<Event>

    @Query("SELECT * FROM Events WHERE id = :id")
    fun getEventById(id: Long): Event?

    @Query("SELECT * FROM Events WHERE date = :date ORDER BY time")
    fun getEventsByDate(date: String): List<Event>?

    @Query("SELECT count(id) FROM Events WHERE date = :date")
    fun getEventsCountByDate(date: String): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: Event?)

    @Update
    fun update(event: Event?)

    @Delete
    fun delete(event: Event?)
}