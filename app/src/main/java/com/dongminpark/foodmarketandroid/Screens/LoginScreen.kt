package com.dongminpark.foodmarketandroid.Screens

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dongminpark.foodmarketandroid.App
import com.dongminpark.foodmarketandroid.OAuthData
import com.dongminpark.foodmarketandroid.navigation.Screen
import com.dongminpark.foodmarketandroid.ui.theme.suite
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import com.dongminpark.foodmarketandroid.R
import com.dongminpark.foodmarketandroid.Retrofit.RetrofitManager
import com.dongminpark.foodmarketandroid.Utils.Constants
import com.dongminpark.foodmarketandroid.Utils.Constants.TAG
import com.dongminpark.foodmarketandroid.Utils.MESSAGE
import com.dongminpark.foodmarketandroid.Utils.RESPONSE_STATE
import com.dongminpark.foodmarketandroid.ui.theme.Point

var isLoginLoading by mutableStateOf(false)

@Composable
fun LoginScreen(navController: NavHostController) {
    navController.enableOnBackPressed(false)
    /*
    if (OAuthData.account != null){
        // 로그인 돼있음
        val uid = OAuthData.auth?.currentUser?.uid ?: ""
        RetrofitManager.instance.firebaseConnect(uid, completion = { responseState ->

            when (responseState) {
                RESPONSE_STATE.OKAY -> {
                    OAuthData.nav?.navigate(Screen.Once.route)
                    Log.d(Constants.TAG, "api 호출 성공")
                }
                RESPONSE_STATE.FAIL -> {
                    Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                    Log.d(Constants.TAG, "api 호출 에러")
                }
            }
        })
    }

     */

    if (isLoginLoading){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Point),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Loading...",
                fontFamily = suite,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(vertical = 100.dp)
            )
        }
    }else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Point),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "세상에서 가장 환경적인 거래",
                    fontFamily = suite,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 25.sp,
                    color = Color.Black,
                )
                Image(
                    painter = painterResource(id = R.drawable.eat_logo),
                    contentDescription = "먹어요",
                    modifier = Modifier.size(160.dp)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "SNS계정으로 로그인하기",
                    fontFamily = suite,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )

                Icon(
                    painter = painterResource(id = R.drawable.google_kor),
                    contentDescription = "Google Login",
                    modifier = Modifier
                        .clickable {
                            //OAuthData.nav?.navigate(Screen.Once.route)
                            isLoginLoading = true
                            googleLogin()
                        }
                        .padding(10.dp)
                        .width(240.dp),
                    tint = Color.Unspecified
                )
            }
        }
    }
}

fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
    Log.e("Firebase", "진입 성공")
    var credntial = GoogleAuthProvider.getCredential(account?.idToken, null)


    OAuthData.auth?.signInWithCredential(credntial)
        ?.addOnCompleteListener { task ->
            if (task.isSuccessful){
                var uid = OAuthData.auth?.currentUser?.uid.toString()

                Log.e("Firebase Success", "네 성공했습니다.")
                Log.e("uid", uid) // BE가 보내달라함

                RetrofitManager.instance.firebaseConnect(uid, completion = {
                        responseState ->
                    when (responseState) {
                        RESPONSE_STATE.OKAY -> {
                            OAuthData.nav?.navigate(Screen.Once.route)
                            Log.d(TAG, "api 호출 성공")
                        }
                        RESPONSE_STATE.FAIL -> {
                            isLoginLoading = false
                            Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "api 호출 에러")
                        }
                    }
                })
            }
            else{
                isLoginLoading = false
                //Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                Log.e("에러 : ", "${task.exception}")
                Log.e("Firebase ERROR", "먼가 먼가 잘못됨")
            }
        }
}

fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
    try {
        val account = completedTask.getResult(ApiException::class.java)
        val serviceAccountKey = account.serverAuthCode.toString()
        val email = account?.email.toString()
        val name = account?.displayName.toString()
        val profileImg = account?.photoUrl
        var userId = account?.id.toString()
        var accessToken = account?.idToken.toString()

        Log.e("Google account email", email)
        Log.e("Google account name", name)
        Log.e("Google account profileImg", "$profileImg")
        Log.e("Google account userId", userId)
        Log.e("Google account serviceAccountKey", serviceAccountKey)
        Log.e("Google account accessId", accessToken)

        firebaseAuthWithGoogle(account)

    } catch (e: ApiException) {
        isLoginLoading = false
        Log.e("Google account", "signInResult:failed Code = " + e.statusCode)
    }
}

fun googleLogin() {
    var signIntent: Intent = OAuthData.mGoogleSignInClient!!.getSignInIntent()
    OAuthData.GoogleSignResultLauncher.launch(signIntent)
}