package com.dongminpark.foodmarketandroid.Screens

import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dongminpark.foodmarketandroid.App
import com.dongminpark.foodmarketandroid.Format.*
import com.dongminpark.foodmarketandroid.Model.Board
import com.dongminpark.foodmarketandroid.R
import com.dongminpark.foodmarketandroid.Retrofit.RetrofitManager
import com.dongminpark.foodmarketandroid.Utils.Constant.Companion.outlinePadding
import com.dongminpark.foodmarketandroid.Utils.Constants.TAG
import com.dongminpark.foodmarketandroid.Utils.MESSAGE
import com.dongminpark.foodmarketandroid.Utils.RESPONSE_STATE
import com.dongminpark.foodmarketandroid.ui.theme.Point
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

val boards = arrayListOf<Board>()
val searchboards = arrayListOf<Board>()

@OptIn(ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController: NavController) {
    var WriteShowDialog by remember { mutableStateOf(false) }

    if (WriteShowDialog) {
        // 각각 텍스트 변수들을 밖으로 빼서 어쩌구... 할 생각
        var nametext by remember { mutableStateOf("") }
        var pricetext by remember { mutableStateOf("") }
        var selectedDate by remember { mutableStateOf(LocalDate.now()) }
        var explaintext by remember { mutableStateOf("") }
        val selectedPhotos = remember { mutableStateListOf<Uri>() }

        AlertDialog(
            modifier = Modifier.fillMaxHeight(0.9f),
            shape = RoundedCornerShape(24.dp),
            onDismissRequest = { },
            title = { Text("게시글 작성") },
            text = {
                LazyColumn(modifier = Modifier.padding(vertical = 12.dp)){
                    item {
                        TextFormat(string = "상품 이름")
                        Spacer(modifier = Modifier.padding(4.dp))
                        TextFieldFormat(text = nametext) {
                            nametext = it
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                    item {
                        TextFormat(string = "가격")
                        Spacer(modifier = Modifier.padding(4.dp))
                        TextFieldFormat(
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = androidx.compose.ui.text.input.ImeAction.Done
                            ),
                            text = pricetext
                        ){newText ->
                            if (newText.all { it.isDigit() }) {
                                pricetext = newText
                            }
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                    item {
                        var expanded by remember { mutableStateOf(false) }

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
                        TextFormat(string = "상세 설명 (150자 제한)")
                        Spacer(modifier = Modifier.padding(4.dp))
                        TextFieldFormat(text = explaintext, isSingle = false) {
                            if (it.length < 150)
                                explaintext = it
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                    item {
                        val requestMultipleImages = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.GetMultipleContents()
                        ) { uris: List<Uri>? ->
                            uris?.let {
                                selectedPhotos.addAll(it)
                            }
                        }

                        LazyRow() {
                            item {
                                Row {
                                    Box(
                                        modifier = Modifier
                                            .size(80.dp)
                                            .border(
                                                width = 1.dp,
                                                color = Color.Gray,
                                                shape = RoundedCornerShape(24.dp)
                                            )
                                            .clip(RoundedCornerShape(24.dp))
                                            .clickable {
                                                requestMultipleImages.launch("image/*")
                                            },
                                        contentAlignment = Alignment.Center
                                    ){
                                        Image(
                                            painter = painterResource(id = R.drawable.camera),
                                            contentDescription = "map",
                                            modifier = Modifier
                                                .size(40.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.padding(4.dp))
                                }
                            }
                            itemsIndexed(selectedPhotos) { index, item ->
                                Row() {
                                    Box(contentAlignment = Alignment.TopEnd){
                                        ImageFormat(url = item.toString())
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = "delete",
                                            modifier = Modifier
                                                .clickable {
                                                    selectedPhotos.remove(item)
                                                }
                                                .clip(RoundedCornerShape(24.dp))
                                                .background(Color.White)
                                        )
                                    }
                                    Spacer(modifier = Modifier.padding(4.dp))
                                }
                            }
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
                        onClick = {
                            RetrofitManager.instance.boardCreate(
                                nametext ,
                                "https://i.ibb.co/202z1kw/image.jpg", // 수정 필요. selectedPhotos의 값을 구분자를 넣어 string하나로 넣기.
                                selectedDate.toString(),
                                pricetext.toInt(),
                                explaintext,
                                completion = { responseState ->
                                    when (responseState) {
                                        RESPONSE_STATE.OKAY -> {
                                            Log.d(TAG, "api 호출 성공")
                                            boards.clear()
                                            RetrofitManager.instance.boardList(completion = { responseState, resposeBody ->
                                                when (responseState) {
                                                    RESPONSE_STATE.OKAY -> {
                                                        boards.addAll(resposeBody!!)
                                                        Log.d(TAG, "MainScreen: examples load success")
                                                        boards.reverse()
                                                        WriteShowDialog = false
                                                    }
                                                    RESPONSE_STATE.FAIL -> {
                                                        Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                                                        Log.d(TAG, "MainScreen: main list load Error")
                                                    }
                                                }
                                            })

                                        }
                                        RESPONSE_STATE.FAIL -> {
                                            Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                                            Log.d(TAG, "api 호출 에러")
                                        }
                                    }
                                })
                                  },
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
    val keyboardController = LocalSoftwareKeyboardController.current
    var loading by rememberSaveable{ mutableStateOf(true) }
    var isOkay by rememberSaveable{ mutableStateOf(false) }
    var search by rememberSaveable{ mutableStateOf(false) }

    if (loading){
        loading = false
        boards.clear()
        RetrofitManager.instance.boardList(completion = { responseState, resposeBody ->
            when (responseState) {
                RESPONSE_STATE.OKAY -> {
                    boards.addAll(resposeBody!!)
                    isOkay = true
                    Log.d(TAG, "MainScreen: examples load success")
                    boards.reverse()
                }
                RESPONSE_STATE.FAIL -> {
                    Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "MainScreen: main list load Error")
                }
            }
        })
    }
    if(isOkay){
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
                        Spacer(modifier = Modifier.padding(4.dp))
                        TextFieldFormat(
                            text = searchText,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                            keyboardActions = KeyboardActions(onSend = {
                                keyboardController?.hide()
                                RetrofitManager.instance.boardSearch(searchText, completion = { responseState, resposeBody ->
                                    when (responseState) {
                                        RESPONSE_STATE.OKAY -> {
                                            searchboards.addAll(resposeBody!!)
                                            Log.d(TAG, "MainScreen: examples load success")
                                        }
                                        RESPONSE_STATE.FAIL -> {
                                            Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                                            Log.d(TAG, "MainScreen: main list load Error")
                                        }
                                    }
                                })
                            })
                        ) {
                            searchText = it
                        }
                    } else {
                        TextFormat(string = "용현동", size = 24)
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
                                Icon(
                                    Icons.Filled.Create,
                                    contentDescription = "Create",
                                    tint = Point
                                )
                            }
                        }
                    } else {
                        IconButton(onClick = {
                            keyboardController?.hide()
                            // 검색 api
                        }) {
                            Icon(Icons.Filled.Send, contentDescription = "Search", tint = Point)
                        }
                    }
                },
                elevation = 4.dp
            )

            if (!search) {
                // 버튼으로 만들고, 해당 정보를 매개변수로 넘기기.
                LazyColumn(modifier = Modifier.padding(1.dp)) {
                    items(count = boards.size) {

                            ItemFormat(navController, "main", board = boards[it])
                    }
                }
            }else{
                LazyColumn(modifier = Modifier.padding(1.dp)) {
                    items(count = searchboards.size) {
                        ItemFormat(navController, "main", board = searchboards[it])
                    }
                }
            }
        }
    }
}