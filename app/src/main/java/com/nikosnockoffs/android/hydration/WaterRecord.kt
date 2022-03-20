package com.nikosnockoffs.android.hydration

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

// will represent a row in the db and also will be used to make objects in the code
// Room will be able to use this to create a db table and datatypes
// Changed to data class because comes with too-string method
@Entity
class WaterRecord(
    @PrimaryKey
    @NonNull
    val day: String,
    glasses: Int
    ) {

    companion object {
        const val MAX_GLASSES = 5
    }

    var glasses: Int = glasses
    set(value) {
        if (value < 0 || value > MAX_GLASSES) {
            throw IllegalArgumentException("Glasses must be between 0 and $MAX_GLASSES")
        }
        field = value
    }

    override fun toString(): String {
        return "Day=$day, Glasses=$glasses"
    }
}