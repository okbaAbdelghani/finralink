package com.oktech.finralink.data.models

data class Message (
    val id: String,
    val senderId: String,
    val text: String,
    val timestamp: Long
)