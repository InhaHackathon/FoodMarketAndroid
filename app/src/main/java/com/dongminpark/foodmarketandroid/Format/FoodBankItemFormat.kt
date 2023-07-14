package com.dongminpark.foodmarketandroid.Format

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dongminpark.foodmarketandroid.R
import com.dongminpark.foodmarketandroid.Utils.Constant.Companion.outlinePadding
import com.dongminpark.foodmarketandroid.ui.theme.Point

@Composable
fun FoodBankItemFormat(content: () -> Unit) {
    Box(modifier = Modifier.padding(4.dp)){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(24.dp))
                .padding(outlinePadding.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextFormat(string = "지점명", size = 24)
                    Spacer(modifier = Modifier.padding(4.dp))
                    Icon(Icons.Default.LocationOn, contentDescription = "location")
                    TextFormat(string = "1.2km", size = 16)
                }
                Spacer(modifier = Modifier.padding(4.dp))
                TextFormat(string = "주소 : 수원시 장안구 연무동 62-6 202호", size = 16)
                Spacer(modifier = Modifier.padding(4.dp))
                TextFormat(string = "전화번호 : 010-2245-3683", size = 16)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Image(
                    painter = painterResource(id = R.drawable.map),
                    contentDescription = "map",
                    modifier = Modifier.size(50.dp).clickable {
                        content()
                    },
                    colorFilter = ColorFilter.tint(Point)
                )
            }
        }
    }


    /*
    Divider(
        color = MaterialTheme.colors.secondaryVariant,
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
    )

     */
}