package com.wesley.ziran

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Event(@PrimaryKey val id :UUID = UUID.randomUUID(),
                 var name:String ="",
                 var date : Date = Date(),
                 var descrpition :String = "",
                 var isExpired: Boolean = false)