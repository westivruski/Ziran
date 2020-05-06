package com.wesley.ziran

import androidx.room.Embedded
import androidx.room.Relation

data class UserEvent(
    @Embedded val useraccount: UserAccount,
    @Relation(
        parentColumn = "userId",
        entityColumn = "id"
    )
    val events: List<Event>
)
