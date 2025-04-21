package com.example.supabasetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.supabasetest.ui.navigation.NavigationWrapper
import com.example.supabasetest.ui.theme.SupabaseTestTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SupabaseTestTheme {
                NavigationWrapper()
            }
        }
    }
}

