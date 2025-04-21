package com.example.supabasetest

import android.app.Application
import com.example.supabasetest.model.MySupabaseClient

class MyApp: Application() {
    companion object {
        lateinit var database: MySupabaseClient
    }

    override fun onCreate() {
        super.onCreate()
        database = MySupabaseClient(
            supabaseUrl = "https://aobflzinjcljzqpxpcxs.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFvYmZsemluamNsanpxcHhwY3hzIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDQ5NzA1MjUsImV4cCI6MjA2MDU0NjUyNX0.0BZ0-bEdYED5R_VY5rGf3QI0D84nyz19GxUMte-whko"
        )
    }
}