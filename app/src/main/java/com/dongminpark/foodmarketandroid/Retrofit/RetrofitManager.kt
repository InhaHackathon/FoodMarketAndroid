package com.dongminpark.foodmarketandroid.Retrofit

import android.util.Log
import com.dongminpark.foodmarketandroid.Model.Board
import com.dongminpark.foodmarketandroid.Model.DetailInfo
import com.dongminpark.foodmarketandroid.Model.FoodBank
import com.dongminpark.foodmarketandroid.Model.Profile
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
import kotlin.math.log

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
        completion: (RESPONSE_STATE, detialInfo: DetailInfo?) -> Unit
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
                            val writer = body.get("writer").asJsonObject
                            val userId = writer.get("userId").asInt
                            val name = writer.get("name").asString
                            val location = writer.get("location").asString
                            val profileImgUrl = writer.get("profileImgUrl").asString

                            val detailInfo = DetailInfo(
                                boardId = boardId,
                                productName = productName,
                                productImg = productImg,
                                expirationDate = expirationDate,
                                price = price,
                                description = description,
                                likeCount = likeCount,
                                isLike = isLike,
                                userId = userId,
                                name = name,
                                location = location,
                                profileImgUrl = profileImgUrl
                            )


                            completion(RESPONSE_STATE.OKAY, detailInfo)
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

    fun boardUserUserId(userId: Int, completion: (RESPONSE_STATE, List<Board>?) -> Unit) {
        val call = iRetrofit?.boardUserUserId(userId) ?: return

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

    fun boardSearch(keyword: String, completion: (RESPONSE_STATE, List<Board>?) -> Unit) {
        val call = iRetrofit?.boardSearch(keyword) ?: return

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

    fun boardUserMypage(completion: (RESPONSE_STATE, List<Board>?) -> Unit) { // Unit이 아니라 반환타입으로 변경 필요
        val call = iRetrofit?.boardUserMypage() ?: return

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
                completion(RESPONSE_STATE.FAIL,null)
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

    fun likeListUserId(userId: Int, completion: (RESPONSE_STATE, List<Board>?) -> Unit) {
        val call = iRetrofit?.likeListUserId(userId) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "gptStop - onResponse() called / respose : ${response.body()}")

                when (response.code()) {
                    200 -> { // 정상 연결
                        response.body()?.let {
                            val boards = arrayListOf<Board>()
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject
                            val commentList = data.getAsJsonArray("likesList")

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

    // User
    fun userUserId(userId: Int, completion: (RESPONSE_STATE, profile: Profile?) -> Unit) {
        val call = iRetrofit?.userUserId(userId) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                Log.d(TAG, "gptStop - onResponse() called / respose : ${response.body()}")

                when (response.code()) {
                    200 -> { // 정상 연결
                        response.body()?.let {
                            val body =
                                it.asJsonObject.get("data").asJsonObject.get("user").asJsonObject
                            val userId = body.get("userId").asInt
                            val name = body.get("name").asString
                            val location = body.get("location").asString
                            val profileImgUrl = body.get("profileImgUrl").asString

                            val profile = Profile(
                                userId = userId,
                                name = name,
                                location = location,
                                profileImgUrl = profileImgUrl
                            )

                            completion(RESPONSE_STATE.OKAY, profile)
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

    fun userDeleteUserId(userId: Int, completion: (RESPONSE_STATE) -> Unit) {
        val call = iRetrofit?.userDeleteUserId(userId) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
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
            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                Log.d(
                    TAG,
                    "FirebaseConnect - onResponse() called / respose : ${response.body()}"
                )
                when (response.code()) {
                    200 -> { // 정상 연결
                        response.body()?.let {
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject
                            val accessToken = data.get("jwt").asString
                            //val id = data.get("userId").asInt

                            // 일단은 이런식으로 사용. 나중에는 security적용해서
                            OAuthData.ACCESS_TOKEN = accessToken
                            USER.ACCESS_TOKEN = accessToken
                            //USER.USERID = id

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

    fun foodbankList(completion: (RESPONSE_STATE, List<FoodBank>?) -> Unit) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("latitude", 37.45086140839723)
        jsonObject.addProperty("longitude", 126.65434232805298)
        val call = iRetrofit?.foodbankList(jsonObject) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "gptStop - onResponse() called / respose : ${response.body()}")

                when (response.code()) {
                    200 -> { // 정상 연결
                        response.body()?.let {
                            val foodBanks = arrayListOf<FoodBank>()
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject
                            val commentList = data.getAsJsonArray("foodbank")

                            commentList.forEach {
                                val body = it.asJsonObject
                                val foodBankId = body.get("foodBankId").asInt
                                val district = body.get("district").asString
                                val centerType = body.get("centerType").asString
                                val name = body.get("name").asString
                                val tel = body.get("tel").asString
                                val address = body.get("address").asString
                                val detailAddress = body.get("detailAddress").asString
                                val latitude = body.get("latitude").asDouble
                                val longitude = body.get("longitude").asDouble
                                val directDistance = body.get("directDistance").asDouble

                                val foodbank = FoodBank(
                                    foodBankId = foodBankId,
                                    district = district,
                                    centerType = centerType,
                                    name = name,
                                    tel = tel,
                                    address = address,
                                    detailAddress = detailAddress,
                                    latitude = latitude,
                                    longitude = longitude,
                                    directDistance = directDistance
                                )
                                foodBanks.add(foodbank)
                            }

                            completion(RESPONSE_STATE.OKAY, foodBanks)
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
}