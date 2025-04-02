package com.example.firebaseauthapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var data: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        data = FirebaseDatabase.getInstance().reference.child("Users")

        val etEmail = findViewById<EditText>(R.id.emailEditText)
        val etPassword = findViewById<EditText>(R.id.passwordEditText)
        val btnRegister = findViewById<Button>(R.id.registerButton)
        val btnLogin = findViewById<Button>(R.id.loginButton)
        val btnShowData = findViewById<Button>(R.id.showDataButton)
        val tvUserData = findViewById<TextView>(R.id.displayDataTextView)

        // Đăng ký
        btnRegister.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            data.get().addOnSuccessListener { snapshot ->
                                val userCount = snapshot.childrenCount.toInt() + 1
                                val userKey = "user$userCount"

                                val userMap = mapOf("email" to email, "password" to password)
                                data.child(userKey).setValue(userMap)
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(this, "Lỗi: ${it.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        } else {
                            Toast.makeText(this, "Lỗi: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }

        // Đăng nhập
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Lỗi: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Vui lòng nhập email và mật khẩu!", Toast.LENGTH_SHORT).show()
            }
        }

        // Hiển thị dữ liệu
        btnShowData.setOnClickListener {
            data.get().addOnSuccessListener { snapshot ->
                val userInfo = StringBuilder()

                snapshot.children.forEach { user ->
                    val email = user.child("email").value.toString()
                    val password = user.child("password").value.toString()
                    userInfo.append("User: ${user.key}\nEmail: $email\nPassword: $password\n\n")
                }

                tvUserData.text = userInfo.toString()
            }.addOnFailureListener {
                Toast.makeText(this, "Không thể lấy dữ liệu!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
