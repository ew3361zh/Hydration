package com.nikosnockoffs.android.hydration

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

// will represent a row in the db and also will be used to make objects in the code
// Room will be able to use this to create a db table and datatypes
// Changed to data class because comes with too-string method
@Entity
data class WaterRecord(
    @PrimaryKey
    @NonNull
    var day: String,
    @NonNull
    val glasses: Int
    )