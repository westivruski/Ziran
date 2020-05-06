package com.wesley.ziran



import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider


class LoginActivity : AppCompatActivity() {

   // private var userViewModel: EventListViewModel? = null
    private var eventRepository: EventRepository? = null
    private var constraintLayout: ConstraintLayout? = null

    private lateinit var usernameText: EditText
    private lateinit var passwordText: EditText
    private lateinit var signUpButton: Button
    private lateinit var loginButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        usernameText = findViewById<View>(R.id.username) as EditText
        passwordText = findViewById<View>(R.id.password) as EditText
        signUpButton = findViewById<View>(R.id.signup) as Button
        loginButton = findViewById<View>(R.id.login) as Button

        val preferences = getSharedPreferences("login", Context.MODE_PRIVATE)

        if (preferences.getBoolean("logged",true)) {
          goToMainActivity()
        }

           // this is to be edited a little later
        val signup = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
            .getBoolean("signup", true)
        if (signup) {
            signUpButton.visibility = View.VISIBLE
            loginButton.visibility = View.GONE
        } else {
            signUpButton.visibility = View.GONE
            loginButton.visibility = View.VISIBLE
        }




        signUpButton.setOnClickListener {
            userListViewModel.createUser(usernameText.text.toString(), passwordText.text.toString())
            getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
                .putBoolean("signup", false).apply()
            Toast.makeText(baseContext, "Successfully Created An Account!", Toast.LENGTH_LONG).show()

        }

        loginButton.setOnClickListener {
            val isValid = userListViewModel.checkValidLogin(usernameText.text.toString().trim { it <= ' ' }, passwordText.text.toString().trim { it <= ' ' })
            if (isValid) {
                goToMainActivity()
                preferences.edit().putBoolean("loggged",true).apply()
            } else {
                Toast.makeText(baseContext, "Invalid Login!", Toast.LENGTH_SHORT).show()
                Log.i("Unsuccessful_Login", "Login was not successful")
            }
        }


    }

    fun goToMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()  // finishing this activity makes it not go back to the LoginActivity, so as soon as it gets sharedPreferences and intents to MainActivity this will e the main process in the stack
    }

    private val userListViewModel: EventListViewModel by lazy {
        ViewModelProvider(this).get(EventListViewModel::class.java)
    }



}