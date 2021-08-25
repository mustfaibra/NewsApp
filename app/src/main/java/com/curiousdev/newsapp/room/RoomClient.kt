package com.curiousdev.newsapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.curiousdev.newsapp.model.Article
import kotlin.synchronized

@Database(entities = [Article::class],version=1,exportSchema = false)
abstract class RoomClient : RoomDatabase() {
    abstract fun getDao() : RoomDao
    // create a companion object so that one instance is used through all our app
    companion object{
        @Volatile private var instance : RoomClient? = null
        private val DATABASE_NAME = "news"

        fun getRoomInstance(context: Context) : RoomClient{
            val currentInstance = instance
            if (currentInstance != null) {
                return currentInstance
            }
            // assure that same instance is used whenever is requested !
            synchronized(this){
                val newInstance = Room.databaseBuilder(context,RoomClient::class.java, DATABASE_NAME).build()
                instance = newInstance
                return newInstance
            }
        }
    }
}