package com.example.supabasetest

import android.app.Application
import com.example.supabasetest.model.MySupabaseClient

class MyApp: Application() {
    companion object {
        lateinit var database: MySupabaseClient
    }

    override fun onCreate() {
        super.onCreate()
        database = MySupabaseClient()
    }
}