package net.smartgekko.recyclerdiary.views.adapters

import net.smartgekko.recyclerdiary.model.database.entities.Event

interface OnListItemClickListener {
    fun onItemClick(event: Event)
}