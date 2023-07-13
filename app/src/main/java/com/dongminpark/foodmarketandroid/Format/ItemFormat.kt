package com.dongminpark.foodmarketandroid.Format

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dongminpark.foodmarketandroid.R

@Composable
fun ItemFormat(modifier: Modifier = Modifier){
    Column(
    ) {
        Row(
            modifier = modifier
                .padding(horizontal = 0.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            ImageFormat(url = R.drawable.ic_launcher_foreground.toString(), size = 150)
            Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp)) {
                TextFormat(string = "상품명", size = 24)
                Spacer(modifier = Modifier.padding(4.dp))
                TextFormat(string = "유통기한", size = 12)
                Spacer(modifier = Modifier.padding(4.dp))
                TextFormat(string = "80000원", size = 16)
            }
        }

        Divider(
            color = MaterialTheme.colors.secondaryVariant,
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
        )
    }
}