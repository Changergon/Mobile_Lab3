package com.example.lab3

data class HomeItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val imageUrl: String? = null,
    val time: String? = null
)
