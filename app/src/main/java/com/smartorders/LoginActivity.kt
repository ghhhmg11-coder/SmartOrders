package com.smartorders

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.smartorders.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    companion object {
        private const val VALID_USERNAME = "admin"
        private const val VALID_PASSWORD = "1234"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin() {
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        binding.tvError.visibility = View.GONE

        if (username.isEmpty() || password.isEmpty()) {
            binding.tvError.text = getString(R.string.error_empty_fields)
            binding.tvError.visibility = View.VISIBLE
            return
        }

        if (username == VALID_USERNAME && password == VALID_PASSWORD) {
            Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            binding.tvError.text = getString(R.string.error_invalid_credentials)
            binding.tvError.visibility = View.VISIBLE
        }
    }
}
