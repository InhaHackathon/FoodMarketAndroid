package com.dongminpark.foodmarketandroid.Screens

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dongminpark.foodmarketandroid.Format.*
import com.dongminpark.foodmarketandroid.R
import com.dongminpark.foodmarketandroid.Utils.Constant.Companion.outlinePadding
import com.dongminpark.foodmarketandroid.Utils.Constants.TAG
import com.dongminpark.foodmarketandroid.ui.theme.Point
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController: NavController) {
    var WriteShowDialog by remember { mutableStateOf(false) }

    if (WriteShowDialog) {
        // 각각 텍스트 변수들을 밖으로 빼서 어쩌구... 할 생각
        AlertDialog(
            modifier = Modifier.fillMaxHeight(0.95f),
            shape = RoundedCornerShape(24.dp),
            onDismissRequest = { },
            title = { Text("게시글 작성") },
            text = {
                LazyColumn(){
                    item {
                        var text by remember { mutableStateOf("") }

                        TextFormat(string = "상품 이름")
                        Spacer(modifier = Modifier.padding(4.dp))
                        TextFieldFormat(text = text) {
                            text = it
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                    item {
                        var text by remember { mutableStateOf("") }

                        TextFormat(string = "가격")
                        Spacer(modifier = Modifier.padding(4.dp))
                        TextFieldFormat(
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = androidx.compose.ui.text.input.ImeAction.Done
                            ),
                            text = text
                        ){newText ->
                            if (newText.all { it.isDigit() }) {
                                text = newText
                            }
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                    item {
                        var expanded by remember { mutableStateOf(false) }
                        var selectedDate by remember { mutableStateOf(LocalDate.now()) }

                        Column {
                            TextFormat(string = "유통기한")
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
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                    item {
                        var text by remember { mutableStateOf("") }

                        TextFormat(string = "상세 설명 (150자 제한)")
                        Spacer(modifier = Modifier.padding(4.dp))
                        TextFieldFormat(text = text, isSingle = false) {
                            if (it.length < 150)
                                text = it
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                    item {
                        val selectedPhotos = remember { mutableStateListOf<Uri>() }

                        val requestMultipleImages = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.GetMultipleContents()
                        ) { uris: List<Uri>? ->
                            uris?.let {
                                selectedPhotos.addAll(it)
                            }
                        }

                        LazyRow() {
                            itemsIndexed(selectedPhotos) { index, item ->
                                Box(contentAlignment = Alignment.TopEnd){
                                    ImageFormat(url = item.toString())
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "delete",
                                        modifier = Modifier.clickable {
                                            selectedPhotos.remove(item)
                                        }
                                    )
                                }
                            }
                        }
                        Button(onClick = { requestMultipleImages.launch("image/*") }) {
                            TextFormat(string = "사진 업로드")
                        }
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
    var searchEnabled by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    Column() {
        TopAppBar(
            title = {
                if (searchEnabled) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "Search",
                        modifier = Modifier.clickable {
                            searchEnabled = !searchEnabled
                            searchText = ""
                        }
                    )
                    TextFieldFormat(text = searchText){
                        searchText = it
                    }
                } else {
                    Text(text = "연무동") // 텍스트 API로 받아올 예정
                }
            },
            actions = {
                if (!searchEnabled) {
                    Row {
                        IconButton(onClick = {
                            searchEnabled = !searchEnabled
                        }) {
                            Icon(Icons.Filled.Search, contentDescription = "Search")
                        }
                        IconButton(onClick = { WriteShowDialog = true }) {
                            Icon(Icons.Filled.Create, contentDescription = "Create", tint = Point)
                        }
                    }
                }else{
                    IconButton(onClick = {
                        // 검색 api
                    }) {
                        Icon(Icons.Filled.Send, contentDescription = "Search")
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