package com.dongminpark.foodmarketandroid.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.foodmarketandroid.Button.BackButton
import com.dongminpark.foodmarketandroid.Format.ImageFormat
import com.dongminpark.foodmarketandroid.Format.ItemFormat
import com.dongminpark.foodmarketandroid.Format.TextFormat
import com.dongminpark.foodmarketandroid.R
import com.dongminpark.foodmarketandroid.Utils.Constant
import com.dongminpark.foodmarketandroid.Utils.Constant.Companion.outlinePadding
import com.dongminpark.foodmarketandroid.ui.theme.FoodmarketAndroidTheme

@Composable
fun ProfileScreen(navController: NavController, route: String) {
    Column() {
        TopAppBar(
            navigationIcon = {
                BackButton(navController = navController)
            },
            title = {
                Text(
                    text = "이름 프로필",
                ) // 텍스트 API로 받아와서 할 예정
            },
            elevation = 4.dp
        )
        LazyColumn() {
            item {
                Row(
                    modifier = Modifier
                        .padding(outlinePadding.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    ImageFormat(url = R.drawable.ic_launcher_foreground.toString(), size = 120)
                    Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp)) {
                        TextFormat(string = "박동민", size = 32)
                        Spacer(modifier = Modifier.padding(4.dp))
                        TextFormat(string = "연무동", size = 24)
                    }
                }
            }

            // 판매 상품
            items(15) {
                ItemFormat(
                    navController = navController,
                    route = route
                )
            }
        }
    }
}


@Composable
fun profile(route: String) {
    var navController = rememberNavController()

    Column() {
        TopAppBar(
            navigationIcon = {
                BackButton(navController = navController)
            },
            title = {
                Text(
                    text = "이름 프로필",
                ) // 텍스트 API로 받아와서 할 예정
            },
            elevation = 4.dp
        )
        LazyColumn() {
            item {
                Row(
                    modifier = Modifier
                        .padding(horizontal = outlinePadding.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    ImageFormat(url = R.drawable.ic_launcher_foreground.toString(), size = 120)
                    Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp)) {
                        TextFormat(string = "박동민", size = 32)
                        Spacer(modifier = Modifier.padding(4.dp))
                        TextFormat(string = "연무동", size = 24)
                    }
                }
            }

            // 판매 상품
            items(15) {
                ItemFormat(modifier = Modifier.padding(outlinePadding.dp), navController = navController, route = route)
            }
        }
    }
}


@Preview
@Composable
fun profilePreview() {
    FoodmarketAndroidTheme {
        //profile()
    }
}
