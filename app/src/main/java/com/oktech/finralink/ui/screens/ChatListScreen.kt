package com.oktech.finralink.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.oktech.finralink.R
import com.oktech.finralink.data.models.Conversation
import com.oktech.finralink.ui.theme.FinraLinkTheme

@Composable
fun ChatListScreen(navController: NavController) {

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
                Header()
                val convs = getDummyConversations()
                LazyColumn {
                    items(convs) { conv ->
                        ConversationItem(
                            profilePicture = painterResource(id = conv.profileImageRes),
                            contactName = conv.contactName,
                            lastMessage = conv.lastMessage,
                            sender = conv.sender,
                            time = conv.time,
                            unreadCount = conv.unreadCount,
                            isSeen = conv.isSeen,
                            isOnline = conv.isOnline,
                            onConvClick = {
                                navController.navigate("chat_details/${conv.contactName}")
                            }
                        )
                    }
                }
            }
        }
    )
}


@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)           // White background
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(text = "FinraLink Chat")
    }
}

@Composable
fun ConversationItem(
    profilePicture: Painter,
    contactName: String,
    lastMessage: String,
    sender: String,
    time: String,
    unreadCount: Int,
    isSeen: Boolean,
    isOnline: Boolean,
    onConvClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onConvClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            Image(
                painter = profilePicture,
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

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = contactName,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
            Text(
                text = "$sender: $lastMessage",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isSeen) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Seen",
                        tint = Color(0xFF6D4AFF),
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = time,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            if (unreadCount > 0) {
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .sizeIn(minWidth = 24.dp)
                        .background(Color.Red, shape = CircleShape)
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = unreadCount.toString(),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewConversationItem() {
    val profileImage = painterResource(id = R.drawable.profile)
    FinraLinkTheme {
        ConversationItem(
            profilePicture = profileImage,
            contactName = "Photographers",
            lastMessage = "Hmm, are you sure?",
            sender = "@Philippe",
            time = "10:16 PM",
            unreadCount = 80,
            isSeen = true,
            isOnline = true
        ) {

        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewChatList() {
    ChatListScreen(navController = rememberNavController())
}

fun getDummyConversations(): List<Conversation> {
    return listOf(
        Conversation(
            id = 1,
            contactName = "Photographers",
            sender = "@Philippe",
            lastMessage = "Hmm, are you sure?",
            time = "10:16 PM",
            unreadCount = 80,
            isSeen = true,
            isOnline = true,
            profileImageRes = R.drawable.profile
        ),
        Conversation(
            id = 2,
            contactName = "Anna",
            sender = "@Anna",
            lastMessage = "Let’s meet tomorrow morning",
            time = "09:45 PM",
            unreadCount = 2,
            isSeen = false,
            isOnline = false,
            profileImageRes = R.drawable.profile
        ),
        Conversation(
            id = 3,
            contactName = "Team Chat",
            sender = "@David",
            lastMessage = "The meeting is rescheduled",
            time = "08:30 PM",
            unreadCount = 0,
            isSeen = true,
            isOnline = true,
            profileImageRes = R.drawable.profile
        ),
        Conversation(
            id = 4,
            contactName = "Mom",
            sender = "@Mom",
            lastMessage = "Dinner is ready!",
            time = "07:00 PM",
            unreadCount = 5,
            isSeen = false,
            isOnline = true,
            profileImageRes = R.drawable.profile
        ),
        Conversation(
            id = 5,
            contactName = "Alex",
            sender = "@Alex",
            lastMessage = "Alright, see you later.",
            time = "06:10 PM",
            unreadCount = 0,
            isSeen = true,
            isOnline = false,
            profileImageRes = R.drawable.profile
        )
    )
}