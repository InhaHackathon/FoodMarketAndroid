package com.dongminpark.foodmarketandroidModel

data class Post(
    val postNum: Int,
    val thumbnailImgUrl: String?,
    val bookmark: Boolean,
    val me: Boolean
)