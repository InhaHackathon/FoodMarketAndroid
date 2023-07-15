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
import com.dongminpark.foodmarketandroid.Model.FoodBank
import com.dongminpark.foodmarketandroid.R
import com.dongminpark.foodmarketandroid.Utils.Constant.Companion.outlinePadding
import com.dongminpark.foodmarketandroid.ui.theme.Point

@Composable
fun FoodBankItemFormat(foodBank: FoodBank, content: () -> Unit) {
    Box(modifier = Modifier.padding(4.dp)){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(24.dp))
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextFormat(string = foodBank.name, size = 24)
                    Spacer(modifier = Modifier.padding(4.dp))
                    Icon(Icons.Default.LocationOn, contentDescription = "location")
                    TextFormat(string = distanceCalc(foodBank.directDistance), size = 12)
                }
                Spacer(modifier = Modifier.padding(4.dp))
                TextFormat(string = "주소 : ${foodBank.address}", size = 14)
                Spacer(modifier = Modifier.padding(4.dp))
                TextFormat(string = "전화번호 : ${foodBank.tel}", size = 14)
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
}

fun distanceCalc(len: Double) = (len/1000).toInt().toString() + if((len / 1000 * 100).toInt() == 0) "Km" else  ".${(len % 1000 / 100).toInt()}Km"