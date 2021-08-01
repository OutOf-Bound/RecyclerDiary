package net.smartgekko.recyclerdiary.model.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*
@Entity(tableName = "Events")
class Event (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var order_id: Int,
    val date: String,
    val time: String,
    val title: String,
    val description: String,
    val importance: Int
)