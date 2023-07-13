package com.dongminpark.foodmarketandroid.Screens

import android.widget.Toast
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.dongminpark.foodmarketandroid.R
import com.dongminpark.foodmarketandroid.Utils.Constant.Companion.outlinePadding
import com.dongminpark.foodmarketandroid.Utils.MESSAGE
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

    Column(modifier = Modifier.padding(outlinePadding.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ImageFormat(url = R.drawable.ic_launcher_foreground.toString())
            Column(
                modifier = Modifier.padding(12.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextFormat(string = "박동민", size = 30)
                    IconButton(onClick = { /* Handle second icon button click */ }) {
                        Icon(Icons.Filled.Create, contentDescription = "Create")
                    }
                }
                Spacer(modifier = Modifier.padding(4.dp))
                TextFormat(string = "연무동", size = 20)
            }
        }

        TextButtonFormat(string = "관심목록"){
            // 관심목록 페이지 이동
            // MyListPage(navController = navController, title = "관심목록") 여기로 이동
            navController.navigate("my_list_screen/관심목록")
        }
        TextButtonFormat(string = "판매내역"){
            // 판매내역 페이지 이동
            // MyListPage(navController = navController, title = "판매내역") 여기로 이동
            navController.navigate("my_list_screen/판매내역")
        }
        // 관심목록과 판매내역은 같은 함수 재활용. 배열과 이름을 받아서 출력하는 방식

        TextButtonFormat(string = "위치 최신화"){
            //Toast.makeText(App.instance, "위치정보 최신화 완료!", Toast.LENGTH_SHORT).show()
            LocationShowDialog = true
            // 위치 최신화 함수 호출
            // 로딩 팝업 띄우고 특정 시간 뒤 종료
        }
        if (LocationShowDialog) {
            LaunchedEffect(true) {
                delay(3000L) // 3초 후에 로딩 인디케이터를 닫습니다.
                LocationShowDialog = false
                // 토스트 띄우기

            }

            AlertDialog(
                onDismissRequest = {  },
                title = {},
                text = {
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(modifier = Modifier.wrapContentWidth(), color = Point)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("위치를 확인하고 있어요")
                    }
                },
                confirmButton = {
                    Button(
                        onClick = { LocationShowDialog = false},
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("취소")
                    }
                }
            )
        }


        TextButtonFormat(string = "로그아웃"){
            LogoutShowDialog1 = true
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
                                LogoutShowDialog1 = false },
                            modifier = Modifier.fillMaxWidth(0.4f)
                        ) {
                            Text("확인")
                        }
                        Button(
                            onClick = {
                                LogoutShowDialog1 = false },
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
                        onClick = { LogoutShowDialog2 = false},
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("확인")
                    }
                }
            )
        }

        TextButtonFormat(string = "탈퇴하기", color = Color.Red){
            SecessionShowDialog1 = true
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
                                SecessionShowDialog1 = false },
                            modifier = Modifier.fillMaxWidth(0.4f)
                        ) {
                            Text("확인")
                        }
                        Button(
                            onClick = {
                                SecessionShowDialog1 = false },
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

@Composable
fun MyListScreen(navController: NavController, title: String){ //리스트도 앞으로 받아야함.
    Column {
        TopAppBar(
            navigationIcon = {
                BackButton(navController = navController)
            },
            title = {
                Text(
                    text = title,
                ) // 텍스트 API로 받아와서 할 예정
            },
            elevation = 4.dp
        )
        // 리스트 띄우기
        LazyColumn(modifier = Modifier.padding(outlinePadding.dp)){
            items(15) {
                ItemFormat(navController, "my")
            }
        }
    }
}