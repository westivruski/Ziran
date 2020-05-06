package com.wesley.ziran

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.wesley.ziran.database.EventDao
import com.wesley.ziran.database.EventDatabase
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "crime-database"

class EventRepository private constructor(context: Context) {


    private val database : EventDatabase = Room.databaseBuilder(
        context.applicationContext,
        EventDatabase::class.java,
        DATABASE_NAME
    )
        .allowMainThreadQueries()
        .build()

    private val eventDao = database.eventDao()

    private val executor = Executors.newSingleThreadExecutor()

    fun getEvents(): LiveData<List<Event>> = eventDao.getEvents()

    fun getEvent(id: UUID): LiveData<Event?> = eventDao.getEvent(id)


    fun updateEvent(event: Event) {
        executor.execute {
            eventDao.updateEvent(event)
        }
    }

    fun addEvent(event: Event) {
        executor.execute {
            eventDao.addEvent(event)
        }
    }



    fun getAccount(userId: Int): LiveData<UserAccount?> = eventDao.getAccount(userId)

    fun insertUser(username: String, password: String) {
        val account = UserAccount( username, password)
        eventDao.insertUser(account)
    }


    fun isValidAccount(username: String, password: String): Boolean {

        val userAccount = eventDao.validAccount(username)
        return userAccount.password == password
    }

    companion object {
        private var INSTANCE: EventRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = EventRepository(context)
            }
        }

        fun get(): EventRepository {
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}

