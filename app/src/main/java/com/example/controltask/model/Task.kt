package com.example.controltask.model

import android.os.Parcelable
import com.example.controltask.helper.FirebaseHelper
import com.google.firebase.Firebase
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    var id: String = "",
    var description: String = "",
    var status: Int = 0
) : Parcelable {
    init {
        this.id = FirebaseHelper.getDatabase().push().key ?: ""
    }
}
