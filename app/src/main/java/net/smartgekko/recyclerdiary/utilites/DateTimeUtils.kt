package net.smartgekko.recyclerdiary.utilites

import net.smartgekko.recyclerdiary.R
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    var daysOfWeek: List<String> = arrayListOf()
    init {
        daysOfWeek = arrayListOf(
            MyApplication.getAppContext().getString(R.string.day1),
            MyApplication.getAppContext().getString(R.string.day2),
            MyApplication.getAppContext().getString(R.string.day3),
            MyApplication.getAppContext().getString(R.string.day4),
            MyApplication.getAppContext().getString(R.string.day5),
            MyApplication.getAppContext().getString(R.string.day6),
            MyApplication.getAppContext().getString(R.string.day7)
        )
    }

    public fun getDateAsString(date:Date): String{
        val sdf = SimpleDateFormat("dd-M-yyyy")
        val currentDate = sdf.format(Date())
        return currentDate
    }

    public fun getTimeAsString(time:Date): String{
        val sdf = SimpleDateFormat("hh:mm")
        val currentDate = sdf.format(Date())
        return currentDate
    }

    public fun getDayNameByNumber(num:Int) = daysOfWeek[num]
}