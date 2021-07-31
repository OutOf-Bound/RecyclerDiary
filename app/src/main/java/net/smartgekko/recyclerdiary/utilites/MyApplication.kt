package net.smartgekko.recyclerdiary.utilites
import android.content.Context
import androidx.room.Room
import net.smartgekko.recyclerdiary.model.database.database.MainDatabase


class MyApplication(contextArg: Context) {
    var context: Context = contextArg

    fun create() {
        setAppContext(context, this)
    }

    companion object {

        private lateinit var contextIn: Context
        private var database: MainDatabase? = null
        private var instance: MyApplication? = null

        fun setAppContext(context: Context, inst: MyApplication) {
            contextIn = context
            instance = inst
            database = Room.databaseBuilder(context, MainDatabase::class.java, "database")
                .allowMainThreadQueries()
                .build()
        }

        fun getAppContext(): Context = contextIn
        fun getDatabase(): MainDatabase? = database
    }
}