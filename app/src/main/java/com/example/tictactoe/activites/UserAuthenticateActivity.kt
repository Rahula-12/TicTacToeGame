package com.example.tictactoe.activites

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tictactoe.R
import com.example.tictactoe.ui.navigation.TicTacToeApp
import com.example.tictactoe.ui.screens.AuthenticateScreen
import com.example.tictactoe.ui.screens.NoInternetDialog
import com.example.tictactoe.ui.theme.TicTacToeTheme
import com.example.tictactoe.ui.viewmodel.AuthViewModel
import com.example.tictactoe.ui.viewmodel.TicTacToeViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserAuthenticateActivity : ComponentActivity() {

    @Inject
    lateinit var mGoogleSignInClient: GoogleSignInClient

    private lateinit var firebaseAuth:FirebaseAuth

    private lateinit var authViewModel:AuthViewModel

    private var noInternetDialog:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth=FirebaseAuth.getInstance()
        if(firebaseAuth.currentUser!=null) {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        val googleButtonListener=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activityResult->
            val task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                account?.run {
                    firebaseAuthWithGoogle(idToken!!)
                    Toast.makeText(
                        this@UserAuthenticateActivity,
                        "Please wait while we are checking",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
            }
        }
        setContent {
            TicTacToeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                     authViewModel= hiltViewModel()
                    AuthenticateScreen(
                        register ={email,password->
                            firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this,
                                        "Registration Successful",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    authViewModel.register()
                                    val intent = Intent(this, MainActivity::class.java)
                                    this.startActivity(intent)
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        this,
                                        "Registration Failed",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        },
                        signIn = {
                            email,password->
                            firebaseAuth.signInWithEmailAndPassword(email, password)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this,
                                        "Login Successful",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent = Intent(this, MainActivity::class.java)
                                    this.startActivity(intent)
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        this,
                                        "Username or Password is incorrect.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        },
                        onSignInWithGoogleClicked={
                            firebaseAuth.signOut()
                            val signInIntent = mGoogleSignInClient.signInIntent
                            googleButtonListener.launch(signInIntent)
                        }
                    )
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(
                        this,
                        "Logged in Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, MainActivity::class.java)
                    authViewModel.register()
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        this,
                        "Authentication Failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}