package com.example.testapplication

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Skill(val lang: String, val days: Int, val modificator: Char, var time: Int = 0) :
    Parcelable {
    init {
        time = days * convertModificator(modificator)
    }

    var exp = "${days}$modificator"
}

data class Filter(
    val days: Int,
    val modificator: Char,
    var time: Int = 0,
    val selectAll: Boolean = false
) {
    init {
        time = days * convertModificator(modificator)
    }

    var label = "<${days}$modificator"
}

public fun convertModificator(modificator: Char): Int {
    val timeModifiers = hashMapOf(
        'd' to 1, 'w' to 7, 'm' to 30, 'y' to 365
    )
    return timeModifiers[modificator] ?: 0
}
