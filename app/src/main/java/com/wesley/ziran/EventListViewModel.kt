package com.wesley.ziran

import androidx.lifecycle.ViewModel

class EventListViewModel : ViewModel(){

    private val eventRepository = EventRepository.get()
    val eventListLiveData = eventRepository.getEvents()

    fun addEvent(event: Event) {
        eventRepository.addEvent(event)
    }

    internal fun checkValidLogin(username: String, password: String): Boolean {
        return eventRepository.isValidAccount(username, password)
    }
    internal fun createUser( username: String, password: String) {
        eventRepository.insertUser(username, password)
    }
    internal fun createEvent( event: Event) {
        eventRepository.addEvent(event)
    }

}