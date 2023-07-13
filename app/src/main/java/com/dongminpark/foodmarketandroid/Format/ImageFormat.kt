package com.dongminpark.foodmarketandroid.Format

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.dongminpark.foodmarketandroid.R

@Composable
fun ImageFormat(url: String, size: Int = 100) {
    val painter = // 이미지 로드 중 및 실패 시 표시할 이미지 리소스를 설정할 수 있습니다.
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = url)
                .apply(block = fun ImageRequest.Builder.() {
                    // 이미지 로드 중 및 실패 시 표시할 이미지 리소스를 설정할 수 있습니다.
                    placeholder(R.drawable.placeholder)
                    error(R.drawable.placeholder)
                }).build()
        )
    Image(
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .size(size.dp)
            .aspectRatio(1f),
        painter = painter,
        contentDescription = "Image"
    )
}