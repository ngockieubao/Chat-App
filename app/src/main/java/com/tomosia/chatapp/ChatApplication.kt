package com.tomosia.chatapp

import android.app.Application
import com.tomosia.chatapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ChatApplication : Application() {
    companion object {
        private var mInstant: ChatApplication? = null
        val instant
            get() = mInstant
    }

    override fun onCreate() {
        super.onCreate()
        mInstant = this
        startKoin {
            androidContext(this@ChatApplication)
            modules(viewModelModule)
        }
    }
}