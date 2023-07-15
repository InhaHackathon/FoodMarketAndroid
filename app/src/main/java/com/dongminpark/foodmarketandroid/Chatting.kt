package com.dongminpark.foodmarketandroid

import androidx.compose.runtime.remember
import com.dongminpark.foodmarketandroid.Model.ChatMessage

class Chatting{
    companion object {
        var pic: Int = 0 // 유저 사진
        var price: Int = 3000 // 물건 가격
        var names: String = "박동민" // 판매자 이름
        var index: Int = 0 // 해당 인덱스

        var chatLogs = listOf(
                ChatMessage("other", "안녕하세요 물품 구매하고싶어서 연락드렸어요", "10:00 AM"),
                ChatMessage("me", "네 안녕하세요", "10:01 AM"),
                ChatMessage("other", "사용감 있나요? 좀 깎아주세요", "10:02 AM"),
                ChatMessage("me", "네고 안됩니다.", "10:03 AM"),
                ChatMessage("other", "그건 너무 비싸네요", "10:03 AM"),
            )

        var Name = ""
        var Latitude = 37.3450775
        var Longitude = 126.654308

    }
}