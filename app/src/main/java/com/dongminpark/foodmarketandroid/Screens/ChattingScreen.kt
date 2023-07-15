package com.dongminpark.foodmarketandroid.Screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dongminpark.foodmarketandroid.Format.ImageFormat
import com.dongminpark.foodmarketandroid.Format.ItemFormat
import com.dongminpark.foodmarketandroid.Format.TextFormat
import com.dongminpark.foodmarketandroid.R
import com.dongminpark.projectgd.Button.FavoriteButton

@Composable
fun ChattingScreen(navController: NavController) {
    Column() {
        TopAppBar(
            title = {
                TextFormat(
                    string = "채팅",
                    size = 24
                )
            },
            elevation = 4.dp
        )
        LazyColumn(modifier = Modifier.padding(1.dp)) {
            item{
                ChattingFormat(navController = navController)
            }
            item{
                ChattingFormat1(navController = navController)
            }
            item{
                ChattingFormat2(navController = navController)
            }
        }
    }
}

@Composable
fun ChattingFormat(navController: NavController){
    // 상대 사진, 이름, 채팅 보낸 날짜, 최근 한마디
    Box(modifier = Modifier.padding(4.dp)){
        Button(
            modifier = Modifier
                .shadow(0.dp)
                .clip(RoundedCornerShape(24.dp))
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(24.dp)),
            onClick = {
                navController.navigate("chatting_detail_detail_screen")
            }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.BottomEnd,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    ImageFormat(url = "https://i.ibb.co/rK9mD4L/image.jpg", size = 60)
                    Column(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp),
                    ) {
                        TextFormat(string = "한관희", size = 20)
                        Spacer(modifier = Modifier.padding(4.dp))
                        TextFormat(string = "생각해보고 말씀드릴게요", size = 16)
                    }
                }
                TextFormat(string = "07.15")
            }
        }
    }
}

@Composable
fun ChattingFormat1(navController: NavController){
    // 상대 사진, 이름, 채팅 보낸 날짜, 최근 한마디
    Box(modifier = Modifier.padding(4.dp)){
        Button(
            modifier = Modifier
                .shadow(0.dp)
                .clip(RoundedCornerShape(24.dp))
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(24.dp)),
            onClick = {
                navController.navigate("chatting_detail_detail_screen")
            }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.BottomEnd,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    ImageFormat(url = "https://i.ibb.co/Kw15Vzn/image.jpg", size = 60)
                    Column(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp),
                    ) {
                        TextFormat(string = "코코", size = 20)
                        Spacer(modifier = Modifier.padding(4.dp))
                        TextFormat(string = "내일 저녁은 어때요?", size = 16)
                    }
                }
                TextFormat(string = "07.14")
            }
        }
    }
}

@Composable
fun ChattingFormat2(navController: NavController){
    // 상대 사진, 이름, 채팅 보낸 날짜, 최근 한마디
    Box(modifier = Modifier.padding(4.dp)){
        Button(
            modifier = Modifier
                .shadow(0.dp)
                .clip(RoundedCornerShape(24.dp))
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(24.dp)),
            onClick = {
                navController.navigate("chatting_detail_detail_screen")
            }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.BottomEnd,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    ImageFormat(url = "https://i.ibb.co/17ffmVq/image.jpg", size = 60)
                    Column(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp),
                    ) {
                        TextFormat(string = "석현쨩", size = 20)
                        Spacer(modifier = Modifier.padding(4.dp))
                        TextFormat(string = "저도 좋아요~", size = 16)
                    }
                }
                TextFormat(string = "07.15")
            }
        }
    }
}