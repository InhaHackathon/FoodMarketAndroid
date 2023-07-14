package com.dongminpark.foodmarketandroid.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dongminpark.foodmarketandroid.Screens.*
import com.dongminpark.foodmarketandroid.navigation.*
import java.lang.Exception


@Composable
fun SetupNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.Once.route) {
            OnceScreen()
        }
    }
}

@Composable
fun BottomNavigation(navController: NavHostController) {
    val items = listOf(
        BottomScreen.Main,
        BottomScreen.Chatting,
        BottomScreen.FoodBank,
        BottomScreen.My
    )

    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = if (currentRoute?.startsWith(item.screenRoute) == true) item.iconSolid else item.iconOutline,
                        contentDescription = item.title,
                        modifier = Modifier
                            .width(26.dp)
                            .height(26.dp)
                    )
                },
                label = { Text(item.title, fontSize = 11.sp) },
                selectedContentColor = MaterialTheme.colors.primaryVariant,
                unselectedContentColor = MaterialTheme.colors.secondary,
                selected = currentRoute?.startsWith(item.screenRoute) == true,
                alwaysShowLabel = false,
                onClick = {
                    try {
                        navController.navigate(item.screenRoute) {
                            if (currentRoute?.startsWith(item.screenRoute) != true) {
                                navController.graph.startDestinationRoute?.let {
                                    popUpTo(it) { saveState = true }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    } catch (e: Exception) {
                        navController.navigate(item.screenRoute)
                    }
                }
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreenView(startDestination: String) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) {
        Box(Modifier.padding(it)) {
            NavHost(
                navController = navController,
                startDestination = startDestination,
            ) {
                // Main
                composable(MainNavigationScreens.Main.route) { MainScreen(navController = navController) }
                composable(MainNavigationScreens.Detail.route) { DetailScreen(navController = navController, "main") }
                composable(MainNavigationScreens.Profile.route) { ProfileScreen(navController = navController, "main")}
                composable(MainNavigationScreens.Chatting.route) { ChattingChattingDetailScreen(navController = navController, "main") }

                // Chatting
                composable(ChattingNavigationScreens.Chatting.route) {
                    ChattingScreen(
                        navController = navController
                    )
                }
                composable(ChattingNavigationScreens.ChattingChattingDetail.route) {
                    ChattingChattingDetailScreen(
                        navController = navController,
                        route = "chatting"
                    )
                }
                composable(ChattingNavigationScreens.ChattingDetail.route) {
                    DetailScreen(
                        navController = navController,
                        route = "chatting"
                    )
                }

/*
                composable(
                    route = "${CommunityNavigationScreens.Follow.route}/{variable}",
                    arguments = listOf(navArgument("variable") { type = NavType.StringType })
                ) { entry ->
                    val variable = entry.arguments?.getString("variable")
                    FollowScreen(
                        navController = navController,
                        BottomScreen.Community.screenRoute,
                        currentPage = variable!!
                    )
                }

 */
                // FoodBank
                composable(FoodBankNavigationScreens.FoodBank.route) {
                    FoodBankScreen(navController = navController)
                }
                composable(FoodBankNavigationScreens.FoodBankDetail.route) {
                    FoodBankDetailScreen(navController = navController)
                }


                // My
                composable(MyNavigationScreens.My.route) {
                    MyScreen(navController = navController)
                }

                composable(
                    route = "${MyNavigationScreens.MyList.route}/{variable}",
                    arguments = listOf(navArgument("variable") { type = NavType.StringType })
                ) { entry ->
                    val variable = entry.arguments?.getString("variable")
                    MyListScreen(navController = navController, title = variable!!)
                }

                composable(MyNavigationScreens.MyDetail.route) {
                    DetailScreen(navController = navController, "my")
                }
                composable(MyNavigationScreens.Chatting.route) { ChattingChattingDetailScreen(navController = navController, "my") }
            }
        }
    }
}