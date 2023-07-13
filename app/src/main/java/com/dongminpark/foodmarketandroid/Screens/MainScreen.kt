package com.dongminpark.foodmarketandroid.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dongminpark.foodmarketandroid.Format.ImageFormat
import com.dongminpark.foodmarketandroid.Format.ItemFormat
import com.dongminpark.foodmarketandroid.Utils.Constant.Companion.outlinePadding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController: NavController) {
    var WriteShowDialog by remember { mutableStateOf(false) }
    var isTextFieldFocused = false

    if (WriteShowDialog) {
        AlertDialog(
            onDismissRequest = { WriteShowDialog = false },
            title = { Text("게시글 작성") },
            text = {
                LazyColumn(){
                    item {
                        val focusRequester by remember { mutableStateOf(FocusRequester()) }
                        var text by remember { mutableStateOf("") }

                        Text(text = "상품 이름")
                        TextField(
                            value = text,
                            onValueChange = {
                                text = it
                            },
                            modifier = Modifier
                                .focusRequester(focusRequester = focusRequester)
                                .onFocusChanged {
                                    isTextFieldFocused = it.isFocused
                                },
                            maxLines = 1,
                            singleLine = true
                        )
                    }
                    item {
                        val focusRequester by remember { mutableStateOf(FocusRequester()) }
                        var text by remember { mutableStateOf("") }

                        Text(text = "가격")
                        TextField(
                            value = text,
                            onValueChange = { newText ->
                                // 숫자만 입력 받도록 처리
                                if (newText.all { it.isDigit() }) {
                                    text = newText
                                }
                            },
                            modifier = Modifier
                                .focusRequester(focusRequester = focusRequester)
                                .onFocusChanged {
                                    isTextFieldFocused = it.isFocused
                                },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = androidx.compose.ui.text.input.ImeAction.Done
                            ),
                            maxLines = 1,
                            singleLine = true
                        )
                    }
                    item {
                        var expanded by remember { mutableStateOf(false) }
                        var selectedDate by remember { mutableStateOf(LocalDate.now()) }

                        Column {
                            Text(text = "유통기한")
                            Button(
                                onClick = { expanded = !expanded },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(text = "▼")
                            }

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                val startDate = LocalDate.now()
                                val endDate = LocalDate.now().plusMonths(6).minusDays(1) // 6개월 이후까지의 날짜 표시

                                var currentDate = startDate
                                while (currentDate.isBefore(endDate) || currentDate.isEqual(endDate)) {
                                    val date = currentDate
                                    DropdownMenuItem(
                                        onClick = {
                                            selectedDate = date
                                            expanded = false
                                        }
                                    ) {
                                        Text(
                                            text = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                            color = if (date == selectedDate) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
                                        )
                                    }
                                    currentDate = currentDate.plusDays(1)
                                }
                            }
                        }
                    }
                    item {
                        val focusRequester by remember { mutableStateOf(FocusRequester()) }
                        var text by remember { mutableStateOf("") }

                        Text(text = "상세 설명 (150자 제한)")
                        TextField(
                            value = text,
                            onValueChange = {
                                if (it.length < 150)
                                    text = it
                            },
                            modifier = Modifier
                                .focusRequester(focusRequester = focusRequester)
                                .onFocusChanged {
                                    isTextFieldFocused = it.isFocused
                                }
                        )
                    }
                    item {
                        Text(text = "사진")
                    }
                }
                   },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { WriteShowDialog = false },
                        modifier = Modifier.fillMaxWidth(0.4f)
                    ) {
                        Text("등록")
                    }
                    Button(
                        onClick = { WriteShowDialog = false },
                        modifier = Modifier.fillMaxWidth(0.7f)
                    ) {
                        Text("취소")
                    }
                }
            }
        )
    }

    Column() {
        TopAppBar(
            title = {
                Text(text = "연무동") // 텍스트 API로 받아와서 할 예정
            },
            actions = {
                Row {
                    IconButton(onClick = { /*  */ }) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { WriteShowDialog = true }) {
                        Icon(Icons.Filled.Create, contentDescription = "Create")
                    }
                }
            },
            elevation = 4.dp
        )
        // 버튼으로 만들고, 해당 정보를 매개변수로 넘기기.
        LazyColumn(modifier = Modifier.padding(outlinePadding.dp)) {
            items(15) {
                ItemFormat(navController, "main")
            }
        }
    }
}