package com.dongminpark.foodmarketandroid.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.foodmarketandroid.App
import com.dongminpark.foodmarketandroid.Button.BackButton
import com.dongminpark.foodmarketandroid.Format.ImageFormat
import com.dongminpark.foodmarketandroid.Format.ItemFormat
import com.dongminpark.foodmarketandroid.Format.TextButtonFormat
import com.dongminpark.foodmarketandroid.Format.TextFormat
import com.dongminpark.foodmarketandroid.Model.Board
import com.dongminpark.foodmarketandroid.Model.Profile
import com.dongminpark.foodmarketandroid.OAuthData
import com.dongminpark.foodmarketandroid.R
import com.dongminpark.foodmarketandroid.Retrofit.RetrofitManager
import com.dongminpark.foodmarketandroid.Utils.Constant.Companion.outlinePadding
import com.dongminpark.foodmarketandroid.Utils.Constants
import com.dongminpark.foodmarketandroid.Utils.MESSAGE
import com.dongminpark.foodmarketandroid.Utils.RESPONSE_STATE
import com.dongminpark.foodmarketandroid.Utils.USER
import com.dongminpark.foodmarketandroid.ui.theme.Point
import kotlinx.coroutines.delay


@Composable
fun MyScreen(navController: NavController) {
    // 각각 요소들을 감싸서 디자인. 관식이가 주로 하는 디자인 느낌으로
    var LocationShowDialog by remember { mutableStateOf(false) }
    var LogoutShowDialog1 by remember { mutableStateOf(false) }
    var LogoutShowDialog2 by remember { mutableStateOf(false) }
    var SecessionShowDialog1 by remember { mutableStateOf(false) }
    var SecessionShowDialog2 by remember { mutableStateOf(false) }
    var loading by rememberSaveable{ mutableStateOf(true) }
    var isOkay by rememberSaveable{ mutableStateOf(false) }
    var profile by remember {
        mutableStateOf(Profile())
    }

    if (loading){
        loading = false
        RetrofitManager.instance.userUserId(userId = USER.USERID, completion = { responseState, resposeBody ->
            when (responseState) {
                RESPONSE_STATE.OKAY -> {
                    profile = resposeBody!!
                    Log.d(Constants.TAG, "MainScreen: examples load success")
                    RetrofitManager.instance.boardList(completion = { responseState, resposeBody ->
                        when (responseState) {
                            RESPONSE_STATE.OKAY -> {
                                boards.addAll(resposeBody!!)
                                isOkay = true
                                Log.d(Constants.TAG, "MainScreen: examples load success")
                                boards.reverse()
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
                title = {
                    TextFormat(
                        string = "마이 페이지",
                        size = 24
                    )
                },
                elevation = 4.dp
            )
            Column(modifier = Modifier.padding(outlinePadding.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(24.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        ImageFormat(url = profile.profileImgUrl, size = 180)
                        Column(
                            modifier = Modifier.padding(horizontal = 12.dp),
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                TextFormat(string = profile.name, size = 32)
                                IconButton(onClick = { /* Handle second icon button click */ }) {
                                    Icon(
                                        Icons.Filled.Create,
                                        contentDescription = "Create",
                                        tint = Point
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.padding(0.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                TextFormat(string = "용현동", size = 24, weight = FontWeight.Light)
                                IconButton(onClick = { LocationShowDialog = true }) {
                                    Icon(
                                        Icons.Filled.Refresh,
                                        contentDescription = "Refresh",
                                        tint = Point
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(12.dp))

                Row() {
                    BoxFormat(text = "관심목록") {
                        navController.navigate("my_list_screen/관심목록")
                    }
                    Spacer(modifier = Modifier.padding(8.dp))

                    BoxFormat(text = "판매내역") {
                        navController.navigate("my_list_screen/판매내역")
                    }
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Row() {
                    BoxFormat(text = "로그아웃") {
                        LogoutShowDialog1 = true
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    BoxFormat(text = "회원탈퇴", color = Color.Red) {
                        SecessionShowDialog1 = true
                    }
                }

                if (LocationShowDialog) {
                    LaunchedEffect(true) {
                        delay(3000L) // 3초 후에 로딩 인디케이터를 닫습니다.
                        LocationShowDialog = false
                        // 토스트 띄우기

                    }

                    AlertDialog(
                        onDismissRequest = { },
                        title = {},
                        text = {
                            Column(
                                Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.wrapContentWidth(),
                                    color = Point
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text("위치를 확인하고 있어요")
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = { LocationShowDialog = false },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("취소")
                            }
                        }
                    )
                }



                if (LogoutShowDialog1) {
                    AlertDialog(
                        onDismissRequest = { LogoutShowDialog1 = false },
                        title = { Text("로그아웃") },
                        text = { Text("정말로 로그아웃 하시겠습니까?") },
                        confirmButton = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Button(
                                    onClick = {
                                        LogoutShowDialog2 = true
                                        LogoutShowDialog1 = false
                                    },
                                    modifier = Modifier.fillMaxWidth(0.4f)
                                ) {
                                    Text("확인")
                                }
                                Button(
                                    onClick = {
                                        LogoutShowDialog1 = false
                                    },
                                    modifier = Modifier.fillMaxWidth(0.7f)
                                ) {
                                    Text("취소")
                                }
                            }

                        }
                    )
                }
                if (LogoutShowDialog2) {
                    AlertDialog(
                        onDismissRequest = { LogoutShowDialog2 = false },
                        title = { Text("로그아웃") },
                        text = { Text("로그아웃 되셨습니다.") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    OAuthData.mGoogleSignInClient!!.signOut().addOnCompleteListener {  }
                                    OAuthData.auth = null
                                    LogoutShowDialog2 = false
                                          },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("확인")
                            }
                        }
                    )
                }


                if (SecessionShowDialog1) {
                    AlertDialog(
                        onDismissRequest = { SecessionShowDialog1 = false },
                        title = { Text("회원탈퇴") },
                        text = { Text("모든 정보가 삭제되고, 복구되지 않습니다.\n정말로 탈퇴 하시겠습니까?") },
                        confirmButton = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Button(
                                    onClick = {
                                        SecessionShowDialog2 = true
                                        SecessionShowDialog1 = false
                                    },
                                    modifier = Modifier.fillMaxWidth(0.4f)
                                ) {
                                    Text("확인")
                                }
                                Button(
                                    onClick = {
                                        SecessionShowDialog1 = false
                                    },
                                    modifier = Modifier.fillMaxWidth(0.7f)
                                ) {
                                    Text("취소")
                                }
                            }

                        }
                    )
                }
                if (SecessionShowDialog2) {
                    AlertDialog(
                        onDismissRequest = { SecessionShowDialog2 = false },
                        title = { Text("회원탈퇴") },
                        text = { Text("회원탈퇴 되셨습니다.") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    SecessionShowDialog2 = false
                                    // 로딩페이지 or 로그인 페이지로 이동하는 함수.
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("확인")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MyListScreen(navController: NavController, title: String){ //리스트도 앞으로 받아야함.
    var loading by rememberSaveable{ mutableStateOf(true) }
    var isOkay by rememberSaveable{ mutableStateOf(false) }
    var list = remember {
        mutableListOf<Board>()
    }

    if (loading){
        loading = false
        if (title == "관심목록") {
            RetrofitManager.instance.likeListUserId(
                userId = USER.USERID,
                completion = { responseState, resposeBody ->
                    when (responseState) {
                        RESPONSE_STATE.OKAY -> {
                            list.addAll(resposeBody!!)
                            isOkay = true
                            Log.d(Constants.TAG, "MainScreen: examples load success")
                            list.reverse()
                        }
                        RESPONSE_STATE.FAIL -> {
                            Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                            Log.d(Constants.TAG, "MainScreen: main list load Error")
                        }
                    }
                })
        }else{
            RetrofitManager.instance.boardUserMypage(
                completion = { responseState, resposeBody ->
                    when (responseState) {
                        RESPONSE_STATE.OKAY -> {
                            list.addAll(resposeBody!!)
                            isOkay = true
                            Log.d(Constants.TAG, "MainScreen: examples load success")
                            list.reverse()
                        }
                        RESPONSE_STATE.FAIL -> {
                            Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                            Log.d(Constants.TAG, "MainScreen: main list load Error")
                        }
                    }
                })
        }
    }
    if(isOkay){
        Column {
            TopAppBar(
                navigationIcon = {
                    BackButton(navController = navController)
                },
                title = {
                    Text(
                        text = title,
                    )
                },
                elevation = 4.dp
            )
            // 리스트 띄우기
            LazyColumn(modifier = Modifier.padding(outlinePadding.dp)){
                items(list.size) {
                    ItemFormat(navController, "my", board = list[it])
                }
            }
        }
    }
}

@Composable
fun BoxFormat(text: String, color: Color = Color.Black, content: () -> Unit){
    Row(modifier = Modifier
        .size(180.dp)
        .clip(RoundedCornerShape(24.dp))
        .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(24.dp))
        .clickable {
            content()
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        TextFormat(string = text, size = 30, color = color)
    }
}