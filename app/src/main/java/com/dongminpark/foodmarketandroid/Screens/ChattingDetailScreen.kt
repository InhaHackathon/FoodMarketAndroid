package com.dongminpark.foodmarketandroid.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dongminpark.foodmarketandroid.Button.BackButton
import com.dongminpark.foodmarketandroid.Format.ImageFormat
import com.dongminpark.foodmarketandroid.Format.TextFormat
import com.dongminpark.foodmarketandroid.Model.ChatMessage
import com.dongminpark.foodmarketandroid.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChattingChattingDetailScreen(navController: NavController, route: String) {
    val textFieldColors = TextFieldDefaults.textFieldColors(
        backgroundColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )
    val chatMessages = remember {
        listOf(
            ChatMessage("other", "안녕하세요 물품 구매하고싶어서 연락드렸어요", "10:00 AM"),
            ChatMessage("me", "네 안녕하세요", "10:01 AM"),
            ChatMessage("other", "사용감 있나요? 좀 깎아주세요", "10:02 AM"),
            ChatMessage("me", "네고 안됩니다.", "10:03 AM"),
            ChatMessage("other", "그건 너무 비싸네요", "10:03 AM"),
            ChatMessage("other", "안녕하세요 물품 구매하고싶어서 연락드렸어요", "10:00 AM"),
            ChatMessage("me", "네 안녕하세요", "10:01 AM"),
            ChatMessage("other", "사용감 있나요? 좀 깎아주세요", "10:02 AM"),
            ChatMessage("me", "네고 안됩니다.", "10:03 AM"),
            ChatMessage("other", "그건 너무 비싸네요", "10:03 AM"),
            ChatMessage("other", "안녕하세요 물품 구매하고싶어서 연락드렸어요", "10:00 AM"),
            ChatMessage("me", "네 안녕하세요", "10:01 AM"),
            ChatMessage("other", "사용감 있나요? 좀 깎아주세요", "10:02 AM"),
            ChatMessage("me", "네고 안됩니다.", "10:03 AM"),
            ChatMessage("other", "그럼 너무 비싸네요", "10:03 AM"),
        )
    }

    var isExitChatting1 by remember { mutableStateOf(false) }
    var isExitChatting2 by remember { mutableStateOf(false) }
    var inputTextState by remember { mutableStateOf(TextFieldValue()) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column() {
        TopAppBar(
            navigationIcon = {
                BackButton(navController = navController)
            },
            title = {
                Text(
                    text = "한관희"
                ) // 텍스트 API로 받아와서 할 예정
            },
            elevation = 4.dp,
            actions = {
                Icon(
                    Icons.Default.ExitToApp,
                    contentDescription = "exit",
                    modifier = Modifier.clickable {
                        isExitChatting1 = true
                        // 채팅방 나가기 -> 리스트에서 제거하고 리로드
                        // AlertDialog로 ㄹㅇ 나갈겨? 물어봐야함.
                    }
                )
            }
        )

        Box(modifier = Modifier.padding(4.dp)){
            Button(
                modifier = Modifier
                    .shadow(0.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(24.dp)),
                onClick = {
                    navController.navigate(route + "_detail_screen")
                }) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        ImageFormat(url = R.drawable.ic_launcher_foreground.toString(), size = 60)
                        Column(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp),
                        ) {
                            TextFormat(string = "상품 이름", size = 24)
                            Spacer(modifier = Modifier.padding(4.dp))
                            TextFormat(string = "가격", size = 16)
                        }
                    }
                }
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            ChatScreen(chatMessages)
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextField(
                value = inputTextState,
                onValueChange = { inputTextState = it },
                placeholder = { Text(text = "Type a message") },
                colors = textFieldColors,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .border(
                        BorderStroke(1.dp, Color.Gray),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            keyboardController?.show()
                        } else {
                            keyboardController?.hide()
                        }
                    },
                singleLine = true,
                textStyle = TextStyle(fontSize = 16.sp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = {
                    // Handle send action
                    inputTextState = TextFieldValue()
                    keyboardController?.hide()
                })
            )

            Icon(
                Icons.Filled.Send,
                contentDescription = "send",
                modifier = Modifier.size(30.dp).clickable {
                    // 메세지 보내기
                    inputTextState = TextFieldValue()
                    keyboardController?.hide()
                }
           )
        }
    }

    if (isExitChatting1) {
        AlertDialog(
            onDismissRequest = { isExitChatting1 = false },
            title = { Text("채팅방 나가기") },
            text = { Text("정말로 채팅방을 나가시겠습니까?\n복구가 불가능합니다.") },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            isExitChatting2 = true
                            isExitChatting1 = false },
                        modifier = Modifier.fillMaxWidth(0.4f)
                    ) {
                        Text("나가기")
                    }
                    Button(
                        onClick = {
                            isExitChatting1 = false },
                        modifier = Modifier.fillMaxWidth(0.7f)
                    ) {
                        Text("취소")
                    }
                }

            }
        )
    }
    if (isExitChatting2) {
        AlertDialog(
            onDismissRequest = { isExitChatting2 = false },
            title = { Text("채팅방 나가기") },
            text = { Text("채팅방에서 나가셨습니다.") },
            confirmButton = {
                Button(
                    onClick = {
                        isExitChatting2 = false
                        navController.navigate("chatting_screen")
                              },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("확인")
                }
            }
        )
    }


}

@Composable
fun ChatScreen(chatMessages: List<ChatMessage>) {
    LazyColumn {
        itemsIndexed(chatMessages){index, message->
            ChatMessageItem(message)
        }
    }
}

@Composable
fun ChatMessageItem(chatMessage: ChatMessage) {
    val backgroundColor = if (chatMessage.sender == "me") {
        Color(0xFFECEFF1) // Light Gray
    } else {
        Color.White
    }

    val shape = if (chatMessage.sender == "me") {
        RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
    } else {
        RoundedCornerShape(topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
    }

    val alignment = if (chatMessage.sender == "me") {
        Alignment.CenterEnd
    } else {
        Alignment.CenterStart
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = alignment
    ){
        Card(
            backgroundColor = backgroundColor,
            shape = shape,
            elevation = 4.dp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = chatMessage.content,
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = chatMessage.timestamp,
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                )
            }
        }
    }
}