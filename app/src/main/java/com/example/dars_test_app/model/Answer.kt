package com.example.dars_test_app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Answer(
    val isCorrect: Boolean,
    val answer: String
) : Parcelable