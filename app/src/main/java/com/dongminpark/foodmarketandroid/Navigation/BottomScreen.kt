package com.dongminpark.foodmarketandroid.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.dongminpark.foodmarketandroid.ui.IconPack
import com.dongminpark.foodmarketandroid.ui.iconpack.*


sealed class BottomScreen(
    val title: String,
    val iconOutline: ImageVector,
    val iconSolid: ImageVector,
    val screenRoute: String
) {
    object Main : BottomScreen("메인", IconPack.HomeOutline, IconPack.HomeSolid, "main")

    object Chatting :
        BottomScreen("채팅",IconPack.UserOutline, IconPack.UserSolid, "chatting")

    object FoodBank : BottomScreen("푸드뱅크", IconPack.UserOutline, IconPack.UserSolid, "foodbank")

    object My :
        BottomScreen("마이페이지", IconPack.UserOutline, IconPack.UserSolid, "my")
}

sealed class MainNavigationScreens(val route: String) {
    object Main : MainNavigationScreens("main")
    object Detail : MainNavigationScreens("main_detail_screen")
    object Profile : MainNavigationScreens("main_profile_screen")
}

sealed class ChattingNavigationScreens(val route: String) {
    object Chatting : ChattingNavigationScreens("chatting")
}

sealed class FoodBankNavigationScreens(val route: String) {
    object FoodBank : FoodBankNavigationScreens("foodbank")
    object FoodBankDetail : FoodBankNavigationScreens("foodbank_detail_screen")
}

sealed class MyNavigationScreens(val route: String = "my") {
    object My : MyNavigationScreens("my")
    object MyList : MyNavigationScreens("my_list_screen")
    object MyDetail : MyNavigationScreens("my_detail_screen")
}