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
import com.dongminpark.foodmarketandroid.R
import com.dongminpark.projectgd.Button.FavoriteButton

@Composable
fun ItemFormat(navController: NavController, route: String, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.padding(4.dp)){
        Button(
            modifier = modifier
                .shadow(0.dp)
                .clip(RoundedCornerShape(24.dp))
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(24.dp)),
            onClick = {
                navController.navigate(route + "_detail_screen")
            }) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    ImageFormat(url = R.drawable.ic_launcher_foreground.toString(), size = 150)
                    Column(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp),
                    ) {
                        TextFormat(string = "상품명", size = 24)
                        Spacer(modifier = Modifier.padding(4.dp))
                        TextFormat(string = "유통기한 2일 남음", size = 12)
                        Spacer(modifier = Modifier.padding(4.dp))
                        TextFormat(string = "80000원", size = 16)
                    }
                }
                FavoriteButton(like = true, postNum = 1, count = 30, size = 40)
            }
        }
    }
}