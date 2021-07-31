package net.smartgekko.recyclerdiary.model.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*
@Entity(tableName = "Events", indices = arrayOf(Index(value = ["id"], unique = true)))
class Event (
    @PrimaryKey
    val id: Long,
    val date: String,
    val time: String,
    val title: String,
    val description: String,
    val importance: Int
)