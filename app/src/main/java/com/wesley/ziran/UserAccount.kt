package com.wesley.ziran

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "useraccounts")
data class UserAccount(
    @PrimaryKey
     var userId: String,
     var password: String)
{
 /*   @PrimaryKey(autoGenerate = true)
    var userId: Int = 0*/
}