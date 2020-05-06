package com.wesley.ziran.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wesley.ziran.Event
import com.wesley.ziran.UserAccount
import com.wesley.ziran.UserEvent
import java.util.*

@Dao
interface EventDao {

    @Query("SELECT * FROM event")
    fun getEvents(): LiveData<List<Event>>

    @Query("SELECT * FROM event WHERE id=(:id)")
    fun getEvent(id: UUID): LiveData<Event?>

    @Update
    fun updateEvent(event: Event)

    @Insert
    fun addEvent(event: Event)

    @Transaction
    @Query("SELECT * FROM useraccounts")
    fun getUserEvents(): List<UserEvent>

  /*  @Transaction
    @Query("SELECT * FROM useraccounts")
    fun getUsersEvent(): List<UserEvent>*/

    @Insert
    fun insertUser(account: UserAccount)

    @Query("SELECT * FROM useraccounts WHERE userId=(:userId)")
    fun getAccount(userId: Int):LiveData<UserAccount?>

 /*   @Query("SELECT * FROM useraccounts WHERE username=(:username)")
    fun validAccount(username: String): UserAccount*/

    @Query("SELECT * FROM useraccounts WHERE useraccounts.userId LIKE :username")
    fun validAccount(username: String): UserAccount
}