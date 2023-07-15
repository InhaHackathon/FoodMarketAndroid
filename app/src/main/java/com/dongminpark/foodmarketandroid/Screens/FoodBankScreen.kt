package com.dongminpark.foodmarketandroid.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dongminpark.foodmarketandroid.App
import com.dongminpark.foodmarketandroid.Chatting
import com.dongminpark.foodmarketandroid.Format.FoodBankItemFormat
import com.dongminpark.foodmarketandroid.Format.TextFormat
import com.dongminpark.foodmarketandroid.Model.Board
import com.dongminpark.foodmarketandroid.Model.FoodBank
import com.dongminpark.foodmarketandroid.OAuthData
import com.dongminpark.foodmarketandroid.Retrofit.RetrofitManager
import com.dongminpark.foodmarketandroid.Utils.Constants
import com.dongminpark.foodmarketandroid.Utils.MESSAGE
import com.dongminpark.foodmarketandroid.Utils.RESPONSE_STATE
import com.dongminpark.foodmarketandroid.navigation.Screen

var list = listOf<FoodBank>()
@Composable
fun FoodBankScreen(navController: NavController) {
    var loading by rememberSaveable{ mutableStateOf(true) }
    var isOkay by rememberSaveable{ mutableStateOf(false) }
//    var list = rememberSaveable {
//        mutableListOf<FoodBank>()
//    }

    if (loading){
        loading = false

        RetrofitManager.instance.foodbankList(completion = { responseState, resposeBody ->
            when (responseState) {
                RESPONSE_STATE.OKAY -> {
                    list = resposeBody!!.toMutableList()
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
    if (isOkay) {
        Column() {
            TopAppBar(
                title = {
                    TextFormat(
                        string = "내 주변 푸드뱅크",
                        size = 24
                    )
                },
                elevation = 4.dp
            )

            LazyColumn() {
                items(list.size) {
                    FoodBankItemFormat(list[it]) {
                        // FoodBankDetailScreen으로 이동
                        Chatting.Latitude = list[it].latitude
                        Chatting.Longitude = list[it].longitude
                        Chatting.Name = list[it].name
                        navController.navigate("foodbank_detail_screen")
                    }
                }
            }
        }
    }
}