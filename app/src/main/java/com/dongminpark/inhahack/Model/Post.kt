package com.dongminpark.inhahack.Model

data class Post(
    val postNum: Int,
    val thumbnailImgUrl: String?,
    val bookmark: Boolean,
    val me: Boolean
)