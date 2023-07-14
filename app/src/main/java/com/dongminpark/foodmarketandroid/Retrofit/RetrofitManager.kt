package com.dongminpark.foodmarketandroid.Retrofit

import android.util.Log
import com.dongminpark.foodmarketandroid.Model.Board
import com.dongminpark.foodmarketandroid.OAuthData
import com.dongminpark.foodmarketandroid.Utils.API
import com.dongminpark.foodmarketandroid.Utils.Constants.TAG
import com.dongminpark.foodmarketandroid.Utils.RESPONSE_STATE
import com.dongminpark.foodmarketandroid.Utils.USER
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import kotlin.math.exp

class RetrofitManager {
    companion object {
        val instance = RetrofitManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun boardCreate(
        productName: String,
        productImg: String,
        expirationDate: String,
        price: Int,
        description: String,
        completion: (RESPONSE_STATE) -> Unit
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("productName", productName)
        jsonObject.addProperty("productImg", productImg)
        jsonObject.addProperty("expirationDate", expirationDate)
        jsonObject.addProperty("price", price)
        jsonObject.addProperty("description", description)
        val call = iRetrofit?.boardCreate(BoardRequestDto = jsonObject) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "gptStop - onResponse() called / respose : ${response.body()}")

                when (response.code()) {
                    200 -> { // 정상 연결
                        completion(RESPONSE_STATE.OKAY)
                    }
                    else -> { // 에러
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL)
            }

        })
    }

    fun boardDeleteBoardId(boardId: Int, completion: (RESPONSE_STATE) -> Unit) {
        val call = iRetrofit?.boardDeleteBoardId(boardId) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "gptStop - onResponse() called / respose : ${response.body()}")

                when (response.code()) {
                    200 -> { // 정상 연결
                        completion(RESPONSE_STATE.OKAY)
                    }
                    else -> { // 에러
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL)
            }

        })
    }

    fun boardList(completion: (RESPONSE_STATE, ArrayList<Board>?) -> Unit) {
        val call = iRetrofit?.boardList() ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "gptStop - onResponse() called / respose : ${response.body()}")

                when (response.code()) {
                    200 -> { // 정상 연결
                        response.body()?.let {
                            val boards = arrayListOf<Board>()
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject
                            val commentList = data.getAsJsonArray("board")

                            commentList.forEach {
                                val body = it.asJsonObject
                                val boardId = body.get("boardId").asInt
                                val productName = body.get("productName").asString
                                val productImg = body.get("productImg").asString
                                val expirationDate = body.get("expirationDate").asString
                                val price = body.get("price").asInt
                                val description = body.get("description").asString
                                val likeCount = body.get("likeCount").asInt
                                val isLike = body.get("isLike").asBoolean

                                val board = Board(
                                    boardId = boardId,
                                    productName = productName,
                                    productImg = productImg,
                                    expirationDate = expirationDate,
                                    price = price,
                                    description = description,
                                    likeCount = likeCount,
                                    isLike = isLike
                                )
                                boards.add(board)
                            }

                            completion(RESPONSE_STATE.OKAY, boards)
                        }
                    }
                    else -> { // 에러
                        completion(RESPONSE_STATE.FAIL, null)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL, null)
            }
        })
    }

    fun boardListBoardId(
        boardId: Int,
        completion: (RESPONSE_STATE, board: Board?) -> Unit
    ) { // 반환타입 Unit에서 변경필요
        val call = iRetrofit?.boardListBoardId(boardId) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "gptStop - onResponse() called / respose : ${response.body()}")

                when (response.code()) {
                    200 -> { // 정상 연결
                        response.body()?.let {
                            val body =
                                it.asJsonObject.get("data").asJsonObject.get("board").asJsonObject
                            val boardId = body.get("boardId").asInt
                            val productName = body.get("productName").asString
                            val productImg = body.get("productImg").asString
                            val expirationDate = body.get("expirationDate").asString
                            val price = body.get("price").asInt
                            val description = body.get("description").asString
                            val likeCount = body.get("likeCount").asInt
                            val isLike = body.get("isLike").asBoolean

                            val board = Board(
                                boardId = boardId,
                                productName = productName,
                                productImg = productImg,
                                expirationDate = expirationDate,
                                price = price,
                                description = description,
                                likeCount = likeCount,
                                isLike = isLike
                            )


                            completion(RESPONSE_STATE.OKAY, board)
                        }
                    }
                    else -> { // 에러
                        completion(RESPONSE_STATE.FAIL, null)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL, null)
            }

        })
    }

    fun boardUpdateBoardId(boardId: Int, completion: (RESPONSE_STATE) -> Unit) {
        val call = iRetrofit?.boardUpdateBoardId(boardId) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "gptStop - onResponse() called / respose : ${response.body()}")

                when (response.code()) {
                    200 -> { // 정상 연결
                        completion(RESPONSE_STATE.OKAY)
                    }
                    else -> { // 에러
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL)
            }

        })
    }

    fun boardUserUserId(userId: Int, completion: (RESPONSE_STATE) -> Unit) {
        val call = iRetrofit?.boardUserUserId(userId) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "gptStop - onResponse() called / respose : ${response.body()}")

                when (response.code()) {
                    200 -> { // 정상 연결
                        completion(RESPONSE_STATE.OKAY)
                    }
                    else -> { // 에러
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL)
            }

        })
    }

    fun boardUserMypage(completion: (RESPONSE_STATE) -> Unit) { // Unit이 아니라 반환타입으로 변경 필요
        val call = iRetrofit?.boardUserMypage() ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "gptStop - onResponse() called / respose : ${response.body()}")

                when (response.code()) {
                    200 -> { // 정상 연결
                        // 뭔가 많은 값을 받을 것임.
                        /* 예시
                        response.body()?.let {
                                val comments = arrayListOf<Comments>()
                                val body = it.asJsonObject
                                val data = body.get("data").asJsonObject
                                val commentList = data.getAsJsonArray("commentList")

                                commentList.forEach {
                                    val body = it.asJsonObject
                                    val user = body.get("user").asJsonObject
                                    val userId = user.get("userId").asInt
                                    val name = user.get("name").asString
                                    val profileImgUrl = user.get("profileImgUrl").asString
                                    val serialNum = body.get("serialNum").asInt
                                    val content = body.get("content").asString
                                    val isMe = body.get("isMe").asBoolean

                                    val comment = Comments(
                                        serialNum = serialNum,
                                        userId = userId,
                                        name = name,
                                        profileImgUrl = profileImgUrl,
                                        content = content,
                                        isMe = isMe
                                    )
                                    comments.add(comment)
                                }

                                completion(RESPONSE_STATE.OKAY, comments)
                         */
                        completion(RESPONSE_STATE.OKAY)
                    }
                    else -> { // 에러
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL)
            }

        })
    }

    fun LikeBoardId(boardId: Int, completion: (RESPONSE_STATE) -> Unit) {
        val call = iRetrofit?.likeBoardId(boardId) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "gptStop - onResponse() called / respose : ${response.body()}")

                when (response.code()) {
                    200 -> { // 정상 연결
                        completion(RESPONSE_STATE.OKAY)
                    }
                    else -> { // 에러
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL)
            }

        })
    }

    fun likeDeleteBoardId(boardId: Int, completion: (RESPONSE_STATE) -> Unit) {
        val call = iRetrofit?.likeDeleteBoardId(boardId) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "gptStop - onResponse() called / respose : ${response.body()}")

                when (response.code()) {
                    200 -> { // 정상 연결
                        completion(RESPONSE_STATE.OKAY)
                    }
                    else -> { // 에러
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL)
            }

        })
    }

    fun likeListUserId(userId: Int, completion: (RESPONSE_STATE) -> Unit) {
        val call = iRetrofit?.likeListUserId(userId) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "gptStop - onResponse() called / respose : ${response.body()}")

                when (response.code()) {
                    200 -> { // 정상 연결
                        completion(RESPONSE_STATE.OKAY)
                    }
                    else -> { // 에러
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL)
            }

        })
    }

    // User
    fun userUserId(userId: Int, completion: (RESPONSE_STATE) -> Unit) {
        val call = iRetrofit?.userUserId(userId) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "gptStop - onResponse() called / respose : ${response.body()}")

                when (response.code()) {
                    200 -> { // 정상 연결
                        completion(RESPONSE_STATE.OKAY)
                    }
                    else -> { // 에러
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL)
            }

        })
    }

    fun userDeleteUserId(userId: Int, completion: (RESPONSE_STATE) -> Unit) {
        val call = iRetrofit?.userDeleteUserId(userId) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "gptStop - onResponse() called / respose : ${response.body()}")

                when (response.code()) {
                    200 -> { // 정상 연결
                        completion(RESPONSE_STATE.OKAY)
                    }
                    else -> { // 에러
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL)
            }

        })
    }

    // firebase와 DB 연결
    fun firebaseConnect(
        uid: String?,
        completion: (RESPONSE_STATE) -> Unit
    ) { // 통신 성공 여부 , 토큰, 멤버 여부
        val term = uid ?: ""

        val call = iRetrofit?.firebaseConnect(uid = term) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            // 응답 실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "FirebaseConnect - onFailure() called / t: $t")
                completion(RESPONSE_STATE.FAIL)
            }

            // 응답 성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "FirebaseConnect - onResponse() called / respose : ${response.body()}")
                when (response.code()) {
                    200 -> { // 정상 연결
                        response.body()?.let {
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject
                            val accessToken = data.get("jwt").asString

                            // 일단은 이런식으로 사용. 나중에는 security적용해서
                            OAuthData.ACCESS_TOKEN = accessToken
                            USER.ACCESS_TOKEN = accessToken

                            // 이런방식으로 하면 안되고
                            Log.e(TAG, "onResponse: ${OAuthData.ACCESS_TOKEN}")
                            completion(RESPONSE_STATE.OKAY)
                        }
                    }
                    else -> { // 에러
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }
        })
    }
}