package com.dongminpark.foodmarketandroid.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dongminpark.foodmarketandroid.Format.ImageFormat
import com.dongminpark.foodmarketandroid.Format.ItemFormat
import com.dongminpark.foodmarketandroid.Utils.Constant.Companion.outlinePadding

@Composable
fun MainScreen() {
    Column() {
        TopAppBar(
            title = {
                Text(text = "연무동") // 텍스트 API로 받아와서 할 예정
            },
            actions = {
                Row {
                    IconButton(onClick = { /* Handle first icon button click */ }) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { /* Handle second icon button click */ }) {
                        Icon(Icons.Filled.Create, contentDescription = "Create")
                    }
                }
            },
            elevation = 4.dp
        )
        // 버튼으로 만들고, 해당 정보를 매개변수로 넘기기.
        LazyColumn(modifier = Modifier.padding(outlinePadding.dp)){
            items(15) {
                ItemFormat()
            }
        }
    }
}