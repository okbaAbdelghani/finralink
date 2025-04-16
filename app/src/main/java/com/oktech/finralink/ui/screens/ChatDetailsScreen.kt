package com.oktech.finralink.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oktech.finralink.R
import com.oktech.finralink.data.models.Message

@Composable
fun ChatDetailsScreen(contactName: String) {
    val currentUserId = "user_123"

    // Dummy messages
    val messages = listOf(
        Message(id = "1", senderId = "contact_456", text = "Hey, are you there?", timestamp = 0L),
        Message(id = "2", senderId = currentUserId, text = "Yes! What's up?", timestamp = 0L),
        Message(
            id = "3",
            senderId = "contact_456",
            text = "Can you send me the file?",
            timestamp = 0L
        ),
        Message(id = "4", senderId = currentUserId, text = "Sure, give me a sec.", timestamp = 0L),
        Message(id = "5", senderId = "contact_456", text = "Thanks!", timestamp = 0L)
    )

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ConvHeader(contactName = contactName, isOnline = true)
                ConvBody(messages = messages, currentUserId = currentUserId) {

                }
            }
        })
}

@Composable
fun ConvHeader(contactName: String, isOnline: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back"
            )
        }

        Spacer(modifier = Modifier.width(5.dp))

        Box {
            Image(
                painter = painterResource(R.drawable.profile),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            if (isOnline) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(Color.Green, CircleShape)
                        .align(Alignment.BottomEnd)
                        .border(2.dp, Color.White, CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = contactName,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = if (isOnline) {
                    "Online"
                } else {
                    "Offline"
                },
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
        }

        IconButton(
            onClick = {},
            modifier = Modifier
                .border(1.dp, Color.Gray, CircleShape)
                .size(38.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Call,
                contentDescription = "",
            )
        }
    }
}

@Composable
fun ConvBody(
    messages: List<Message>,
    currentUserId: String,
    onSend: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            reverseLayout = true
        ) {
            items(messages.reversed()) { message ->
                MessageBubble(message = message, isCurrentUser = message.senderId == currentUserId)
            }
        }
        MessageInput(onSend = onSend)
    }
}

@Composable
fun MessageBubble(message: Message, isCurrentUser: Boolean) {
    Row(
        horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (isCurrentUser) Color(0xFFD0F0C0) else Color(0xFFE0E0E0),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        ) {
            Text(text = message.text, color = Color.Black)
        }
    }
}


@Composable
fun MessageInput(onSend: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text(text = "Type a message...") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = {
            if (text.isNotBlank()) {
                onSend(text)
                text = ""
            }
        }) {
            Icon(imageVector = Icons.Default.Send, contentDescription = "Send")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewChatDetailsScreen() {
    return ChatDetailsScreen("Larry")
}