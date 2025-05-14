package com.example.supabasetest.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object PermissionCheck

@Serializable
object List

@Serializable
object NewStudent

@Serializable
data class Detail(val id: String)