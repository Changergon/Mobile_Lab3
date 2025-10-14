package com.example.lab2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val email: String,
    val password: String
    // Можно добавить и другие поля, если нужно, например, возраст, пол из SignUpActivity
) : Parcelable
