package com.example.dars_test_app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Question(
    val question: String,
    val answers: Array<Answer>
) : Parcelable