package net.smartgekko.recyclerdiary.model.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import net.smartgekko.recyclerdiary.model.database.dao.DiaryDao
import net.smartgekko.recyclerdiary.model.database.entities.Event

@Database(entities = arrayOf(Event::class), version = 1)
abstract class MainDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao?
}