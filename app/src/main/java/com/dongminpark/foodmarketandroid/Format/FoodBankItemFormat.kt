package com.dongminpark.foodmarketandroid.Format

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dongminpark.foodmarketandroid.Utils.Constant.Companion.outlinePadding

@Composable
fun FoodBankItemFormat(content: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(outlinePadding.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextFormat(string = "지점 명", size = 24)
                Spacer(modifier = Modifier.padding(4.dp))
                TextFormat(string = "거리", size = 16)
            }
            Spacer(modifier = Modifier.padding(4.dp))
            TextFormat(string = "주소", size = 16)
            Spacer(modifier = Modifier.padding(4.dp))
            TextFormat(string = "전화번호", size = 16)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Button(onClick = { content() }) {
                TextFormat(string = "지도 보기", size = 15)
            }
        }
    }

    Divider(
        color = MaterialTheme.colors.secondaryVariant,
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
    )
}