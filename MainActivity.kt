package com.learning.test1.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.learning.test1.R
import com.learning.test1.rest.RetrofitClient
import com.learning.test1.models.DefaultResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    lateinit var username: String
    lateinit var email: String
    lateinit var phonenumber: String
    lateinit var password: String
    lateinit var confirmpassword: String

    lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        signin()
        signupMain()
        fb()
        /*google()*/
    }

    /*private fun google() {
        var gso:GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        var mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }*/

   /* override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }*/
    private fun fb() {

        facebook_button.setOnClickListener {
            callbackManager = CallbackManager.Factory.create()

            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    val i = Intent(this@MainActivity,LogoutActivity::class.java)
                    startActivity(i)
                }
                override fun onCancel() {
                    Toast.makeText(applicationContext,"cancelled",Toast.LENGTH_SHORT).show()
                }
                override fun onError(exception: FacebookException) {
                    Toast.makeText(applicationContext,"error",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    /**
     * Initialization
     */
    private fun init() {
        username = username_tiet.text.toString()
        email = email_tiet.text.toString()
        phonenumber = phone_tiet.text.toString()
        password = password_tiet.text.toString()
        confirmpassword = cpassword_tiet.text.toString()
    }

    /**
     * Signup for validating and calling response from client
     */
    private fun signupMain() {
        var signup = findViewById<AppCompatButton>(R.id.signup_acb1)
        signup.setOnClickListener {
            init()
            validation()
        }
    }

    /**
     * validating user details
     */
    private fun validation() {

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+[.][a-z]+".toRegex()

        if (username.isEmpty()) {
            username_tiet.setError("User Name cannot be empty")
        } else if (username.length >= 15) {
            username_tiet.setError("user name is too Long")
        } else if (email.isEmpty()) {
            email_tiet.setError("Email cannot be empty")
        } else if (!email.matches(emailPattern)) {
            email_tiet.setError("Invalid Email")
        } else if (phonenumber.isEmpty()) {
            phone_tiet.setError("Phone Number cannot be empty")
        } else if (phonenumber.length != 10) {
            phone_tiet.setError("enter valid phone number")
        } else if (password.isEmpty()) {
            password_tiet.setError("Password cannot be empty")
        } else if (password.length < 8) {
            password_tiet.setError("Password Must be Alteast 8 characters")
        } else if (confirmpassword.isEmpty()) {
            cpassword_tiet.setError("Confirm password should not be empty")
        } else if (password != confirmpassword ) {
            Toast.makeText(this, "Password Mis-Matched", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Registered Sucessfully now", Toast.LENGTH_LONG).show()
            clientCalling()
        }
    }

    private fun clientCalling() {
        RetrofitClient.instance.UserDetails(username, email, phonenumber, "+91",
            confirmpassword, 1234, 1, 1, "INR")
            .enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    Log.e("out", "onResponse:response came" + response.toString())
                }
                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Log.e("out", "onResponse:fail")
                }
            })
    }

    /**
     * redirecting to signin
     */
    private fun signin() {
        var signin = findViewById<AppCompatButton>(R.id.signin_acb)
        signin.setOnClickListener {
            var i = Intent(this, activity_signin::class.java)
            startActivity(i)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}

