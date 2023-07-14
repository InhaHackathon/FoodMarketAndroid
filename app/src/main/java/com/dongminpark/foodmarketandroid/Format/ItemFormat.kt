package com.dongminpark.foodmarketandroid.Format

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.dongminpark.foodmarketandroid.Model.Board
import com.dongminpark.foodmarketandroid.R
import com.dongminpark.projectgd.Button.FavoriteButton

@Composable
fun ItemFormat(navController: NavController, route: String, modifier: Modifier = Modifier, board: Board) {
    Box(modifier = Modifier.padding(4.dp)){
        Button(
            modifier = modifier
                .shadow(0.dp)
                .clip(RoundedCornerShape(24.dp))
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(24.dp)),
            onClick = {
                // 이동할 때 postNum 가지고 넘어가야함.
                navController.navigate(route + "_detail_screen/${board.boardId}")
            }) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    ImageFormat(url = board.productImg, size = 150)
                    Column(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp),
                    ) {
                        Spacer(modifier = Modifier.padding(6.dp))
                        TextFormat(string = board.productName, size = 24)
                        Spacer(modifier = Modifier.padding(4.dp))
                        TextFormat(string = board.expirationDate, size = 12)
                        Spacer(modifier = Modifier.padding(4.dp))
                        TextFormat(string = "${board.price}원", size = 16)
                    }
                }
                FavoriteButton(like = board.isLike, postNum = board.boardId, count = board.likeCount, size = 25)
            }
        }
    }
}