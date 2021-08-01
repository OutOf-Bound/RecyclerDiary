package net.smartgekko.recyclerdiary.model.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*
@Entity(tableName = "Events")
class Event (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: String,
    val time: String,
    val title: String,
    val description: String,
    val importance: Int
)