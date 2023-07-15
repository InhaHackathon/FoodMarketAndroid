package com.dongminpark.foodmarketandroid.Model

data class FoodBank(
    val foodBankId: Int,
    val district: String,
    val centerType: String,
    val name: String,
    val tel: String,
    val address: String,
    val detailAddress: String,
    val latitude: Double,
    val longitude: Double,
    val directDistance: Double
)
