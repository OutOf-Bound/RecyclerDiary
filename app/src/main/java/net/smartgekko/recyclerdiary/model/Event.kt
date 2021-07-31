package net.smartgekko.recyclerdiary.model

import java.util.*

class Event (
    val id: Long,
    val date: String,
    val time: String,
    val title: String,
    val description: String,
    val importance: Int
)