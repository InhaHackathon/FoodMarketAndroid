package com.dongminpark.foodmarketandroid.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.foodmarketandroid.App
import com.dongminpark.foodmarketandroid.Button.BackButton
import com.dongminpark.foodmarketandroid.Format.ImageFormat
import com.dongminpark.foodmarketandroid.Format.ItemFormat
import com.dongminpark.foodmarketandroid.Format.TextFormat
import com.dongminpark.foodmarketandroid.Model.Board
import com.dongminpark.foodmarketandroid.Model.Profile
import com.dongminpark.foodmarketandroid.R
import com.dongminpark.foodmarketandroid.Retrofit.RetrofitManager
import com.dongminpark.foodmarketandroid.Utils.Constant
import com.dongminpark.foodmarketandroid.Utils.Constant.Companion.outlinePadding
import com.dongminpark.foodmarketandroid.Utils.Constants
import com.dongminpark.foodmarketandroid.Utils.MESSAGE
import com.dongminpark.foodmarketandroid.Utils.RESPONSE_STATE
import com.dongminpark.foodmarketandroid.ui.theme.FoodmarketAndroidTheme

@Composable
fun ProfileScreen(navController: NavController, route: String, userId: Int) {
    var loading by rememberSaveable{ mutableStateOf(true) }
    var isOkay by rememberSaveable{ mutableStateOf(false) }
    var list = remember {
        mutableListOf<Board>()
    }
    var profile by rememberSaveable {
        mutableStateOf(Profile())
    }

    if (loading){
        loading = false
        // 수정해야함.
        RetrofitManager.instance.boardUserUserId(userId = userId, completion = { responseState, resposeBody ->
            when (responseState) {
                RESPONSE_STATE.OKAY -> {
                    list.addAll(resposeBody!!)
                    list.reverse()
                    Log.d(Constants.TAG, "MainScreen: examples load success")
                    isOkay = true
                    RetrofitManager.instance.userUserId(userId = userId, completion = { responseState, resposeBody ->
                        when (responseState) {
                            RESPONSE_STATE.OKAY -> {
                                profile = resposeBody!!
                                Log.d(Constants.TAG, "MainScreen: examples load success")
                                isOkay = true
                            }
                            RESPONSE_STATE.FAIL -> {
                                Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                                Log.d(Constants.TAG, "MainScreen: main list load Error")
                            }
                        }
                    })
                }
                RESPONSE_STATE.FAIL -> {
                    Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                    Log.d(Constants.TAG, "MainScreen: main list load Error")
                }
            }
        })
    }
    if(isOkay) {
        Column() {
            TopAppBar(
                navigationIcon = {
                    BackButton(navController = navController)
                },
                title = {
                    Text(
                        text = "프로필",
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
                        ImageFormat(url = profile.profileImgUrl, size = 120)
                        Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp)) {
                            TextFormat(string = profile.name, size = 32)
                            Spacer(modifier = Modifier.padding(4.dp))
                            TextFormat(string = profile.location, size = 24)
                        }
                    }
                }
                // 판매 상품
                items(list.size) {
                    ItemFormat(
                        navController = navController,
                        route = route,
                        board = list[it]
                    )
                }
            }
        }
    }
}