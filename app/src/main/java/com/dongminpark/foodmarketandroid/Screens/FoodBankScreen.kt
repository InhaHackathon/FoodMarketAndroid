package com.dongminpark.foodmarketandroid.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dongminpark.foodmarketandroid.Format.FoodBankItemFormat
import com.dongminpark.foodmarketandroid.OAuthData
import com.dongminpark.foodmarketandroid.navigation.Screen

@Composable
fun FoodBankScreen(navController: NavController) {
    Column() {
        TopAppBar(
            title = {
                Text(
                    text = "내 주변 푸드뱅크",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                ) // 텍스트 API로 받아와서 할 예정
            },
            elevation = 4.dp
        )

        LazyColumn(){
            items(20){
                FoodBankItemFormat(){
                    // FoodBankDetailScreen으로 이동
                    navController.navigate("foodbank_detail_screen")
                }
            }
        }
    }
}