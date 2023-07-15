package com.dongminpark.foodmarketandroid.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.foodmarketandroid.App
import com.dongminpark.foodmarketandroid.Button.BackButton
import com.dongminpark.foodmarketandroid.Format.ImageFormat
import com.dongminpark.foodmarketandroid.Format.TextFormat
import com.dongminpark.foodmarketandroid.Model.Board
import com.dongminpark.foodmarketandroid.Model.DetailInfo
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.dongminpark.foodmarketandroid.R
import com.dongminpark.foodmarketandroid.Retrofit.RetrofitManager
import com.dongminpark.foodmarketandroid.Utils.Constants.TAG
import com.dongminpark.foodmarketandroid.Utils.MESSAGE
import com.dongminpark.foodmarketandroid.Utils.RESPONSE_STATE
import com.dongminpark.foodmarketandroid.ui.theme.FoodmarketAndroidTheme
import com.dongminpark.projectgd.Button.FavoriteButton

var testString =
    "이런내용 저런내용 요론내용 조론내용이런내용 저런내용 요론내용 조론내용이런내용 저런내용 요론내용 조론내용 이런내용 저런내용 요론내용 조론내용이런내용 저런내용 요론내용 조론내용"

lateinit var detailInfo: DetailInfo

@Composable
fun DetailScreen(navController: NavController, route: String, boardId: Int) {
    var loading by rememberSaveable{ mutableStateOf(true) }
    var isOkay by rememberSaveable{ mutableStateOf(false) }

    if (loading) {
        loading = false
        RetrofitManager.instance.boardListBoardId(
            boardId,
            completion = { responseState, responseBody ->
                when (responseState) {
                    RESPONSE_STATE.OKAY -> {
                        detailInfo = responseBody!!
                        Log.d(TAG, "api 호출 성공")
                        isOkay = true
                    }
                    RESPONSE_STATE.FAIL -> {
                        Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "api 호출 에러")
                    }
                }
            })
    }
    if (isOkay){
        LazyColumn(
            modifier = Modifier
            //.padding(bottom = 16.dp)
        ) {
            item {
                // 사진 Pager로 표시 및 현재 페이지 표시
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopStart
                ) {
                    PostUi(images = arrayListOf(detailInfo.productImg))
                    BackButton(navController = navController)
                }

                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    // 프로필 내용
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Row(
                            modifier = Modifier
                                .clickable {
                                    // 프로필 창으로 이동
                                    navController.navigate(route + "_profile_screen/${detailInfo.userId}")
                                }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ImageFormat(url = detailInfo.profileImgUrl, size = 80)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column() {
                                TextFormat(string = detailInfo.name, size = 24)
                                TextFormat(string = "용현동", size = 16, weight = FontWeight.Light)
                            }

                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            TextFormat(string = detailInfo.expirationDate, size = 20)
                        }
                    }

                    Spacer(modifier = Modifier.padding(vertical = 8.dp))

                    Divider(
                        color = MaterialTheme.colors.secondaryVariant,
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(vertical = 8.dp))
                    // 내용
                    TextFormat(string = detailInfo.description, size = 16, weight = FontWeight.Light)

                    Spacer(modifier = Modifier.padding(vertical = 16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        FavoriteButton(like = detailInfo.isLike, postNum = detailInfo.boardId, count = detailInfo.likeCount)

                        Spacer(modifier = Modifier.padding(8.dp))

                        Divider(
                            color = MaterialTheme.colors.secondaryVariant,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                        )

                        Spacer(modifier = Modifier.padding(4.dp))

                        TextFormat(string = detailInfo.price.toString() + "원", size = 20)

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            Button(onClick = {
                                navController.navigate(route + "_chatting_detail_screen")
                                // 추후 내가 채팅하고자 하는 사용자의 정보를 보내거나 받거나 하는 기능 필요
                            }) {
                                TextFormat(string = "채팅하기", size = 16)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostUi(images: ArrayList<String>) {
    var nowImageIndex = rememberPagerState(0)
    var circle = painterResource(id = R.drawable.circle)
    // 사진 갯수따라 동적인 변화 필요
    var indexIcons: List<Painter> = listOf()

    repeat(images.size){
       indexIcons = indexIcons.plus(circle)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        HorizontalPager(
            count = images.size,
            state = nowImageIndex
        ) { page ->
            ImageFormat(url = images[page], size = 400)
        }
        Row(modifier = Modifier.padding(3.dp)) {
            indexIcons.forEachIndexed { index, icon ->
                Icon(
                    modifier = Modifier
                        .size(15.dp)
                        .padding(2.dp),
                    painter = icon,
                    contentDescription = "Index Icon",
                    tint = if (index == nowImageIndex.currentPage) MaterialTheme.colors.primaryVariant
                    else MaterialTheme.colors.secondary
                )
            }
        }

    }
}