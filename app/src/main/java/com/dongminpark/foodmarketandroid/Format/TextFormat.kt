package com.dongminpark.foodmarketandroid.Format

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dongminpark.foodmarketandroid.ui.theme.suite

@Composable
fun TextFormat(modifier: Modifier = Modifier, string: String, size: Int, weight: FontWeight = FontWeight.SemiBold){
    Text(
        modifier = modifier,
        text = string,
        color = Color.Black,
        fontFamily = suite,
        fontWeight = weight,
        fontSize = size.sp
    )
}