package com.oktech.finralink.data.models

data class Conversation(
    val id: Int,
    val contactName: String,
    val sender: String,
    val lastMessage: String,
    val time: String,
    val unreadCount: Int,
    val isSeen: Boolean,
    val isOnline: Boolean,
    val profileImageRes: Int
)