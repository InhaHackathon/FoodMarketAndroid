package com.dongminpark.foodmarketandroid.Model

data class DetailInfo(
    val boardId: Int,
    val productName: String,
    val productImg: String,
    val expirationDate: String,
    val price: Int,
    val description: String,
    val likeCount: Int,
    val isLike: Boolean,
    val userId: Int,
    val name: String,
    val location: String,
    val profileImgUrl: String
)
