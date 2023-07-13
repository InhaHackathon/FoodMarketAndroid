package com.dongminpark.foodmarketandroid.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dongminpark.foodmarketandroid.Format.ImageFormat
import com.dongminpark.foodmarketandroid.Format.TextButtonFormat
import com.dongminpark.foodmarketandroid.Format.TextFormat
import com.dongminpark.foodmarketandroid.R
import com.dongminpark.foodmarketandroid.Utils.Constant.Companion.outlinePadding

@Composable
fun MyScreen(navController: NavController) {
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
        }
        TextButtonFormat(string = "판매내역"){
            // 판매내역 페이지 이동
        }
        // 관심목록과 판매내역은 같은 함수 재활용. 배열과 이름을 받아서 출력하는 방식

        TextButtonFormat(string = "위치 최신화"){

        }
        TextButtonFormat(string = "로그아웃"){

        }
        TextButtonFormat(string = "탈퇴하기"){

        }
    }
}


@Composable
fun MyListPage(title: String){ //리스트도 앞으로 받아야함.

}