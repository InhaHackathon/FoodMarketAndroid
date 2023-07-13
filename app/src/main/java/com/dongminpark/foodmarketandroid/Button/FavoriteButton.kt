package com.dongminpark.projectgd.Button

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dongminpark.foodmarketandroid.App
import com.dongminpark.foodmarketandroid.Format.TextFormat
import com.dongminpark.foodmarketandroid.R
import com.dongminpark.foodmarketandroid.Retrofit.RetrofitManager
import com.dongminpark.foodmarketandroid.Utils.Constants
import com.dongminpark.foodmarketandroid.Utils.MESSAGE
import com.dongminpark.foodmarketandroid.Utils.RESPONSE_STATE


@Composable
fun FavoriteButton(like: Boolean, postNum: Int, count: Int, size: Int = 26){
    var isFavorite by rememberSaveable { mutableStateOf(like) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = if (isFavorite) painterResource(id = R.drawable.favorite_red) else painterResource(
                id = R.drawable.favorite_outline
            ),
            contentDescription = "Favorite",
            modifier = Modifier
                .size(size.dp)
                .clickable {
                    if (isFavorite) {
                        RetrofitManager.instance.likesDeletePostNum(
                            postNum, completion = { responseState ->
                                when (responseState) {
                                    RESPONSE_STATE.OKAY -> {
                                        isFavorite = !isFavorite
                                    }
                                    RESPONSE_STATE.FAIL -> {
                                        Toast.makeText(
                                            App.instance,
                                            MESSAGE.ERROR,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        Log.d(Constants.TAG, "Likes Delete Error")
                                    }
                                }
                            })
                    } else {
                        RetrofitManager.instance.likesCreatePostNum(
                            postNum, completion = { responseState ->
                                when (responseState) {
                                    RESPONSE_STATE.OKAY -> {
                                        isFavorite = !isFavorite
                                    }
                                    RESPONSE_STATE.FAIL -> {
                                        Toast
                                            .makeText(
                                                App.instance,
                                                MESSAGE.ERROR,
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                        Log.d(Constants.TAG, "Likes Create Error")
                                    }
                                }
                            })
                    }
                },
            tint = Color.Unspecified
        )
        TextFormat(string = count.toString(), size = 8)
    }
}