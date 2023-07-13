package com.dongminpark.foodmarketandroid.Screens

import androidx.compose.runtime.Composable
import com.dongminpark.foodmarketandroid.Navigation.MainScreenView
import com.dongminpark.foodmarketandroid.navigation.BottomScreen


// 매개변수로 원하는 페이지를 넣으면 해당 페이지에서 시작. 안넣을 경우 기본은 메인페이지로 설정.
@Composable
fun OnceScreen(
    startDestination: String = BottomScreen.Main.screenRoute
) {
    MainScreenView(startDestination)
}