package com.wesley.ziran.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wesley.ziran.Event
import com.wesley.ziran.UserAccount

@Database(entities = [ UserAccount::class, Event::class ], version=1, exportSchema = false)
@TypeConverters(EventTypeConverter::class)
abstract class EventDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao

   // abstract fun userAccountDao(): UserDao

    //abstract fun userEventDao(): UserEventDao

}