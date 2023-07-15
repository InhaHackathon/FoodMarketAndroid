package com.dongminpark.foodmarketandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.dongminpark.foodmarketandroid.Navigation.SetupNavGraph
import com.dongminpark.foodmarketandroid.Screens.handleSignInResult
import com.dongminpark.foodmarketandroid.ui.theme.FoodmarketAndroidTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        OAuthData.auth = FirebaseAuth.getInstance()
        OAuthData.GoogleSignResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }

        installSplashScreen().apply {
            setKeepVisibleCondition{
                viewModel.isLoading.value
            }
        }

        setContent {
            FoodmarketAndroidTheme {
                val navController = rememberNavController()
                OAuthData.nav = navController
                OAuthData.mGoogleSignInClient = GoogleSignIn.getClient(this, OAuthData.gso)
                OAuthData.account = GoogleSignIn.getLastSignedInAccount(this)
                SetupNavGraph(navController = navController)
            }
        }
    }
}