package com.dongminpark.foodmarketandroid.Format

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldFormat(modifier: Modifier = Modifier, keyboardOptions: KeyboardOptions = KeyboardOptions.Default, text: String, isSingle: Boolean = true, onValueChange: (String) -> Unit){
    val textFieldColors = TextFieldDefaults.textFieldColors(
        backgroundColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )

    TextField(
        value = text,
        onValueChange = {
            onValueChange(it)
        },
        modifier = modifier
            .border(
                BorderStroke(1.dp, Color.Gray),
                shape = RoundedCornerShape(4.dp)
            )
            .fillMaxWidth(),
        maxLines = if (isSingle) 1 else 100,
        keyboardOptions = keyboardOptions,
        colors = textFieldColors,
        singleLine = isSingle
    )
}