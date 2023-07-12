package com.dongminpark.inhahack.Model

data class FollowUser(
    val userId: Int,
    val name: String,
    val profileImgUrl: String,
    val follow: Boolean
)
