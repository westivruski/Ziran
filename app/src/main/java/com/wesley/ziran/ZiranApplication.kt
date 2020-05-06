package com.wesley.ziran

import android.app.Application

class ZiranApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        EventRepository.initialize(this)
    }
}