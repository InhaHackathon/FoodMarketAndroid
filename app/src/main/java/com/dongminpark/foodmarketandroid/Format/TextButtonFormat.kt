package com.dongminpark.foodmarketandroid.Format

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dongminpark.foodmarketandroid.ui.theme.suite

@Composable
fun TextButtonFormat(modifier: Modifier = Modifier, string: String, size: Int = 30, weight: FontWeight = FontWeight.SemiBold, color: Color = Color.Black, content: () -> Unit){
    Text(
        modifier = modifier
            //.fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 8.dp)
            .clickable { content() },
        text = string,
        color = color,
        fontFamily = suite,
        fontWeight = weight,
        fontSize = size.sp
    )
}