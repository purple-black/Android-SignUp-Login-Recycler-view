package com.example.todolistapp

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class Todos(var newtodo: String? = null, @Exclude @JvmField
    var key : String? = null)


